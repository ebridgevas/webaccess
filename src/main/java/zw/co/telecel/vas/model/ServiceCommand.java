package zw.co.telecel.vas.model;

/**
 * david@ebridgevas.com
 *
 */
public enum ServiceCommand {

    REGISTER_USER("user registration"),
    GENERATE_ACTIVATION_CODE("activation code generation"),
    ACTIVATE_USER("user activation"),
    CHANGE_USER_PASSWORD("password change"),
    RESET_USER_PASSWORD("password reset"),
    DATA_BUNDLE_PURCHASE("data bundle purchase"),
    DEACTIVATE_USER("user deletion"),
    DELETE_USER("user deletion");

    private String description;

    private ServiceCommand(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
