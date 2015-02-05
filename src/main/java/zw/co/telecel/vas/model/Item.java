package zw.co.telecel.vas.model;

import java.math.BigDecimal;

/**
 * david@ebridgevas.com
 *
 */
public class Item {

    private String itemId;
    private String itemDescription;
    private BigDecimal duration;
    private BigDecimal sellingPrice;
    private String unitOfMeasure;
    private BigDecimal quantity;

    public Item() {
    }

    public Item(String itemId,
                String itemDescription,
                BigDecimal duration,
                BigDecimal sellingPrice,
                String unitOfMeasure,
                BigDecimal quantity) {

        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.duration = duration;
        this.sellingPrice = sellingPrice;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
