package utilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hp on 6/8/2017.
 */

public class DataFactory {
    public static final String objectToString(Object object){
        ObjectMapper mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        try {
            String jsonData=mapper.writeValueAsString(object);
            return jsonData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDouble(double value) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(3);
        return format.format(value).replace(",", "");
    }

    public static final String[] splitString(String input, String criteria){
        String[] outPut = input.split("\\"+criteria);
        return outPut;
    }

    public static final String formatDate(Date date)throws Exception{
        DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dFormat.format(date);
    }

    public static final Date parseDate(String date)throws Exception{
        DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dFormat.parse(date);
    }

    public static final Date parseStringDate(String date)throws Exception{
        DateFormat dFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dFormat.parse(date);
    }

    public static final List<Object> stringToObjectList(Class className, String jsonString){
        ObjectMapper mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        try{
            return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, className));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static final Object stringToObject(Class className, String jsonString){
        ObjectMapper mapper= new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        try {
            return mapper.readValue(jsonString,className);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
