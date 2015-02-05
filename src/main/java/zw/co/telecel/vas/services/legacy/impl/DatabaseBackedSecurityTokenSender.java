package zw.co.telecel.vas.services.legacy.impl;

import zw.co.telecel.vas.dao.OutboundMsgDao;
import zw.co.telecel.vas.model.OutboundMsg;
import zw.co.telecel.vas.services.legacy.SecurityTokenSender;
import zw.co.telecel.vas.util.legacy.DatabaseException;

import java.io.IOException;

/**
 * @author david@tekeshe.com
 *
 */
public class DatabaseBackedSecurityTokenSender implements SecurityTokenSender {

    @Override
    public void send ( OutboundMsg outboundMsg) throws IOException {

        try {
            OutboundMsgDao.persist( outboundMsg );
        } catch (DatabaseException e) {
            throw new IOException( e );
        }
    }
}
