package by.learning.web.model.entity;

import java.math.BigDecimal;
import java.util.Set;

public class ClientOrder {

    private int userId;
    private Set<Integer> gameIdSet;
    private BigDecimal price;
    private Coupon coupon;

    public ClientOrder(int userId, Set<Integer> gameIdSet, BigDecimal price) {
        this.userId = userId;
        this.gameIdSet = gameIdSet;
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public Set<Integer> getGameIdSet() {
        return gameIdSet;
    }

    public void setGameIdSet(Set<Integer> gameIdSet) {
        this.gameIdSet = gameIdSet;
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

        ClientOrder that = (ClientOrder) o;

        if (userId != that.userId) return false;
        if (gameIdSet != null ? !gameIdSet.equals(that.gameIdSet) : that.gameIdSet != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return coupon != null ? coupon.equals(that.coupon) : that.coupon == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (gameIdSet != null ? gameIdSet.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (coupon != null ? coupon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClientOrder{");
        sb.append("userId=").append(userId);
        sb.append(", gameIdSet=").append(gameIdSet);
        sb.append(", price=").append(price);
        sb.append(", coupon=").append(coupon);
        sb.append('}');
        return sb.toString();
    }
}
