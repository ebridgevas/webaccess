/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 3/5/14
 * Time: 9:09 PM
 * To change this template use File | Settings | File Templates.
 */

function send() {

    if ( ! $('#message-composer').val() ) {
        addMessage("A blank message is not allowed.<br/>Please type something and send.", true);
        $('#message-composer').focus();

    }  else {
        if ( ! userSession.isUserLoggedOn() ) {
            login();
        } else if ( userSession.getServiceCommand() == SERVICE_COMMAND.DATA_BUNDLE_PURCHASE ) {
            processDataBundlePurchaseRequest();
        }

    }
}