package by.learning.web.model.entity;

import java.math.BigDecimal;
import java.util.HashMap;

public class ClientOrder {

    private int userId;
    private HashMap<Game, Integer> gameMap;
    private BigDecimal price;
    private Coupon coupon;

    public ClientOrder(int userId, HashMap<Game, Integer> gameMap, BigDecimal price) {
        this.userId = userId;
        this.gameMap = gameMap;
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public HashMap<Game, Integer> getGameMap() {
        return gameMap;
    }

    public void setGameMap(HashMap<Game, Integer> gameMap) {
        this.gameMap = gameMap;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientOrder order = (ClientOrder) o;

        if (userId != order.userId) return false;
        if (gameMap != null ? !gameMap.equals(order.gameMap) : order.gameMap != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        return coupon != null ? coupon.equals(order.coupon) : order.coupon == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (gameMap != null ? gameMap.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (coupon != null ? coupon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientOrder{");
        sb.append("userId=").append(userId);
        sb.append(", gameMap=").append(gameMap);
        sb.append(", price=").append(price);
        sb.append(", coupon=").append(coupon);
        sb.append('}');
        return sb.toString();
    }
}
