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

    /**
     * Cheats a post object.
     *
     * @param endpoint The destination to post.
     * @param authKey The authentication key.
     */

    public Post (String endpoint, String authKey) {
        try {
            //Create HttpURLConnection and set properties.
            URL url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            //Add authKey if appropriate.
            if (authKey != null) {
                urlConnection.setRequestProperty("Cookie", authKey);
            }

        //Catch exceptions
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a JSON to the HttpURLConnection as request property.
     *
     * @param json the JSON to add.
     */

    public void addJson(JSONObject json) {

        urlConnection.setRequestProperty("Content-Type","application/json");
        outputString = json.toString();
    }

    /**
     * Execute the post method.
     *
     * @return The string from the server.
     */

    public String execute() {
        try {
            urlConnection.getOutputStream().write(outputString.getBytes());

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

            //debug code
            /*
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                Log.d("key=value", header.getKey() + " = " + header.getValue());
            }
            */

        //Catch exceptions.
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("exception", "IO");
        } catch (Throwable t) {
            t.printStackTrace();
            Log.d("throwable", "t");
        }

        return returnString;
    }





}
