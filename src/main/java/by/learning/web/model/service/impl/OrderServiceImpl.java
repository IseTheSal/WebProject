package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.dao.impl.GameDaoImpl;
import by.learning.web.model.dao.impl.OrderDaoImpl;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.OrderService;
import by.learning.web.util.MailSender;
import by.learning.web.validator.OrderValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();

    private final GameDao gameDao = GameDaoImpl.getInstance();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();


    public Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException {
        Optional<Coupon> result = Optional.empty();
        boolean couponValid = OrderValidator.isCouponValid(code);
        logger.log(Level.DEBUG, " VALID {}", couponValid);
        logger.log(Level.DEBUG, " CODE {}", code);
        if (couponValid) {
            try {
                result = orderDao.findAvailableCouponByCode(code);
                if (result.isPresent()) {
                    logger.log(Level.INFO, "coupon with name {} exist", code);
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public boolean isGameInStock(int gameId) throws ServiceException {
        boolean result;
        try {
            int gameAmount = gameDao.findGameCount(gameId);
            if (gameAmount > 0) {
                result = true;
            } else {
                result = false;
                logger.log(Level.INFO, "Game with id {} out of stock", gameId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public void addGameToCart(Game game, HashMap<Game, Integer> hashMap) {
        if (hashMap.containsKey(game)) {
            Integer amount = hashMap.get(game);
            amount++;
            logger.log(Level.INFO, amount + " " + game.getTitle() + " was added to cart");
            hashMap.put(game, amount);
        } else {
            hashMap.put(game, 1);
        }
    }

    @Override
    public int countCartAmount(HashMap<Game, Integer> hashMap) {
        int totalAmount = 0;
        Collection<Integer> gameAmount = hashMap.values();
        for (Integer amount : gameAmount) {
            totalAmount += amount;
        }
        return totalAmount;
    }

    @Override
    public void removeGameFromCart(HashMap<Game, Integer> cartMap, int gameId) throws ServiceException {
        try {
            Optional<Game> gameById = gameDao.findGameById(gameId);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                cartMap.remove(game);
                logger.log(Level.INFO, "{} was removed from cart", game.getTitle());
            } else {
                logger.log(Level.INFO, "game with id - {} was not found", gameId);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void changeGameCartAmount(HashMap<Game, Integer> cartMap, int gameId, String operation) throws
            ServiceException {
        try {
            Optional<Game> gameById = gameDao.findGameById(gameId);
            if (gameById.isPresent()) {
                Game game = gameById.get();
                if (cartMap.containsKey(game)) {
                    changeAmount(cartMap, game, operation);
                } else {
                    logger.log(Level.INFO, "Cart map haven`t game {}", game);
                }
            } else {
                logger.log(Level.INFO, "This id game {} was not found", gameById);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void changeAmount(HashMap<Game, Integer> cartMap, Game game, String operation) throws
            ServiceException {
        Integer amount = cartMap.get(game);
        switch (operation) {
            case "+" -> {
                amount++;
                cartMap.put(game, amount);
            }
            case "-" -> {
                if (amount == 1) {
                    removeGameFromCart(cartMap, game.getId());
                } else {
                    amount--;
                    cartMap.put(game, amount);
                }
            }
        }
    }

    @Override
    public int findAvailableCouponAmount(String codeName) throws ServiceException {
        int amount = 0;
        if (OrderValidator.isCouponValid(codeName)) {
            try {
                amount = orderDao.findAvailableCouponAmountByName(codeName);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            logger.log(Level.INFO, "coupon with code {} not valid", codeName);
        }
        return amount;
    }

    @Override
    public short findCouponDiscount(String codeName) throws ServiceException {
        short discount = 0;
        if (OrderValidator.isCouponValid(codeName)) {
            try {
                discount = orderDao.findCouponDiscountByName(codeName);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return discount;
    }

    @Override
    public void increaseCouponAmount(String codeName, int amount) throws ServiceException {
        try {
            orderDao.changeCouponAmountByName(codeName, amount, true);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void decreaseCouponAmount(String codeName, int amount) throws ServiceException {
        try {
            orderDao.changeCouponAmountByName(codeName, amount, false);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private int findAvailableGameAmount(int gameId) throws ServiceException {
        int amount;
        try {
            amount = orderDao.findAvailableGameAmountById(gameId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return amount;
    }

    private BigDecimal countOrderPrice(HashMap<Game, Integer> cartMap) {
        BigDecimal totalPrice = new BigDecimal("0.0");
        Set<Game> gameKeySet = cartMap.keySet();
        int size = gameKeySet.size();
        Game[] games = gameKeySet.toArray(new Game[size]);
        for (Game currentGame : games) {
            Integer amount = cartMap.get(currentGame);
            BigDecimal gamePrice = currentGame.getPrice();
            BigDecimal totalGamePrice = gamePrice.multiply(new BigDecimal(amount));
            totalPrice = totalPrice.add(totalGamePrice);
        }
        return totalPrice;
    }

    private BigDecimal countPriceDiscount(BigDecimal price, Coupon coupon) {
        short discount = 0;
        if (coupon != null) {
            discount = coupon.getDiscount();
        }
        BigDecimal totalPrice = new BigDecimal(String.valueOf(price));
        BigDecimal discountPercent = BigDecimal.valueOf((double) discount / 100);
        totalPrice = totalPrice.subtract(totalPrice.multiply(discountPercent));
        return totalPrice;
    }

    private Set<Integer> createGameIdSet(HashMap<Game, Integer> cartMap) {
        Set<Integer> gameIdSet = new HashSet<>();
        Set<Game> gameKeySet = cartMap.keySet();
        gameKeySet.forEach(game -> gameIdSet.add(game.getId()));
        return gameIdSet;
    }

    @Override
    public boolean createOrder(int userId, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException {
        boolean isCreated = false;
        Set<Integer> gameIdSet = createGameIdSet(cartMap);
        List<Game> gameList = new ArrayList<>(cartMap.keySet());
        boolean amountValid = true;
        for (Game game : gameList) {
            Integer gameCartAmount = cartMap.get(game);
            int availableGameAmount = findAvailableGameAmount(game.getId());
            if (availableGameAmount < gameCartAmount) {
                amountValid = false;
                if (availableGameAmount > 0) {
                    cartMap.put(game, availableGameAmount);
                } else {
                    removeGameFromCart(cartMap, game.getId());
                }
            }
        }
        if (amountValid) {
            BigDecimal orderPrice = countOrderPrice(cartMap);
            BigDecimal totalPrice = countPriceDiscount(orderPrice, coupon);
            ClientOrder order = new ClientOrder(userId, gameIdSet, totalPrice);
            order.setCoupon(coupon);
            try {
                isCreated = orderDao.createOrder(order);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isCreated;
    }

    @Override
    public void sendGameCodeToUser(HashMap<Game, Integer> cartMap, User user) throws ServiceException {
        List<Game> gameList = new ArrayList<>(cartMap.keySet());
        for (Game game : gameList) {
            Integer amount = cartMap.get(game);
            try {
                List<String> gameCodeList = orderDao.findLimitedGameCodes(game.getId(), amount);
                if (!gameCodeList.isEmpty()) {
                    orderDao.putSoldGameCodeList(gameCodeList);
                }
                MailSender mailSender = new MailSender();
                String body = convertCodeListToMessage(gameCodeList);
                String email = user.getEmail();
                String title = game.getTitle();
                mailSender.sendMessage(email, title, body);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
    }

    private String convertCodeListToMessage(List<String> codeList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String code : codeList) {
            stringBuilder.append(code).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public BigDecimal findOrderPrice(int userId) throws ServiceException {
        BigDecimal result;
        try {
            result = orderDao.findOrderPriceByUserId(userId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        return result;
    }

    @Override
    public List<Game> findOrderHistory(int userId) throws ServiceException {
        List<Game> result;
        try {
            result = orderDao.findOrderHistoryByUserId(userId);
        } catch (DaoException ex) {
            throw new ServiceException(ex);
        }
        return result;
    }
}
