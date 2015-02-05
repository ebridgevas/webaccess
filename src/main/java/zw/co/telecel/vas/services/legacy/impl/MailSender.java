package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.OutboundMsgDao;
import zw.co.telecel.vas.model.OutboundMsg;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import org.apache.commons.mail.EmailException;

/**
 * @author david@ebridgevas.com
 *
 */
public class MailSender {

    private SendMailTLS sendMail;

    public MailSender() {
        sendMail = new SendMailTLS();
    }

    public void send(){

        try {

            for ( OutboundMsg message : OutboundMsgDao.getPendingMessages("EMAIL")) {

                sendMail.send(
                        new String[]{message.getDestinationId()},
                        "Telecel Web Access Activation Code",
                        message.getPayload());
                OutboundMsgDao.updateStatus(message.getMessageId(), "SENT");
            }
        } catch (EmailException e) {
            e.printStackTrace();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        while (true) {
            new MailSender().send();
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
