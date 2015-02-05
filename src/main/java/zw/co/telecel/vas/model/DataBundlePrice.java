package zw.co.telecel.vas.model;

import java.math.BigDecimal;

public class DataBundlePrice {

    private String bundleId;
    private String bundleDescription;
    private BigDecimal bundleSize;
    private BigDecimal bundleRate;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal bonusCredit;
    private BigDecimal outOfBundleRate;
    private Integer window;

    public DataBundlePrice() {
    }

    public DataBundlePrice(
            String bundleId,
            String bundleDescription,
            BigDecimal bundleSize,
            BigDecimal bundleRate,
            BigDecimal debit,
            BigDecimal credit,
            BigDecimal bonusCredit,
            BigDecimal outOfBundleRate,
            Integer window) {
        this.bundleId = bundleId;
        this.bundleDescription = bundleDescription;
        this.bundleSize = bundleSize;
        this.bundleRate = bundleRate;
        this.debit = debit;
        this.credit = credit;
        this.outOfBundleRate = outOfBundleRate;
        this.window = window;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getBundleDescription() {
        return bundleDescription;
    }

    public void setBundleDescription(String bundleDescription) {
        this.bundleDescription = bundleDescription;
    }

    public BigDecimal getBundleSize() {
        return bundleSize;
    }

    public void setBundleSize(BigDecimal bundleSize) {
        this.bundleSize = bundleSize;
    }

    public BigDecimal getBundleRate() {
        return bundleRate;
    }

    public void setBundleRate(BigDecimal bundleRate) {
        this.bundleRate = bundleRate;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getBonusCredit() {
        return bonusCredit;
    }

    public void setBonusCredit(BigDecimal bonusCredit) {
        this.bonusCredit = bonusCredit;
    }

    public BigDecimal getOutOfBundleRate() {
        return outOfBundleRate;
    }

    public void setOutOfBundleRate(BigDecimal outOfBundleRate) {
        this.outOfBundleRate = outOfBundleRate;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }
}