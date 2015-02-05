package zw.co.telecel.vas.dto;

import zw.co.telecel.vas.model.AccountTye;
import zw.co.telecel.vas.model.UserAction;

import java.io.Serializable;

/**
 * @author david@ebridgevas.com
 *
 */
public class UserAuthenticationResponse implements Serializable {

    private UserAction userAction;
    private String narrative;
    private String sessionId;
    private AccountTye accountType;
    private String mobileNumber;
    private String fullName;
    private String firstName;
    private String lastName;
    private String securityQuestion;
    private String securityAnswer;
    private String emailAddress;
    private String notificationAgent;
    private String role;
    private String password;
    private String userPhotoFilename;

    public UserAuthenticationResponse() {
    }

    public UserAuthenticationResponse(
                    UserAction userAction,
                    String narrative,
                    String sessionId) {

        this.userAction = userAction;
        this.narrative = narrative;
        this.sessionId = sessionId;
    }

    public UserAction getUserAction() {
        return userAction;
    }

    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setAccountType(AccountTye accountType) {
        this.accountType = accountType;
    }

    public AccountTye getAccountType() {
        return accountType;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setNotificationAgent(String notificationAgent) {
        this.notificationAgent = notificationAgent;
    }

    public String getNotificationAgent() {
        return notificationAgent;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserPhotoFilename() {
        return userPhotoFilename;
    }

    public void setUserPhotoFilename(String userPhotoFilename) {
        this.userPhotoFilename = userPhotoFilename;
    }
}
