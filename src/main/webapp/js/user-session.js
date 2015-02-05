/**
 *
 * user-session.js
 *
 * @author david@tekeshe.com
 *
 * Description:
 *   Determines next state using userCommand and known information.
 *
 * History:
 * Date         Author          Maintenance
 * ----         --------------  --------------------------------------------
 * 25 Jan 2014  David Tekeshe   Initial release
 *
 */

function UserSession() {

    var state;
    var userLoggedOn;
    var userId;
    var userName;
    var firstName;
    var lastName;
    var securityQuestion;
    var securityAnswer;
    var userEmail;
    var notificationAgent;
    var userRole;
    var beneficiaryMobileNumber;
    var amount;
    var userPassword;
    var userSessionId;

    var accountMap;
    var serviceCommand;
    var product;
    var paymentMethod;
    var userOneTimePassword;

    this.isUserLoggedOn = function() {
        return this.userLoggedOn;
    };

    this.setIsUserLoggedOn = function ( userLoggedOn) {
        this.userLoggedOn = userLoggedOn;
    };

    this.setUserId = function( userId ) {
        this.userId = userId;
    };

    this.getUserId = function () {
        return this.userId;
    };

    this.isUserIdSet = function () {
        return this.userId != null;
    };

    this.setUserName = function( userName ) {
        this.userName = userName;
    };

    this.getUserName = function() {
        return this.userName;
    };

    this.setFirstName = function( firstName ) {
        this.firstName = firstName;
    };

    this.getFirstName = function ( firstName ) {
        return this.firstName;
    };

    this.setLastName = function ( lastName ) {
        this.lastName = lastName;
    };

    this.getLastName = function() {
        return this.lastName;
    };

    this.setSecurityQuestion = function ( securityQuestion ) {
        this.securityQuestion = securityQuestion;
    };

    this.getSecurityQuestion = function() {
        return this.securityQuestion;
    };

    this.setSecurityAnswer = function ( securityAnswer ) {
        this.securityAnswer = securityAnswer;
    };

    this.getSecurityAnswer = function () {
        return this.securityAnswer;
    };

    this.setUserEmail = function ( userEmail) {
        this.userEmail = userEmail;
    };

    this.getUserEmail = function () {
        return this.userEmail;
    };

    this.setNotificationAgent = function ( notificationAgent ) {
        this.notificationAgent = notificationAgent;
    };

    this.getNotificationAgent = function () {
        return this.notificationAgent;
    };

    this.getBeneficiaryMobileNumber = function() {
        return this.beneficiaryMobileNumber;
    };

    this.setBeneficiaryMobileNumber = function(beneficiaryMobileNumber) {
        this.beneficiaryMobileNumber = beneficiaryMobileNumber;
    };

    this.isBeneficiaryOwnPhone = function () {
        return this.beneficiaryMobileNumber == this.userId;
    };

    this.getAmount = function () {
        return this.amount;
    };

    this.setAmount = function( amount ) {
        this.amount = amount;
    };

    this.setUserPassword = function ( userPassword ) {
        this.userPassword = userPassword;
    };

    this.getUserPassword = function () {
        return this.userPassword;
    };

    this.getSessionId = function() {
        return this.sessionId;
    };

    this.setSessionId = function( sessionId ) {
        this.sessionId = sessionId;
    };

    this.isUserPasswordSet = function () {
        this.userPassword != null;
    };

    this.getState = function () {
        return this.state;
    };

    this.setState = function ( state ) {
        this.state = state;
    };

    this.setUserRole = function ( userRole ) {
        this.userRole = userRole;
    };

    this.getUserRole = function() {
        return this.userRole;
    };

    this.getAccountMap = function() {
        return this.accountMap;
    };

    this.setAccountMap = function( accountMap) {
        this.accountMap = accountMap;
    };

    this.getServiceCommand = function() {
        return this.serviceCommand;
    };

    this.setServiceCommand = function ( serviceCommand ) {
        this.serviceCommand = serviceCommand;
    };

    this.setProduct = function ( product ) {
        this.product = product;
    };

    this.getProduct = function() {
        return this.product;
    };

    this.setPaymentMethod = function( paymentMethod ) {
        this.paymentMethod = paymentMethod;
    };

    this.getPaymentMethod = function() {
        return this.paymentMethod;
    };

    this.setOneTimePassword = function( oneTimePassword ) {
        this.oneTimePassword = oneTimePassword;
    };

    this.getOneTimePassword = function() {
        return this.oneTimePassword;
    };
}