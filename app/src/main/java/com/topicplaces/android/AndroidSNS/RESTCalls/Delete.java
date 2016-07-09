package com.topicplaces.android.AndroidSNS.RESTCalls;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Stanley R on 7/7/2016.
 */
public class Delete {

    HttpURLConnection urlConnection = null;

    public Delete(String endpoint, String authKey){
        try {
            URL url = new URL(endpoint);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            if ( authKey != null ) {
                urlConnection.setRequestProperty("Cookie", authKey);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void execute() {

        try {
            urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
