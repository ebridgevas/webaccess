package zw.co.telecel.vas.dao;

import zw.co.telecel.vas.model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * david@ebridgevas.com
 *
 */
public class DataBundleDao extends AbstractDao {

    public List<Item> findAllDataBundlePrices() throws Exception {

        String sql = "SELECT * FROM item ORDER BY itemId";
        return loadItems(sql);
    }

    private List<Item> loadItems(String sql) throws Exception {

        List<Item> items = new ArrayList<Item>();

        Connection con = getConnection();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                items.add(
                        new Item(
                                rs.getString("itemId"),
                                rs.getString("itemDescription"),
                                rs.getBigDecimal("duration"),
                                rs.getBigDecimal("sellingPrice"),
                                rs.getString("unitOfMeasure"),
                                rs.getBigDecimal("quantity")));
            }
            return items;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try { rs = null; } catch(Exception e){}
            try { stmt = null; } catch(Exception e){}
        }
    }
}
