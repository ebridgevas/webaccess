package zw.co.telecel.vas.services.legacy.billing.util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;

import static javax.crypto.Cipher.getInstance;

/**
 * @author david@ebridgevas.com
 *
 */
public class SymmetricCipher {

    private static final Cipher CIPHER;
    private static final SecretKeySpec KEY;

    // http://en.wikipedia.org/wiki/Initialization_vector
    private static final IvParameterSpec IV_SPEC;

    static {
        // wrap key data in Key/IV specs to pass to cipher
        KEY = new SecretKeySpec("changeit".getBytes(), "DES");
        IV_SPEC = new IvParameterSpec(null);

        // create the cipher with the algorithm you choose
        CIPHER = cipherInstance();
    }

    private static Cipher cipherInstance() {
        try {
            return getInstance("DES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void encrypt() {
//        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//        byte[] encrypted= new byte[cipher.getOutputSize(input.length)];
//        int enc_len = cipher.update(input, 0, input.length, encrypted, 0);
//        enc_len += cipher.doFinal(encypted, enc_len);
    }

    public static void decrypt() {
//        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
//        byte[] decrypted = new byte[cipher.getOutputSize(enc_len)];
//        int dec_len = cipher.update(encrypted, 0, enc_len, decrypted, 0);
//        dec_len += cipher.doFinal(decrypted, dec_len);
    }
}
