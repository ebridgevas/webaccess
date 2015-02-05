package zw.co.telecel.vas.util.legacy;

/**
 *
 * @author DaTekeshe
 */
public class MobileNumberFormatter {

    // 07369729644
    // 263737379760
    // 0737379760
    public static String format( String mobileNumber ) throws InvalidMobileNumberException {

        try { mobileNumber = mobileNumber.replaceAll(" ", "");} catch(Exception e){}
        byte len = (byte)mobileNumber.length();
        switch ( len ) {
            case 8: if ( mobileNumber.startsWith("23") ) { break; }
            case 9: if ( mobileNumber.startsWith("023") || mobileNumber.startsWith("73") ) { break; }
            case 10: if ( mobileNumber.startsWith("073") ) { break; }
            case 11: if ( mobileNumber.startsWith("26323") ) { break; }
            case 12: if ( mobileNumber.startsWith("26373") ) { break; }
            default:
                throw new InvalidMobileNumberException("Mobile number " + mobileNumber + " length is invalid.");
        }
        if (mobileNumber.startsWith("26373") ) {
            return mobileNumber;
        } else {
            return "26373" + mobileNumber.substring( len - 7 );
        }
    }
    public static String shortFormat( String mobileNumber ) throws InvalidMobileNumberException {
        String formattedMobileNumber = format(mobileNumber);
        return "0" + mobileNumber.substring(3);
    }
}
