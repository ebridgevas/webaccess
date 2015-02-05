package zw.co.telecel.vas.esme;

import com.ebridge.sdp.smpp.TimeoutException;
import com.ebridge.sdp.smpp.WrongSessionStateException;
import com.ebridge.sdp.smpp.pdu.PDUException;
import com.ebridge.sdp.smpp.pdu.SubmitSM;

import com.ebridge.sdp.smpp.pdu.ValueNotSetException;
import zw.co.telecel.vas.dao.OutboundMsgDao;
import zw.co.telecel.vas.model.OutboundMsg;
import com.ebridgevas.util.SubmitSmFactory;
import zw.co.telecel.vas.util.legacy.DatabaseException;

import java.io.IOException;

public class MTSMSender implements Runnable {

    private com.ebridge.sdp.smpp.Session smppSession;

    public MTSMSender(com.ebridge.sdp.smpp.Session smppSession) {
        System.out.println("\n\n\n### MTSMProcessor version 13.04\n\n\n");
        this.smppSession = smppSession;
    }

    public void start() {
        Thread serverThread = new Thread(this);
        serverThread.start();
    }

    @Override
    public void run() {

        try {

            while (true ) {
                for ( OutboundMsg message : OutboundMsgDao.getPendingMessages("SMS")) {

                    try {

                        SubmitSM submitSM = new SubmitSmFactory().create(
                                "33350",
                                message.getDestinationId(),
                                message.getPayload(),
                                // (short)9280,
                                null,
                                (byte) 3,
                                (byte) 1 );
                        System.out.println("###### submitSM : " + submitSM.debugString());
                        smppSession.submit(submitSM);

                        OutboundMsgDao.updateStatus(message.getMessageId(), "SENT");
                    } catch (DatabaseException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValueNotSetException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (WrongSessionStateException e) {
            e.printStackTrace();
        } catch (PDUException e) {
            e.printStackTrace();
        }
    }
}