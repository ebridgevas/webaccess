package zw.co.telecel.vas.dao;

import zw.co.telecel.vas.model.DataBundlePrice;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * david@ebridgevas.com
 *
 */
public class DataBundlePriceDao {

    private static Connection connection;

    static {
        try {
            connection = DataBaseConnectionPool.getConnection();
        } catch (Exception e) {
            // TODO handle exception
            e.printStackTrace();
        }
    }

    public static Map<String, DataBundlePrice> getDataBundlePrices() {
        if ( connection == null) {
            try {
                connection = DataBaseConnectionPool.getConnection();
            } catch (Exception e) {
                // TODO handle exception
                e.printStackTrace();
            }
        }

        String sql = " SELECT bundle_id, bundle_description, bundle_size, bundle_rate, debit, credit, " +
                     "        out_of_bundle_rate, window " +
                     " FROM bundle_price " +
                     " ORDER BY bundle_id ";

        Statement stmt = null;
        ResultSet rs = null;
        try {
            Map<String, DataBundlePrice> prices = new HashMap<String, DataBundlePrice>();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {

                String bundleId = rs.getString("bundle_id");
                Boolean hasBonus =  bundleId.equals("5") || bundleId.equals("6") || bundleId.equals("7") || bundleId.equals("8");

                prices.put( rs.getString("bundle_id"),
                            new DataBundlePrice(
                                    rs.getString("bundle_id"),
                                    rs.getString("bundle_description"),
                                    rs.getBigDecimal("bundle_size"),
                                    rs.getBigDecimal("bundle_rate"),
                                    rs.getBigDecimal("debit"),
                                    rs.getBigDecimal("credit"),
                                    hasBonus ? rs.getBigDecimal("credit") : BigDecimal.ZERO,
                                    rs.getBigDecimal("out_of_bundle_rate"),
                                    rs.getInt("window")));
            }

            return prices;
        } catch (SQLException e) {
            // TODO handle exception
            e.printStackTrace();
        } finally {
            try {rs.close();} catch (Exception e){}
            try {stmt.close();} catch (Exception e){}
        }

        return null;
    }
}
