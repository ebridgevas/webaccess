/**
 * @author david@tekeshe.com
 */



var HTTP_URL = "http://localhost:8080/televas-httpbridge-1.0/";
var userSession;

var USER_ID_TYPE;
var subscribersMap;
var SERVICE_COMMANDS;
var serviceCommand;
var userPassword = "changeit";

function Controller() {

    USER_ID_TYPE = {
        MOBILE_NUMBER: {},
        EMAIL_ADDRESS: {}
    };

    SERVICE_COMMANDS = {
        REGISTER_SUBSCRIBER: {
            title : "Register Subscriber",
            buttonText : "Register",
            sServiceCommand: "register-subscriber",
            notificationMessage: "registered"
        },
        EDIT_SUBSCRIBER: {
            title : "Edit Subscriber",
            buttonText : "Save",
            sServiceCommand: "modify-subscriber",
            notificationMessage: "updated"
        },
        DELETE_SUBSCRIBER:{
            title : "Delete Subscriber",
            buttonText : "Delete",
            sServiceCommand: "delete-user",
            notificationMessage: "deleted"
        }
    };

    this.parseUserId = function( userId ) {

        if ( isValidMobileNumber( userId ) ) {
            userSession = new UserSession();
            userSession.setUserId( toStandardMobileNumberFormat( userId ) );
            userSession.userIdType = USER_ID_TYPE.MOBILE_NUMBER;
        } else if (isValidEmailAddress( userId ) ) {
            userSession = new UserSession();
            userSession.setUserId( userId );
            userSession.userIdType = USER_ID_TYPE.EMAIL_ADDRESS;
        } else {
            throw "Invalid user id.";
        }
    }

    this.retrieveSubscriberListing = function( startFrom, pageSize ) {

        var url =  HTTP_URL +
                   "user-account-manager?service-command=subscriber-listing&" +
                    "start-from=" + startFrom +
                    "&page-size=" + pageSize;

        $.getJSON(url, function( data ) {

            dataSet = view.toArray(data);
            controller.subscriberMap( data );
            controller.subscriberListing();
        });
    }

    this.subscriberMap = function ( subscribers ) {

        subscribersMap = new Object();
        $.each(subscribers, function(index, subscriber) {
            var subscriberId = toStandardMobileNumberFormat(subscriber.mobileNumber);
            subscribersMap[subscriberId] = subscriber;
        });
    }

    this.subscriberListing = function() {

        var table = view.registerTable(dataSet);
    }

    this.registerButtonEventHandler = function() {

        $('#title').text("Registered Subscribers");

        $('#register-subscriber-button').click(function(){
            controller.registrationForm();
        });
    }

    this.registrationForm = function( subscriberId ) {

        isTransactionConfirmed = false;

        $('.service-command-panel').css('display','none');
        $('#register-listing').empty();

        $('#title').text( serviceCommand.title );

        $('#register-listing').append(view.subscriberRegisterForm());
        $('#process-subscriber-registration').text(serviceCommand.buttonText);

        if ( serviceCommand == SERVICE_COMMANDS.EDIT_SUBSCRIBER ||
               serviceCommand == SERVICE_COMMANDS.DELETE_SUBSCRIBER ) {

            subscriberId = toStandardMobileNumberFormat(subscriberId);
            var subscriber = subscribersMap[ subscriberId ];
            view.primeSubscriberRegistrationForm( subscriber );
        }

        $('#process-subscriber-registration').click(function(e) {

            $('#confirmation-password-container').css('display', 'block');
            if ( $('#confirmation-password').val() && controller.isValidPassword( $('#confirmation-password').val()) ) {
                var subscriber = view.subscriber();
                if (subscriber != undefined ) {
                    controller.registerSubscriber(JSON.parse(subscriber), serviceCommand);
                }
            } else {
                $('#confirmation-password').val('');
                $('#confirmation-password').prop('placeholder','Enter your password to confirm.');
            }

        });

        $('#cancel-subscriber-registration').click(function(e){

            $('#register-listing').empty();
            controller.subscriberListing();
        });
    }

    this.isValidPassword = function( password ) {
        return userPassword == password;
    }

    this.registerSubscriber = function( subscriber, serviceCommand ) {

        var url =  HTTP_URL +
            "user-account-manager?service-command=" + serviceCommand.sServiceCommand +
            "&subscriberEmailAddress=" + subscriber.emailAddress +
            "&subscriberMobileNumber=" + subscriber.mobileNumber +
            "&subscriberFirstName=" + subscriber.firstName +
            "&subscriberSurname=" + subscriber.surname +
            "&subscriberRole=" + subscriber.role +
            "&subscriberNotificationAgent=" + subscriber.notificationAgent +
            "&subscriberSecurityQuestion=" + subscriber.securityQuestion +
            "&subscriberSecurityAnswer=" + subscriber.securityAnswer;

        var promise = $.getJSON(url, function( data ) {

                            dataSet = view.toArray(data);
                            controller.subscriberMap( data );
                            $('#notification-area').css('display', 'block');
                            $('#notification-area').text( "User: " + subscriber.firstName + " " +
                            subscriber.surname + " " + serviceCommand.notificationMessage + " successfully." );

                            $('#process-subscriber-registration').unbind();
                            if ( serviceCommand == SERVICE_COMMANDS.REGISTER_SUBSCRIBER ) {
                                $('#process-subscriber-registration').text("Create Another");
                            } else  if ( serviceCommand == SERVICE_COMMANDS.DELETE_SUBSCRIBER ) {
                                $('#process-subscriber-registration').css('display','none');
                            }
                            $('#process-subscriber-registration').click(function(){
                                controller.registrationForm();
                            });

                            $('#cancel-subscriber-registration').unbind();
                            $('#cancel-subscriber-registration').text('Close');
                            $('#cancel-subscriber-registration').click(function(){
                                dataSet = view.toArray(data);
                                controller.subscriberMap( data );
                                $('#register-listing').empty();
                                controller.subscriberListing();
                            });
                        });

        promise.fail(function(xhr, status, error) {
            $('#notification-area').css('display', 'block');
            $('#notification-area').text( xhr.responseText );
        });
    }
}