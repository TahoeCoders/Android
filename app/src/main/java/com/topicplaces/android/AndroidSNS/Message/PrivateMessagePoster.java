package com.topicplaces.android.AndroidSNS.Message;


import android.util.Log;

import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by servicedog on 7/8/15.
 */
public class PrivateMessagePoster {

    private String ENDPOINT;

    public PrivateMessagePoster(String end)
    {
        ENDPOINT = end;
    }


    public String execute( String mess, String desc, String mediaID, String authkey, String tid )
    {

    	Post post = new Post( ENDPOINT + "games", authkey );
    	
    	// Create JSON request
        JSONObject json = new JSONObject();

        try {
            JSONArray grou = new JSONArray();
            grou.put(tid);
            json.put("groups", grou);

            json.put("text", mess);

            JSONObject meta = new JSONObject();
            meta.put("description", desc);
            json.put("metadata", meta);

            JSONObject context = new JSONObject();
            context.put("media", mediaID);
            json.put("context", context);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("EXCEPTION", "Malformed JSON");
        }

        // Add JSON entity to post
        post.addJson( json );
        
        // Execute call
        String response = post.execute();
       
        int index = response.indexOf("{\"val\":\"g-");
        
        if ( index == -1 ) return null;
        
        index = response.indexOf( "g-", index );
        
        return response.substring(index, response.indexOf( "\"", index ));
 
    }

}
