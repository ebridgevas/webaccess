package zw.co.telecel.vas.model;

import com.google.gson.Gson;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author david@ebridgevas.com
 *
 * * Search
 *    { filter :
 *    { column : nationalId, direction, desc },
 *    { column : emailAddress, direction : asc },
 *    { column : mobileNumber, direction : desc },
 *    { column : role, direction : des } }
 */
public class FilterDTO {
    private final String columnName;
    private final String direction;

    public FilterDTO(String columnName, String direction) {
        this.columnName = columnName;
        this.direction = direction;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getDirection() {
        return direction;
    }

    // Test
    public static void main(String[] args) {

        Set<FilterDTO> filters = new LinkedHashSet<>();
        filters.add(new FilterDTO("nationalId", "desc"));
        filters.add(new FilterDTO("emailAddress", "asc"));
        filters.add(new FilterDTO("mobileNumber", "desc"));
        filters.add(new FilterDTO("role", "desc"));

        String json = new Gson().toJson(filters);
        System.out.println( json );
    }
}
