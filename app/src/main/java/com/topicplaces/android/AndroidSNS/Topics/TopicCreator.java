package com.topicplaces.android.AndroidSNS.Topics;


import android.util.Log;

import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stanley R on 7/5/2016.
 */
public class TopicCreator {

    private String ENDPOINT;

    public TopicCreator(String end) {
        ENDPOINT = end;
    }

    public String createTopic( String title, boolean priv, String authC )
    {

        Post post = null;


        if ( priv ) {
            post = new Post(ENDPOINT + "groups", authC );
        } else {
            post = new Post(ENDPOINT + "topics", authC );
        }


        // Create the groups array for the groups property.
        JSONObject json = new JSONObject();
        try {
            json.put( "name", title );
            json.put( "title", title );
            json.put( "description", "" );
            json.put( "read_only", false );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        post.addJson( json );

        String beginning = null;
        if ( priv ) {
            beginning = "grp-";
        } else {
            beginning = "t-";
        }

        String response = post.execute();

        Log.d("postResponse", response);

        int index = response.indexOf( "{\"val\":\"" + beginning );

        if ( index == -1 ) return null;

        index = response.indexOf( beginning, index );

        return response.substring(index, response.indexOf( "\"", index ));

    }
}
