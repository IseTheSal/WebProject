package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.impl.DaoInstance;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import by.learning.web.validator.GameValidator;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>Game service implementation.</pre>
 *
 * @author Illia Aheyeu
 */
public class GameServiceImpl implements GameService {
    private static final Logger logger = LogManager.getLogger();

    private final GameDao gameDao = DaoInstance.INSTANCE.getGameDao();
    private static final String UPLOAD_PATH = "C:\\Users\\illya\\Desktop\\Epam\\Epam Learning\\Servlet\\src\\main\\webapp\\img\\logo\\";
    private static final String IMG_PROJECT_PATH = "/img/logo/";

    GameServiceImpl() {
    }

    @Override
    public List<Game> findAllGames() throws ServiceException {
        List<Game> result;
        try {
            result = gameDao.findAllGames();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Set<String> createGame(String gameTitle, Part imagePart,
                                  String description, String price, String trailerLink,
                                  String[] genres, String[] categories) throws ServiceException {
        String imagePath = imagePart.getSubmittedFileName();
        Set<ValidationInformation> validInfo = GameValidator.findGameValidationIssues(gameTitle, imagePath, description, price, trailerLink);
        Set<String> valueValidInfo = validInfo.stream().map(ValidationInformation::getInfoValue).collect(Collectors.toSet());
        if (!valueValidInfo.isEmpty()) {
            return valueValidInfo;
        }
        int[] genresId = Arrays.stream(genres).mapToInt(Integer::parseInt).toArray();
        int[] categoriesId = Arrays.stream(categories).mapToInt(Integer::parseInt).toArray();
        BigDecimal priceValue = new BigDecimal(price);
        String uploadDirectoryPath = UPLOAD_PATH + imagePath;
        try (InputStream inputStream = imagePart.getInputStream()) {
            uploadFile(inputStream, uploadDirectoryPath);
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
        Game game = new Game.GameBuilder(gameTitle, IMG_PROJECT_PATH + imagePath)
                .description(description)
                .price(priceValue)
                .trailer(trailerLink).build();
        try {
            boolean isCreated = gameDao.createGame(game, genresId, categoriesId);
            valueValidInfo.add(isCreated ? ValidationInformation.SUCCESS.getInfoValue() : ValidationInformation.FAIL.getInfoValue());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return valueValidInfo;
    }


    /**
     * Upload file
     *
     * @param inputStream InputStream
     * @param path        Image path from game
     * @throws ServiceException if {@link IOException} was thrown
     */
    private void uploadFile(InputStream inputStream, String path) throws ServiceException {
        try {
            byte[] bytes = new byte[inputStream.available()];
            if (inputStream.read(bytes) != -1) {
                FileOutputStream fops = new FileOutputStream(path);
                fops.write(bytes);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Game> findGameById(int id) throws ServiceException {
        Optional<Game> result = Optional.empty();
        if (id > 0) {
            try {
                result = gameDao.findGameById(id);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.INFO, "Id {} less than zero", id);
        }
        return result;
    }

    @Override
    public HashMap<Integer, String> findAllCategories() throws ServiceException {
        HashMap<Integer, String> result;
        try {
            result = gameDao.findAllCategories();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public HashMap<Integer, String> findGameCategories(int gameId) throws ServiceException {
        HashMap<Integer, String> result;
        try {
            result = gameDao.findGameCategories(gameId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public HashMap<Integer, String> findAllGenres() throws ServiceException {
        HashMap<Integer, String> result;
        try {
            result = gameDao.findAllGenres();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public HashMap<Integer, String> findGameGenres(int gameId) throws ServiceException {
        HashMap<Integer, String> result;
        try {
            result = gameDao.findGameGenres(gameId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Set<String> editGame(String gameIdValue, String gameTitle, String imagePath,
                                String description, String priceValue, String trailerLink,
                                String[] genres, String[] categories) throws ServiceException {
        Set<ValidationInformation> validInfo = GameValidator.findGameValidationIssues(gameTitle, imagePath, description, priceValue, trailerLink);
        Set<String> valueValidInfo = validInfo.stream().map(ValidationInformation::getInfoValue).collect(Collectors.toSet());
        if (!valueValidInfo.isEmpty()) {
            return valueValidInfo;
        }
        int gameId = Integer.parseInt(gameIdValue);
        Optional<Game> gameById = findGameById(gameId);
        if (gameById.isPresent()) {
            BigDecimal price = new BigDecimal(priceValue);
            int[] genresId = Arrays.stream(genres).mapToInt(Integer::parseInt).toArray();
            int[] categoriesId = Arrays.stream(categories).mapToInt(Integer::parseInt).toArray();
            Game game = new Game.GameBuilder(gameTitle, imagePath).id(gameId)
                    .description(description)
                    .price(price)
                    .trailer(trailerLink).build();
            try {
                boolean gameEdited = gameDao.editGame(game, genresId, categoriesId);
                valueValidInfo.add(gameEdited ? ValidationInformation.SUCCESS.getInfoValue() : ValidationInformation.FAIL.getInfoValue());
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return valueValidInfo;
    }

    @Override
    public List<Game> filterAllGames(int startPrice, int endPrice, String[] categories, String[] genres, String sortValue) throws ServiceException {
        List<Game> result;
        Set<Game.Category> categorySet = Arrays.stream(categories)
                .map(category -> Game.Category.valueOf(category.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Game.Category.class)));
        Set<Game.Genre> genreSet = Arrays.stream(genres)
                .map(category -> Game.Genre.valueOf(category.toUpperCase(Locale.ROOT)))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Game.Genre.class)));
        result = findAllGames().stream()
                .filter(game -> (game.getPrice().setScale(0, RoundingMode.FLOOR).intValue() >= startPrice)
                        && (game.getPrice().setScale(0, RoundingMode.CEILING).intValue() <= endPrice)
                        && (game.getCategories().containsAll(categorySet))
                        && (game.getGenres().containsAll(genreSet)))
                .collect(Collectors.toList());
        orderGameList(result, sortValue);
        return result;
    }

    @Override
    public void orderGameList(List<Game> gameList, String sortByValue) {
        switch (sortByValue) {
            case "titleAsc" -> {
                gameList.sort(Comparator.comparing(o -> o.getTitle().toLowerCase(Locale.ROOT)));
            }
            case "titleDesc" -> {
                gameList.sort(Comparator.comparing(o -> o.getTitle().toLowerCase(Locale.ROOT)));
                Collections.reverse(gameList);
            }
            case "priceAsc" -> {
                gameList.sort(Comparator.comparing(Game::getPrice));
            }
            case "priceDesc" -> {
                gameList.sort(Comparator.comparing(Game::getPrice));
                Collections.reverse(gameList);
            }
        }
    }
}
