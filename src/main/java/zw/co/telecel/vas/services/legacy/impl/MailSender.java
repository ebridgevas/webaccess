package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.OutboundMsgDao;
import zw.co.telecel.vas.model.OutboundMsg;
import zw.co.telecel.vas.util.legacy.DatabaseException;
import org.apache.commons.mail.EmailException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

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


        List<OutboundMsg> messages = null;
        try {
            messages = OutboundMsgDao.getPendingMessages("EMAIL");
        } catch (DatabaseException e) {
            String errorMessage = "DatabaseException : " + e.getMessage();
            sendError(new String[]{"roseshumba@gmail.com", "david@tekeshe.com"}, errorMessage);
            e.printStackTrace();
        }

        for ( OutboundMsg message : messages ) {
                try {
                    sendMail.send(
                            new String[]{message.getDestinationId()},
                            "Telecel Web Access Activation Code",
                            message.getPayload());
                    OutboundMsgDao.updateStatus(message.getMessageId(), "SENT");
                } catch (EmailException e) {

                    String errorMessage = "Failed to send message to " + message.getDestinationId() + " " +
                            "message Id = " + message.getMessageId() +
                            " Error: " + e.getMessage();

                    try {

                        sendError(new String[]{"roseshumba@gmail.com", "david@tekeshe.com"}, errorMessage);
                    } catch (Exception e1) {
                        System.out.println("############# " + e1.getMessage());
                        e1.printStackTrace();
                    }
                    try {
                        new DatabaseBackedSecurityTokenSender().send(
                                new OutboundMsg(new BigInteger("" + (System.currentTimeMillis() + 4)),
                                        "QUEUED", "SMS", "263739852990", "263739852990", errorMessage));

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        OutboundMsgDao.updateStatus(message.getMessageId(), "ERROR");
                    } catch (DatabaseException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }

    }

    private void sendError(String [] recipients, String errorMessage) {
        try {
            System.out.println("Sending to : " + recipients[0] + ", " + recipients[1] + " ... " + errorMessage);
            sendMail.send( recipients,"VAS Gw Error Alert",errorMessage);
        } catch (EmailException e) {
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
