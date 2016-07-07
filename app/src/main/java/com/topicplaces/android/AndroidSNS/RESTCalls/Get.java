package com.topicplaces.android.AndroidSNS.RESTCalls;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Get {

    private URL url;
    private HttpURLConnection urlConnection;

    public Get(String endpoint, String authKey) {

        try {
            url = new URL(endpoint);
            urlConnection = (HttpURLConnection) url.openConnection();

            if (urlConnection.getResponseCode() != 200) {
                throw new IOException(urlConnection.getResponseMessage());
            }

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

        return "Get.execute() return String";

    }
}
