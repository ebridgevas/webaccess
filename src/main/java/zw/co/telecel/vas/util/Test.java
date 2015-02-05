package zw.co.telecel.vas.util;

import zw.co.telecel.vas.util.legacy.PasswordGenerator;

/**
 * @author david@ebridgevas.com
 *
 */
public class Test {

    public static void main(String[] args) {
        String securityCode = new PasswordGenerator().getRandomPassword();
        String mobileNumber = "26373383480";
        String sessionId = securityCode + "#" + mobileNumber;

        System.out.println(sessionId);

        String[] tokens = sessionId.split("#");
        System.out.println("securityCode : " + tokens[0]);
        System.out.println("mobile : " + tokens[1]);
    }
}
