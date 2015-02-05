/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 2/23/14
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */

function showPasswordPrompt(title, prompt, buttonText) {
    $('#security-panel-title').text(title);
    $('#password').attr('placeholder', prompt);
    $('#password').val('');
    $('#password').css('display','block');
    $('#password').attr('type', 'password');
    $('#register-user').css('display','none');
    $('#forgot-password').css('display','inline-block');
    $('#login-text').text( buttonText );
}

function showActivateCodePrompt(prompt) {

    $('#password').attr('placeholder', prompt);
    $('#password').attr('type', 'text');
    $('#password').css('display','block');
    $('#recover-security-token').text('Generate new activation code.');
    $('#recover-security-token').css('display','block');
    $('#loginButton').text('Activate');
    $('#security-panel-title').text('Activate Subscription');
}

function showUnRegisteredUserError() {
    $('#user-id').val('');
    $('#user-id').attr('placeholder', 'Enter a registered mobile');
}