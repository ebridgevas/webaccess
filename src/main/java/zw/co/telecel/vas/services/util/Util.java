package zw.co.telecel.vas.services.util;

import com.comverse_in.prepaid.ccws.BalanceCreditAccount;
import org.apache.log4j.Category;
import zw.co.telecel.dto.DataBundleDTO;
import zw.co.telecel.dto.LanguageDTO;
import zw.co.telecel.vas.dao.TxnDAO;
import zw.co.telecel.vas.dto.TxnDto;
import zw.co.telecel.vas.model.BalanceDTO;
import zw.co.telecel.vas.util.TransactionException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * @author david@tekeshe.com
 */
public class Util {

    private static Category CAT = Category.getInstance(Util.class);

    public static BalanceDTO balance(String balanceName, Set<BalanceDTO> balances) throws TransactionException {

        for ( BalanceDTO balance : balances ) {

            if (balanceName.equalsIgnoreCase(balance.getBalanceName().getSystemValue())) {
                return balance;
            }
        }

        throw new TransactionException("Missing account.");
    }

    public static BalanceDTO dataBalance(Set<BalanceDTO> balances) throws TransactionException {

        for ( BalanceDTO balance : balances ) {
            CAT.debug("Balance Name: " + balance.getBalanceName().getSystemValue() +
                    "unit of measure : " + balance.getBalanceName().getUnitOfMeasure() );
            if ("megabyte".equalsIgnoreCase(balance.getBalanceName().getUnitOfMeasure())) {
                return balance;
            }
        }

        BalanceDTO result = new BalanceDTO();

        result.setBalanceName( new LanguageDTO("Gprs_bundle", "Data Account", "TEL_COS",2, "megabyte") );
        result.setBalance(BigDecimal.ZERO);
        result.setExpiryDate(Calendar.getInstance());

        return result;
//        throw new TransactionException("Missing account.");
    }

    public static String[] dataBundleResponse( BalanceDTO[] balances, DataBundleDTO dataBundle, String paymentMethod)
            throws TransactionException {

        Boolean ownPhone = balances[0].getMobileNumber().equals( balances[1].getMobileNumber());

        String[] result = new String[ ownPhone ? 1 : 2 ];
        result[0] =
                "You have bought the " + dataBundle.getBundleSize().setScale(2, RoundingMode.HALF_UP ) + "mb bundle" +
                ( ! ownPhone ? " for 0" + balances[1].getMobileNumber().substring(3) + "" : "") +
                        ( "TELECASH".equalsIgnoreCase(paymentMethod) ?
                                " from your Telecash account." :
                                ". Your main balance is now " + balances[0].getBalance() + "usd. ") +
                ( ownPhone ?
                    "Your data balance is " + balances[ balances.length - 1 ].getBalance() +
                    "mb exp on " +
                    String.format("%1$td/%1$tm/%1$tY", balances[ balances.length - 1 ].getExpiryDate()) : "" );

        if ( ! ownPhone ) {
            result[1] =
                    "You received " + dataBundle.getBundleSize().setScale(2, RoundingMode.HALF_UP) + "mb bundle " +
                    "from 0" + balances[0].getMobileNumber().substring(3) +
                    ". Your data balance is now " + balances[1].getBalance() +
                    "mb exp on " +
                    String.format("%1$td/%1$tm/%1$tY", balances[ 1 ].getExpiryDate() );
        }

        return result;
    }

    public static String[] balanceTransferResponse( BalanceDTO[] balances, BigDecimal amount, String uuid ) {

        String[] result = new String[2];
        result[0] = "$" + amount + " transfer to 0" + balances[1].getMobileNumber().substring(3) + " accepted. " +
                        "Your balance is now: $" + balances[0].getBalance() +
                        ". Reference: " + uuid;

        result[1] = "$" + amount + " transfer from 0" + balances[0].getMobileNumber().substring(3) + " accepted. " +
                "Your balance is now: $" + balances[1].getBalance() +
                ". Reference: " + uuid;

        return result;
    }

    public static String[] voucherRechargeResponse( String uuid,
                                                    String mobileNumber,
                                                    BalanceDTO balance ) {

        String[] result = null;
        if ( mobileNumber.substring(3).equals( balance.getMobileNumber() ) ) {
            result = new String[1];

            result[0] = "$" + new DecimalFormat("###,##.00").format( balance.getOtherAmount().doubleValue() ) +
                            " recharge voucher accepted. Your balance is now " +
                            new DecimalFormat("###,##.00").format(balance.getBalance().doubleValue()) +
                            ". Reference: " + uuid;
        } else {
            result = new String[2];

            result[0] = "$" + new DecimalFormat("###,##.00").format( balance.getOtherAmount().doubleValue() ) +
                            " recharge voucher for 0" + balance.getMobileNumber() + " accepted." +
                            " Reference: " + uuid;

            result[1] = "$" + new DecimalFormat("###,##.00").format( balance.getOtherAmount().doubleValue() ) +
                            " recharge voucher from 0" + mobileNumber.substring(3) + " accepted. Your balance is now " +
                            new DecimalFormat("###,##.00").format(balance.getBalance().doubleValue());
        }

        return result;
    }

    public static void persistDataBundleResponse(String uuid, BalanceDTO[] balances, DataBundleDTO dataBundle) {
        System.out.print("Persisting txn ...");
        TxnDto txn = new TxnDto(new BigInteger(uuid), "WEB", balances[0].getMobileNumber(), "WEB", new Date());
        txn.setDestinationId( balances[1 ].getMobileNumber() );
        txn.setProductCode(dataBundle.getBundleType());
        txn.setAmount(dataBundle.getDebit());
        txn.setTransactionType("DataBundlePurchase");
        txn.setStatusCode("000");
        txn.setAccountType(balances[0].getSubscriberPackage());
        txn.setSourceBalance( balances [ 0 ].getBalance() );
        txn.setBeneficiaryBalance(  balances[ 1 ].getBalance() );
        System.out.println(" mobile number : " + balances[0].getMobileNumber() + ", amount : " + dataBundle.getDebit());
        TxnDAO.persist(txn);
    }

    public static void persistBalanceTransferResponse(BalanceDTO[] balances, BigDecimal amount, String uuid ) {

        TxnDto txn = new TxnDto(new BigInteger(uuid), "WEB", balances[0].getMobileNumber(), "WEB", new Date());
        txn.setDestinationId( balances[ 1 ].getMobileNumber());
        txn.setProductCode("AirtimeTransfer");
        txn.setAmount(amount);
        txn.setTransactionType("AirtimeTransfer");
        txn.setStatusCode("000");
        txn.setAccountType("PREPAID");
        txn.setSourceBalance( balances[ 0 ].getBalance() );
        txn.setBeneficiaryBalance( balances [ 1 ].getBalance() );

        TxnDAO.persist(txn);
    }

    public static void persistVoucherRechargeResponse( String uuid, String mobileNumber, BalanceDTO balance ) {

        TxnDto txn = new TxnDto(new BigInteger(uuid), "WEB", mobileNumber, "WEB", new Date());
        txn.setDestinationId( balance.getMobileNumber());
        txn.setProductCode("VoucherRecharge");
        txn.setAmount(balance.getOtherAmount());
        txn.setTransactionType("VoucherRecharge");
        txn.setStatusCode("000");
        txn.setAccountType("PREPAID");

        Boolean ownPhone = mobileNumber.equals( balance.getMobileNumber() );
        txn.setSourceBalance( ownPhone ? balance.getBalance() : BigDecimal.ZERO );
        txn.setBeneficiaryBalance( balance.getBalance() );

        TxnDAO.persist(txn);
    }

    public static Set<TxnDto> transactionHistory( String mobileNumber ) {
        return TxnDAO.history( mobileNumber );
    }
}
