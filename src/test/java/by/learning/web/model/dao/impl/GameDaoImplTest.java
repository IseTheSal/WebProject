package by.learning.web.model.dao.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.entity.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Optional;

public class GameDaoImplTest {
    final Logger logger = LogManager.getLogger();
    GameDao gameDao = DaoInstance.INSTANCE.getGameDao();

    int allGenresAmount;
    int allCategoriesAmount;

    @BeforeTest
    public void fillGameAttributes() {
        allCategoriesAmount = 5;
        allGenresAmount = 15;
    }

    @Test
    public void testFindAllGames() {
        try {
            int gameId = 1;
            String gameTitle = "Assassin`s Creed: VALHALLA";
            Optional<Game> gameById = gameDao.findGameById(gameId);
            if (gameById.isEmpty() || !(gameById.get().getTitle().equals(gameTitle))) {
                Assert.fail();
            }
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFindAllGenres() {
        try {
            HashMap<Integer, String> allGenres = gameDao.findAllGenres();
            int genresAmount = allGenres.size();
            Assert.assertEquals(genresAmount, allGenresAmount);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFindAllCategories() {
        try {
            HashMap<Integer, String> allCategories = gameDao.findAllCategories();
            int categoriesAmount = allCategories.size();
            Assert.assertEquals(categoriesAmount, allCategoriesAmount);
        } catch (DaoException e) {
            Assert.fail();
        }
    }

    @AfterTest
    public void removeGameAttributes() {
        allCategoriesAmount = 0;
        allGenresAmount = 0;
    }
}