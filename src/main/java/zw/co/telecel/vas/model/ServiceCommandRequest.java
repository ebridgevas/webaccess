package zw.co.telecel.vas.model;

/**
 * david@ebridgevas.com
 *
 */
public class ServiceCommandRequest {

    private final ServiceCommand serviceCommand;

    private final String userId;
    private final IdType idType;
    private final String destinationId;
    private final String productCode;
    private final String sessionId;

    private final String payload;

    public ServiceCommandRequest( ServiceCommand serviceCommand,
                                  String userId,
                                  IdType idType,
                                  String payload) {
        this(serviceCommand, userId, idType, null, null, null, payload);
    }

    public ServiceCommandRequest( ServiceCommand serviceCommand,
                                  String userId,
                                  IdType idType,
                                  String destinationId,
                                  String productCode,
                                  String sessionId,
                                  String payload) {

        this.userId = userId;
        this.idType = idType;
        this.destinationId = destinationId;
        this.productCode = productCode;
        this.sessionId = sessionId;
        this.payload = payload;
        this.serviceCommand = serviceCommand;
    }

    public String getUserId() {
        return userId;
    }

    public IdType getIdType() {
        return idType;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPayload() {
        return payload;
    }

    public ServiceCommand getServiceCommand() {
        return serviceCommand;
    }

    /**
     * Is this a request to benefit own phone.
     *
     * @return
     */
    public Boolean isBeneficiaryOwnPhone() {
        return userId.equals( destinationId );
    }
}
