package zw.co.telecel.vas.util.legacy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 2013/09/17
 * Time: 10:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseConnectionPooling {

    private static Connection connection;

    public static void init() throws SQLException, Exception {

//        gPool = new GenericObjectPool();
//        Properties props = new Properties();
//        props.setProperty("user", "root");
//        props.setProperty("password", "changeit");
//        ConnectionFactory cf = new DriverConnectionFactory(new com.mysql.jdbc.Driver(), "jdbc:mysql://localhost/telecel", props);
//
//        KeyedObjectPoolFactory kopf = new GenericKeyedObjectPoolFactory(null, SMSC.DB_CONNECTION_POOL_SIZE);
//
//        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, gPool, kopf, null, false, true);
//        gPool.setMaxActive(50);
//        for (int i = 0; i < SMSC.DB_CONNECTION_POOL_SIZE; i++) {
//            gPool.addObject();
//        }
//        PoolingDriver pd = new PoolingDriver();
//        pd.registerPool("vasDBCP", gPool);
//
//        statement = getConnection().createStatement();

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/sdp", "root", "changeit");
    }

    public static Connection getConnection() throws Exception {
//        if (gPool == null) {
//            init();
//        }
//        return DriverManager.getConnection("jdbc:apache:commons:dbcp:vasDBCP");
        return connection;
    }
}
