package config;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by Owner on 7/9/2016.
 */
public class ClientData {
    private String tag="AirTime: "+getClass().getSimpleName();
    private String jsonData;
    private ObjectMapper mapper;

    public ClientData() {
    }

    public String mapping(Object object){
        Log.d(tag, "mapping object starts...");
        mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            jsonData=mapper.writeValueAsString(object);
            Log.d(tag,"mapping result: "+jsonData);
            return jsonData;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(tag, "Erroneous mapping object: " + e.getMessage());
            return e.getMessage();
        }
    }

    public Object jsonObject(Class className, String jsonString){
        Class mClass=className;
        mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Object result=mapper.readValue(jsonString,mClass);
            return result;
        } catch (Exception e) {
            System.out.println("Exit with Error: "+e.getMessage());
        }
        return null;
    }
}
