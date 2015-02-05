package zw.co.telecel.vas.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * david@ebridgevas.com
 *
 */
public class ServiceCommandResponse {

    private final String sourceId;
    private final String destinationId;
    private String narrative;
    private BigDecimal productSize;
    private BigDecimal sourceAccountCoreBalance;
    private BigDecimal beneficiaryAccountDataBalance;
    private Date beneficiaryAccountExpiryDate;
    private String responseCode;

    public ServiceCommandResponse(String sourceId, String destinationId) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
    }

    public ServiceCommandResponse(ServiceCommandRequest request) {
        destinationId = request.getDestinationId();
        sourceId = request.getUserId();
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setProductSize(BigDecimal productSize) {
        this.productSize = productSize;
    }

    public BigDecimal getProductSize() {
        return productSize;
    }

    public void setSourceAccountCoreBalance(BigDecimal sourceAccountCoreBalance) {
        this.sourceAccountCoreBalance = sourceAccountCoreBalance;
    }

    public BigDecimal getSourceAccountCoreBalance() {
        return sourceAccountCoreBalance;
    }

    public void setBeneficiaryAccountDataBalance(BigDecimal beneficiaryAccountDataBalance) {
        this.beneficiaryAccountDataBalance = beneficiaryAccountDataBalance;
    }

    public BigDecimal getBeneficiaryAccountDataBalance() {
        return beneficiaryAccountDataBalance;
    }

    public Date getBeneficiaryAccountExpiryDate() {
        return beneficiaryAccountExpiryDate;
    }

    public void setBeneficiaryAccountExpiryDate(Date beneficiaryAccountExpiryDate) {
        this.beneficiaryAccountExpiryDate = beneficiaryAccountExpiryDate;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
