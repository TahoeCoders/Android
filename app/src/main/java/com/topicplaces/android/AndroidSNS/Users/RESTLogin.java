package com.topicplaces.android.AndroidSNS.Users;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RESTLogin {

    private String ENDPOINT;

    public RESTLogin(String end) {
        ENDPOINT = end;
    }

    public String login(String user, String password) {

        String authKey = null;

        HttpURLConnection urlConnection = null;

        try {

            URL url = new URL(ENDPOINT + "sessions");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            String quarry = (URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8"))
                    + "&" + (URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8"));

            byte[] bytesOut = quarry.getBytes("UTF-8");

            /*
            String charset = "UTF-8"
            urlConnection.setRequestProperty("Accept-Charset", charset);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            urlConnection.setRequestProperty("username", user);
            urlConnection.setRequestProperty("password", password);
            urlConnection.setRequestProperty("on_success", ENDPOINT.replaceAll("api/2/",
                    "") + "util-iframes/blank-page.html?response=success");
            urlConnection.setRequestProperty("on_fail", ENDPOINT.replaceAll("api/2/", "")
                    + "util-iframes/blank-page.html?response=fail");

            */

            urlConnection.getOutputStream().write(bytesOut);

            /*
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                Log.d("key=value", header.getKey() + "=" + header.getValue());
            }
            */

            if (urlConnection.getResponseCode() == 201){
                authKey = urlConnection.getHeaderField("Set-cookie");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        return authKey;
    }

}