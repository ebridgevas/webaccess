package zw.co.telecel.vas.services;

import zw.co.telecel.vas.model.ServiceCommandRequest;
import zw.co.telecel.vas.model.ServiceCommandResponse;

/**
 * david@ebridgevas.com
 *
 */
public interface BillingService {

    ServiceCommandResponse balanceEnquiry(ServiceCommandRequest request);
}
