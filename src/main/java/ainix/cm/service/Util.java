package ainix.cm.service;

import ainix.cm.entity.Employe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static Map<String,String> createHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-amazon-author","Lipsa");
        headers.put("X-amazon-apiVersion","v1");
        return  headers ;
    }

    public static String convertObjToString(Employe employee, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
    public static Employe convertStringToObj(String jsonBody, Context context){
        Employe employee = null;
        try {
            employee =   new ObjectMapper().readValue(jsonBody,Employe.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
        }
        return employee;
    }
    public static String convertListOfObjToString(List<Employe> employees, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}
