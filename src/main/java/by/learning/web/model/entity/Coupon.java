package by.learning.web.model.entity;

import java.util.Locale;

public class Coupon {

    private int id;
    private short discount;
    private String codeName;
    private int amount;

    public Coupon(short discount, String codeName, int amount) {
        this.discount = discount;
        this.codeName = codeName.toUpperCase(Locale.ROOT);
        this.amount = amount;
    }

    public Coupon(int id, short discount, String codeName, int amount) {
        this.id = id;
        this.discount = discount;
        this.codeName = codeName.toUpperCase(Locale.ROOT);
        this.amount = amount;
    }

    public Coupon(int id, short discount, String codeName) {
        this.id = id;
        this.discount = discount;
        this.codeName = codeName.toUpperCase(Locale.ROOT);
    }

    public int getId() {
        return id;
    }

    public short getDiscount() {
        return discount;
    }

    public void setDiscount(short discount) {
        this.discount = discount;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coupon coupon = (Coupon) o;

        if (id != coupon.id) return false;
        if (discount != coupon.discount) return false;
        if (amount != coupon.amount) return false;
        return codeName != null ? codeName.equals(coupon.codeName) : coupon.codeName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) discount;
        result = 31 * result + (codeName != null ? codeName.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Coupon{");
        sb.append("id=").append(id);
        sb.append(", discount=").append(discount);
        sb.append(", codeName='").append(codeName).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
