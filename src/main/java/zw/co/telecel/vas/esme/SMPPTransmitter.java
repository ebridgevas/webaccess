package zw.co.telecel.vas.esme;

import com.ebridge.sdp.smpp.*;
import com.ebridge.sdp.smpp.pdu.BindResponse;
import com.ebridge.sdp.smpp.pdu.PDUException;
import com.ebridgevas.esme.net.SmppBinder;
import com.ebridgevas.model.SmppConfig;

import java.io.IOException;

/**
 * @author david@ebridgevas.com
 *
 */

public class SMPPTransmitter {

    private Session ussdSession;

    public SMPPTransmitter(SmppConfig ussdConfig, String commandLine, Boolean withPromotion, String channelType) {
        System.out.println(" ver : 13.08 started.");
        bind(ussdConfig, commandLine, withPromotion, channelType);
    }

    private void bind(SmppConfig ussdConfig, String commandLine, Boolean withPromotion, String channelType) {

        Long timeout = new Long(20 * 1000);

        TCPIPConnection ussdConnection = new TCPIPConnection(ussdConfig.getSmppIPAdress(), ussdConfig.getSmppPort());
        ussdConnection.setReceiveTimeout( timeout );
        ussdSession = new Session(ussdConnection);

        SmppBinder ussdBinder = new SmppBinder(ussdSession, ussdConfig, new ServerPDUEventListenerImpl(ussdSession));
        BindResponse ussdBindResponse = null;
        while (true) {
            try {
                ussdBindResponse = ussdBinder.bind();
                System.out.println("########### ussdBindResponse.debugString() = " + ussdBindResponse.debugString());
                System.out.println(ussdBindResponse != null ? ussdBindResponse.debugString() : "NOT BOUND");
                if ( ussdBindResponse!= null && ussdBindResponse.getCommandStatus() == Data.ESME_ROK) {
                    new MTSMSender(ussdSession).start();
                    break;
                } else {
                    try { Thread.sleep(5000); } catch(Exception e){};
                }
            } catch (PDUException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WrongSessionStateException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

//        if ( ussdBindResponse!= null && ussdBindResponse.getCommandStatus() == Data.ESME_ROK) {
//            new EnquiryLinkGenerator(ussdSession, commandLine).start();
//        }start
    }

    public static void main(String[] args) {
        // 196.2.77.23 2775 NightPromo pwd prepaid with-promotion
        SmppConfig ussdConfig = new SmppConfig(args[0], Integer.parseInt(args[1]), args[2], args[3]);
        new SMPPTransmitter(ussdConfig, args[4], ("with-promotion".equalsIgnoreCase(args[5])), args[6]);
    }
}
