package zw.co.telecel.vas.model;

/**
 * david@ebridgevas.com
 *
 */
public class MTSM {

    private String destinationAddress;
    private String sourceAddress;
    private String shortMessage;

    public MTSM(String destinationAddress, String sourceAddress, String shortMessage) {
        this.destinationAddress = destinationAddress;
        this.sourceAddress = sourceAddress;
        this.shortMessage = shortMessage;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getShortMessage() {
        return shortMessage;
    }
}
