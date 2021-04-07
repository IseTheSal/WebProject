package by.learning.web.model.service.impl;

import by.learning.web.exception.DaoException;
import by.learning.web.exception.ServiceException;
import by.learning.web.model.dao.GameDao;
import by.learning.web.model.dao.OrderDao;
import by.learning.web.model.dao.UserDao;
import by.learning.web.model.dao.impl.DaoInstance;
import by.learning.web.model.entity.ClientOrder;
import by.learning.web.model.entity.Coupon;
import by.learning.web.model.entity.Game;
import by.learning.web.model.entity.User;
import by.learning.web.model.service.OrderService;
import by.learning.web.util.MailSender;
import by.learning.web.validator.OrderValidator;
import by.learning.web.validator.ValidationInformation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>Order service implementation.</pre>
 *
 * @author Illia Aheyeu
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();

    private final GameDao gameDao = DaoInstance.INSTANCE.getGameDao();
    private final OrderDao orderDao = DaoInstance.INSTANCE.getOrderDao();
    private final UserDao userDao = DaoInstance.INSTANCE.getUserDao();

    public Optional<Coupon> findAvailableCouponByCode(String code) throws ServiceException {
        Optional<Coupon> result = Optional.empty();
        boolean couponValid = OrderValidator.isCouponCodeValid(code);
        if (couponValid) {
            code = code.toUpperCase(Locale.ROOT);
            try {
                result = orderDao.findAvailableCoupon(code);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public Optional<Coupon> findCouponByCode(String code) throws ServiceException {
        Optional<Coupon> result = Optional.empty();
        boolean couponValid = OrderValidator.isCouponCodeValid(code);
        if (couponValid) {
            code = code.toUpperCase(Locale.ROOT);
            try {
                result = orderDao.findCouponByCode(code);
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
            int gameAmount = gameDao.findGameAmount(gameId);
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
    public int findCodeAmount(int gameId) throws ServiceException {
        int amount;
        try {
            amount = gameDao.findGameAmount(gameId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return amount;
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
    public int countCartAmount(HashMap<Game, Integer> cartMap) {
        int totalAmount = 0;
        Collection<Integer> gameAmount = cartMap.values();
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
    public boolean decreaseAvailableCouponAmount(String codeName, int decreaseAmount) throws ServiceException {
        boolean isDecreased = false;
        if (OrderValidator.isCouponCodeValid(codeName)) {
            try {
                isDecreased = orderDao.decreaseAvailableCouponAmount(codeName, decreaseAmount);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isDecreased;
    }

    @Override
    public short findCouponDiscount(String codeName) throws ServiceException {
        short discount = 0;
        if (OrderValidator.isCouponCodeValid(codeName)) {
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

    @Override
    public boolean createOrder(int userId, HashMap<Game, Integer> cartMap, Coupon coupon) throws ServiceException {
        if (cartMap.isEmpty()) {
            return false;
        }
        boolean isCreated = false;
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
            ClientOrder order = new ClientOrder(userId, cartMap, totalPrice);
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

    @Override
    public Set<String> addGameCode(String gameIdValue, String code) throws ServiceException {
        boolean isCodeValid = OrderValidator.isGameCodeValid(code);
        Set<String> validInfo = new HashSet<>();
        boolean dataValid = true;
        if (!isCodeValid) {
            validInfo.add(ValidationInformation.GAME_CODE_INCORRECT.getInfoValue());
            dataValid = false;
        }
        int gameId = Integer.parseInt(gameIdValue);
        if (gameId < 0) {
            validInfo.add(ValidationInformation.GAME_ID_DOES_NOT_EXIST.getInfoValue());
            dataValid = false;
        }
        if (dataValid) {
            Optional<Game> gameById;
            try {
                gameById = gameDao.findGameById(gameId);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            if (gameById.isEmpty()) {
                validInfo.add(ValidationInformation.GAME_ID_DOES_NOT_EXIST.getInfoValue());
                dataValid = false;
            }
        }
        if (!dataValid) {
            validInfo.add(ValidationInformation.FAIL.getInfoValue());
            return validInfo;
        }
        try {
            boolean codeAdded = orderDao.addGameCode(gameId, code);
            if (codeAdded) {
                validInfo.add(ValidationInformation.SUCCESS.getInfoValue());
            } else {
                validInfo.add(ValidationInformation.FAIL.getInfoValue());
                validInfo.add(ValidationInformation.CODE_EXISTS.getInfoValue());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return validInfo;
    }

    @Override
    public List<Coupon> findAllCoupons() throws ServiceException {
        List<Coupon> allCoupons;
        try {
            allCoupons = orderDao.findAllCoupons();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return allCoupons;
    }

    @Override
    public Set<String> createCoupon(String discountValue, String code, String amountValue) throws ServiceException {
        Set<ValidationInformation> validationInformation = OrderValidator.isCouponValid(code, discountValue, amountValue);
        Set<String> valueValidInfo = validationInformation.stream().map(ValidationInformation::getInfoValue).collect(Collectors.toSet());
        if (!valueValidInfo.isEmpty()) {
            return valueValidInfo;
        }
        short discount = Short.parseShort(discountValue);
        int amount = Integer.parseInt(amountValue);
        code = code.toUpperCase(Locale.ROOT);
        Coupon coupon = new Coupon(discount, code, amount);
        try {
            boolean isCreated = orderDao.createCoupon(coupon);
            if (isCreated) {
                valueValidInfo.add(ValidationInformation.SUCCESS.getInfoValue());
            } else {
                valueValidInfo.add(ValidationInformation.LOGIN_OR_EMAIL_EXIST.getInfoValue());
                valueValidInfo.add(ValidationInformation.FAIL.getInfoValue());
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return valueValidInfo;
    }

    @Override
    public boolean deleteCoupon(String code) throws ServiceException {
        boolean isDeleted = false;
        if (OrderValidator.isCouponCodeValid(code)) {
            try {
                isDeleted = orderDao.deleteCoupon(code);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return isDeleted;
    }

    @Override
    public HashMap<User, ClientOrder> findAllOrders() throws ServiceException {
        HashMap<User, ClientOrder> result = new HashMap<>();
        try {
            Set<User> allClients = userDao.findAllClients();
            for (User client : allClients) {
                List<Game> clientGames = orderDao.findOrderHistoryByUserId(client.getId());
                BigDecimal price = orderDao.findOrderPriceByUserId(client.getId());
                HashMap<Game, Integer> gameMap = gameListToMap(clientGames);
                ClientOrder clientOrder = new ClientOrder(client.getId(), gameMap, price);
                result.put(client, clientOrder);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private HashMap<Game, Integer> gameListToMap(List<Game> gameList) {
        HashMap<Game, Integer> result = new HashMap<>();
        for (Game game : gameList) {
            if (result.containsKey(game)) {
                result.put(game, result.get(game) + 1);
            } else {
                result.put(game, 1);
            }
        }
        return result;
    }
}
