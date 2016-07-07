package com.topicplaces.android.AndroidSNS.RESTCalls;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Stanley R on 7/5/2016.
 */
public class Post {

    HttpURLConnection urlConnection;
    String outputString;
    String returnString;

    public Post (String endpoint, String authKey) {
        try {
            URL url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            if (authKey != null) {
                urlConnection.setRequestProperty("Cookie", authKey);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addJson(JSONObject json) {

        urlConnection.setRequestProperty("Content-Type","application/json");
        outputString = json.toString();
    }

    public String execute() {
        try {
            urlConnection.getOutputStream().write(outputString.getBytes());

            //From ServiceDog
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            returnString = null;
            String line = reader.readLine();
            returnString = line;

            while (line != null) {
                line = reader.readLine();
                returnString += line;
            }

            //debug code
            /*
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                Log.d("key=value", header.getKey() + " = " + header.getValue());
            }
            */

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exception", "IO");
        } catch (Throwable t) {
            t.printStackTrace();
            Log.d("throwable", "t");
        }

        Log.d("Post_returnString", returnString);
        return returnString;
    }





}
