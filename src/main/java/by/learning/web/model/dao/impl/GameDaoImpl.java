package by.learning.web.model.dao.impl;

import by.learning.web.exception.ConnectionPoolException;
import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.entity.Game;
import by.learning.web.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class GameDaoImpl implements GameDao {
    private static final Logger logger = LogManager.getLogger();

    private static final GameDaoImpl INSTANCE = new GameDaoImpl();

    public static GameDaoImpl getInstance() {
        return INSTANCE;
    }

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.INSTANCE;

    private static final String FIND_ALL_GAMES = "SELECT games.game_id, title, image_path, price, string_agg(DISTINCT g.name, ','), string_agg(DISTINCT ct.name,',') " +
            "FROM games " +
            "         INNER JOIN category_game cg on games.game_id = cg.game_id " +
            "         INNER JOIN categories ct on ct.category_id = cg.category_id " +
            "         INNER JOIN genre_game gn on games.game_id = gn.game_id " +
            "         INNER JOIN genres g on gn.genre_id = g.genre_id " +
            "GROUP BY games.game_id";
    private static final String FIND_GAME_BY_ID = "SELECT title," +
            "       description," +
            "       image_path," +
            "       price," +
            "       trailer," +
            "       string_agg(DISTINCT g.name, ',')," +
            "       string_agg(DISTINCT ct.name, ',')" +
            "FROM games" +
            "         INNER JOIN category_game cg on games.game_id = cg.game_id" +
            "         INNER JOIN categories ct on ct.category_id = cg.category_id" +
            "         INNER JOIN genre_game gn on games.game_id = gn.game_id" +
            "         INNER JOIN genres g on gn.genre_id = g.genre_id " +
            "WHERE games.game_id = ?" +
            "GROUP BY games.game_id;";
    private static final String FIND_GAME_AMOUNT_BY_ID = "SELECT count(*) " +
            "FROM codes " +
            "WHERE (sold IS FALSE) " +
            "AND (game_id = ?)";
    private static final String CREATE_GAME = "INSERT INTO games(game_id, title, image_path, description, price, trailer) " +
            "VALUES (default, ?, ?, ?, ?, ?)";
    private static final String RELATE_GAME_CATEGORY = "INSERT INTO category_game(game_id, category_id) " +
            "VALUES (?, ?)";
    private static final String RELATE_GAME_GENRE = "INSERT INTO genre_game(game_id, genre_id) " +
            "VALUES (?, ?)";
    private static final String FIND_ALL_CATEGORIES = "SELECT category_id, name FROM categories";
    private static final String FIND_ALL_GENRES = "SELECT genre_id, name FROM genres";

    @Override
    public List<Game> findAllGames() throws DaoException {
        List<Game> result = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            preparedStatement = connection.prepareStatement(FIND_ALL_GAMES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int gameId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String imagePath = resultSet.getString(3);
                BigDecimal price = resultSet.getBigDecimal(4);
                String genreString = resultSet.getString(5);
                String categoryString = resultSet.getString(6);
                Game game = new Game(gameId, title, imagePath, price, genreString, categoryString);
                result.add(game);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }
        return result;
    }

    @Override
    public int findGameCount(int gameId) throws DaoException {
        int amount;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.getConnection()) {
            preparedStatement = connection.prepareStatement(FIND_GAME_AMOUNT_BY_ID);
            preparedStatement.setInt(1, gameId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            amount = resultSet.getInt(1);
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return amount;
    }

    @Override
    public boolean createGame(Game game, int[] genresId, int[] categoriesId) throws DaoException {
        boolean isCreated = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CREATE_GAME, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, game.getTitle());
            preparedStatement.setString(2, game.getImagePath());
            preparedStatement.setString(3, game.getDescription());
            preparedStatement.setBigDecimal(4, game.getPrice());
            preparedStatement.setString(5, game.getTrailer());
            int executeUpdate = preparedStatement.executeUpdate();
            if (executeUpdate > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                int gameId = generatedKeys.getInt(1);
                for (int categoryId : categoriesId) {
                    relateGameCategories(connection, gameId, categoryId);
                }
                for (int genreId : genresId) {
                    relateGameGenres(connection, gameId, genreId);
                }
                isCreated = true;
            }
        } catch (ConnectionPoolException | SQLException ex) {
            rollback(connection);
            throw new DaoException(ex);
        } finally {
            setAutoCommitTrue(connection);
            close(generatedKeys);
            close(preparedStatement);
        }
        return isCreated;
    }

    private void relateGameCategories(Connection connection, int gameId, int categoryId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELATE_GAME_CATEGORY)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();
        }
    }

    private void relateGameGenres(Connection connection, int gameId, int genreId) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELATE_GAME_GENRE)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setInt(2, genreId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Optional<Game> findGameById(int id) throws DaoException {
        Optional<Game> result = Optional.empty();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.getConnection()) {
            preparedStatement = connection.prepareStatement(FIND_GAME_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString(1);
                String description = resultSet.getString(2);
                String imagePath = resultSet.getString(3);
                BigDecimal price = resultSet.getBigDecimal(4);
                String trailer = resultSet.getString(5);
                String genreString = resultSet.getString(6);
                String categoryString = resultSet.getString(7);
                Game game = new Game(id, title, description, imagePath, price, trailer, genreString, categoryString);
                result = Optional.of(game);
            }
        } catch (ConnectionPoolException | SQLException e) {
            throw new DaoException(e);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public HashMap<Integer, String> findAllCategories() throws DaoException {
        return findGameCharacteristics(FIND_ALL_CATEGORIES);
    }

    @Override
    public HashMap<Integer, String> findAllGenres() throws DaoException {
        return findGameCharacteristics(FIND_ALL_GENRES);
    }

    private HashMap<Integer, String> findGameCharacteristics(String characteristic) throws DaoException {
        HashMap<Integer, String> result = new HashMap<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = CONNECTION_POOL.getConnection()) {
            preparedStatement = connection.prepareStatement(characteristic);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                result.put(id, name);
            }
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DaoException(ex);
        } finally {
            close(resultSet);
            close(preparedStatement);
        }
        return result;
    }
}
