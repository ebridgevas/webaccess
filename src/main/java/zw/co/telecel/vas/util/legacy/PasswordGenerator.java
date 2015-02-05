package zw.co.telecel.vas.util.legacy;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * david@tekeshe.com
 */
public class PasswordGenerator {

    public static String  getRandomPassword() {
        StringBuffer password = new StringBuffer(RandomStringUtils.randomNumeric(12));
        for ( int idx = password.length() - 1; idx > 0; --idx) {
            if ( idx != 0 && idx % 4 == 0  ) {
                password.insert( idx, ' ');
            }
        }
        return password.toString();
    }

    public static void main(String[] args) {
        System.out.println(getRandomPassword());
    }
}
