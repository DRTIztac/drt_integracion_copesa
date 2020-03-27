/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilesSecos;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author jorge
 */
public class Test {
     
     public static String cov(String txt) throws UnsupportedEncodingException{
       return  new String(txt.getBytes("ISO-8859-1"),"UTF-8");
    }
    
    
    
    public static void main(String[] args) throws UnsupportedEncodingException {
        String txt =  "COMPAÃIA TARAHUMARA DE OCCIDENTE SA DE CV";
        System.out.println(cov(txt));
        /*
        org.json.JSONObject jsonSalida = new org.json.JSONObject();
        jsonSalida.accumulate("idcliente", "ok");
        org.json.JSONArray jsonArr =  new org.json.JSONArray(); 
            org.json.JSONObject jsonS0 = new org.json.JSONObject();
            jsonS0.accumulate("id", "1");
            org.json.JSONObject jsonS1 = new org.json.JSONObject();
            jsonS1.accumulate("id", "2");
            org.json.JSONObject jsonS2 = new org.json.JSONObject();
            jsonS2.accumulate("id", "3");
           
        jsonArr.put(jsonS0);
        jsonArr.put(jsonS1);
        jsonArr.put(jsonS2);
        System.out.println(jsonArr.toString());
        jsonSalida.accumulate("item", jsonArr);
        System.out.println(jsonSalida.toString());
        org.json.JSONArray back = (org.json.JSONArray) jsonSalida.get("item");
        System.out.println(back.length());
        */
    }
        
}
