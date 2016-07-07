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

    /**
     * Create new Get class.
     *
     * @param endpoint The endpoint to retrieve data.
     * @param authKey The authentication key.
     */

    public Get(String endpoint, String authKey) {

        try {
            //Create new HttpURLConnection and set properties.
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");

            /*
            if (urlConnection.getResponseCode() != 200) {
                throw new IOException(urlConnection.getResponseMessage());
            }
            */

            //Set authKey as request property if appropriate.
            if (authKey != null) {
                urlConnection.setRequestProperty("Cookie", authKey);
            }

        //Catch exceptions.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute the given method.
     *
     * @return The return string from the HttpURLConnection.
     */

    public String execute() {
        try {
            //Request JSON.
            urlConnection.setRequestProperty("Content-Type","application/json");

            Log.d("Get_ResponseCode", urlConnection.getResponseCode() +"");

            /*
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                Log.d("key=value", header.getKey() + " = " + header.getValue());
            }
            */

            //From ServiceDog
            //Create new reader and build String.
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            returnString = null;
            String line = reader.readLine();
            returnString = line;

            while (line != null) {
                line = reader.readLine();
                returnString += line;
            }

        //Catch exceptions.
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exception", "IO");
        } catch (Throwable t) {
            t.printStackTrace();
            Log.d("throwable", "t");
        }

        Log.d("Get_returnString", returnString);
        return returnString;
    }
}
