package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.impl.GameDaoImpl;
import by.learning.web.model.entity.Game;
import by.learning.web.model.service.GameService;
import by.learning.web.validator.GameValidator;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GameServiceImpl implements GameService {
    private static final Logger logger = LogManager.getLogger();

    private static final String IMG_PROJECT_PATH = "/img/logo/";

    private final GameDao gameDao = GameDaoImpl.getInstance();

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

    //fixme create Validator
    @Override
    public Set<ValidationInformation> createGame(String gameTitle, String imagePath,
                                                 String description, String price, String trailerLink,
                                                 String[] genres, String[] categories) throws ServiceException {
        Set<ValidationInformation> validInfo = GameValidator.findGameValidationIssues(gameTitle, imagePath, description, price, trailerLink);
        if (!validInfo.isEmpty()) {
            return validInfo;
        }
        int[] genresId = Arrays.stream(genres).mapToInt(Integer::parseInt).toArray();
        int[] categoriesId = Arrays.stream(categories).mapToInt(Integer::parseInt).toArray();
        logger.log(Level.DEBUG, genresId);
        logger.log(Level.DEBUG, categoriesId);
        BigDecimal priceValue = new BigDecimal(price);
        imagePath = IMG_PROJECT_PATH + imagePath;
        Game game = new Game(gameTitle, imagePath, description, priceValue, trailerLink);
        try {
            boolean isCreated = gameDao.createGame(game, genresId, categoriesId);
            if (isCreated) {
                validInfo.add(ValidationInformation.SUCCESS);

            } else {
                validInfo.add(ValidationInformation.FAIL);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return validInfo;
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
}
