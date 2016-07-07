package com.topicplaces.android.AndroidSNS.RESTCalls;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Get {

    private URL url;
    private HttpURLConnection urlConnection;
    String returnString;

    public Get(String endpoint, String authKey) {

        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");

            /*
            if (urlConnection.getResponseCode() != 200) {
                throw new IOException(urlConnection.getResponseMessage());
            }
            */

            if (authKey != null) {
                urlConnection.setRequestProperty("Cookie", authKey);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String execute() {
        try {
            urlConnection.setRequestProperty("Content-Type","application/json");

            Log.d("Get_ResponseCode", urlConnection.getResponseCode() +"");

            /*
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                Log.d("key=value", header.getKey() + " = " + header.getValue());
            }
            */

            //From ServiceDog
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            returnString = null;
            String line = reader.readLine();
            returnString = line;

            while (line != null) {
                line = reader.readLine();
                returnString += line;
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exception", "IO");
        } catch (Throwable t) {
            t.printStackTrace();
            Log.d("throwable", "t");
        }

        Log.d("Get_returnString", returnString);
        return returnString;

        //return "Get.execute() return String";

    }
}
