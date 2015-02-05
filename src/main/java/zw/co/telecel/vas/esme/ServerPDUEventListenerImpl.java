package zw.co.telecel.vas.esme;

import com.ebridge.sdp.smpp.*;
import com.ebridge.sdp.smpp.pdu.*;

import java.io.IOException;

public class ServerPDUEventListenerImpl extends SmppObject implements ServerPDUEventListener {

    private Session session;

    public ServerPDUEventListenerImpl(Session session) {
        this.session = session;
    }

    /* Entry point. This method is called by the Session object upon recieving a PDU from
     *  SMPP interface via the TCP layer (provided for by TCPIPConnection object.)
     */
    public void handleEvent( ServerPDUEvent event ) {

        PDU pdu = event.getPDU();

        if ( pdu.isRequest() )
            try {
                process((Request) pdu);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ValueNotSetException e) {
                e.printStackTrace();
            } catch (WrongSessionStateException e) {
                e.printStackTrace();
            } catch (PDUException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        else
            process((Response) pdu);
    }

    /**
     * Process request from server.
     *
     * @param request
     * @throws java.io.IOException
     * @throws com.ebridge.sdp.smpp.pdu.PDUException
     * @throws com.ebridge.sdp.smpp.WrongSessionStateException
     * @throws com.ebridge.sdp.smpp.TimeoutException
     */
    protected void process(Request request)
            throws IOException, PDUException, WrongSessionStateException, TimeoutException {

        System.out.println(request.debugString());
        switch(request.getCommandId()) {
            case 21:
                EnquireLinkResp response = new EnquireLinkResp();
                response.setCommandStatus(0);
                System.out.println(response.debugString());
                session.respond(response);
                break;
            default:
                System.out.println("Parsing deliverSM pdu.");
                DeliverSM deliverSM = (DeliverSM) request;

                /* Acknowledge delivery. */
                session.respond( getDeliverSmResp( deliverSM ) );
        }
    }

    protected Response getDeliverSmResp(DeliverSM deliverSM) {
        DeliverSMResp deliverSmResp = new DeliverSMResp();
        deliverSmResp.setCommandId(Data.DELIVER_SM_RESP);
        deliverSmResp.setCommandStatus(0);
        deliverSmResp.setSequenceNumber(deliverSM.getSequenceNumber());
        System.out.println("Ack: " + deliverSmResp.debugString());
        return deliverSmResp;
    }

    /**
     * Check if delivery note.
     * @param deliverSM
     * @return
     */
    protected Boolean isDeliveryNote( DeliverSM deliverSM ) {
        return (deliverSM.getShortMessage() != null) && deliverSM.getShortMessage().startsWith("id:");
    }

    protected void process(Response response) {
        System.out.println( response.debugString() );
//        if (request.getCommandStatus() == 0) {
//            SMPPTransciever.linkState = "LINK_IS_UP";
//        }
    }
}