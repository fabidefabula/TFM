package com.example.usuario.ludiuca.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Webservice {

    private static Webservice ws;

    protected Webservice() {

    }

    public static Webservice getInstancia() {
        if (ws == null) {
            ws = new Webservice();
        }
        return ws;
    }


    // Metodos

    public String operacionPost(HashMap<String, String> requestBody) {
        String json = null;

        try{
            URL url = new URL("http://www.fabidefabula.com/api.php");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(requestToString(requestBody));
            writer.flush();
            writer.close();
            os.close();
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK ){
                String linea;
                StringBuilder line = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((linea = br.readLine()) != null){
                    line.append(linea);
                }
                json  = line.toString();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return json;
    }
    private String requestToString(HashMap<String, String> request) throws UnsupportedEncodingException{
        boolean first = true;
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, String> entry : request.entrySet()) {
            if(first){
                first = false;
            }
            else{
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return result.toString();
    }
}
