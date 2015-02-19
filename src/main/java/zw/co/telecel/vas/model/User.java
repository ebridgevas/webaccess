package zw.co.telecel.vas.model;

import java.io.InputStream;
import java.sql.Date;

/**
 * david@ebridgevas.com
 *
 */
public class User {
    private String mobileNumber;
    private String emailAddress;
    private String password;
    private String firstName;
    private String surname;
    private String role;
    private String notificationAgent;
    private String status;
    private String narrative;
    private String activationCode;
    private String securityQuestion;
    private String securityAnswer;
    private String userPhotoFileName;
    private Integer wrongPasswordCounter;
    private Date wrongPasswordCounterDate;

    public User() {
    }

    public User(
            String emailAddress,
            String mobileNumber,
            String password,
            String firstName,
            String surname,
            String role,
            String notificationAgent,
            String status,
            String narrative,
            String activationCode,
            String securityQuestion,
            String securityAnswer
    ) {
            this.emailAddress = emailAddress;
            this.mobileNumber = mobileNumber;
            this.password = password;
            this.firstName = firstName;
            this.surname = surname;
            this.role = role;
            this.notificationAgent = notificationAgent;
            this.status = status;
            this.narrative = narrative;
            this.activationCode = activationCode;
            this.securityQuestion = securityQuestion;
            this.securityAnswer = securityAnswer;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNotificationAgent() {
        return notificationAgent;
    }

    public void setNotificationAgent(String notificationAgent) {
        this.notificationAgent = notificationAgent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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

    public String getUserPhotoFileName() {
        return userPhotoFileName;
    }

    public void setUserPhotoFileName(String userPhotoFileName) {
        this.userPhotoFileName = userPhotoFileName;
    }

    public Integer getWrongPasswordCounter() {
        return wrongPasswordCounter;
    }

    public void setWrongPasswordCounter(Integer wrongPasswordCounter) {
        this.wrongPasswordCounter = wrongPasswordCounter;
    }

    public Date getWrongPasswordCounterDate() {
        return wrongPasswordCounterDate;
    }

    public void setWrongPasswordCounterDate(Date wrongPasswordCounterDate) {
        this.wrongPasswordCounterDate = wrongPasswordCounterDate;
    }
}
