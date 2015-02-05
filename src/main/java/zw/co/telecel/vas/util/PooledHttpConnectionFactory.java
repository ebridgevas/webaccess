package zw.co.telecel.vas.util;

/**
 * @author david@tekeshe.com
 */
public class PooledHttpConnectionFactory {

    /**
     * I'm implementing a SOAP client using Apache Axis 2. Since the SOAP client must handle heavy number of requests
     * I am using a connection pool.

     To do that I had to set a few transport layer configuration of my stub that was generated from a WSDL file:
     */
    public static void connection() {

//        stub._getServiceClient().getOptions().setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);
//
//        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
//        connectionManager.getParams().setDefaultMaxConnectionsPerHost(MAX_CONNECTIONS_PER_HOST);
//        connectionManager.closeIdleConnections(IDLE_CONNECTION_TIMEOUT);
//        HttpClient httpClient = new HttpClient(connectionManager);
//
//        stub._getServiceClient().getOptions().setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);

    }
}
