package zw.co.telecel.vas.services.util;

import com.google.gson.Gson;
import zw.co.telecel.dto.ConfigDTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author david@tekeshe.com
 */
public class JsonConfigurationService implements ConfigurationService {

    @Override
    public ConfigDTO config() {

        try {
            return new Gson().fromJson(
                    new BufferedReader( new FileReader("/prod/ebridge/conf/web.json")), ConfigDTO.class );
        } catch (FileNotFoundException e) {
            // TODO handle exception
            e.printStackTrace();
        }
        return null;
    }
}
