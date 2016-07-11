package com.topicplaces.android.AndroidSNS.Message;


import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by servicedog on 7/8/15.
 */
public class MessagePoster {

    private String ENDPOINT;

    public MessagePoster(String end)
    {
        ENDPOINT = end;
    }

    public String execute( String mess, String desc, String mediaID, String authkey, String tid )
    {
    	
        Post post = new Post( ENDPOINT + "games", authkey );
        
        // Create JSON Request
        JSONObject json = new JSONObject();

        try {
            json.put("topic", tid);
            json.put("text", mess);

            JSONObject meta = new JSONObject();
            meta.put("description", desc);
            json.put("metadata", meta);

            JSONObject context = new JSONObject();
            context.put("media", mediaID);
            json.put("context", context);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Add request to POST call
        post.addJson( json );
        
        // Execute call
        String response = post.execute();
        
        int index = response.indexOf("{\"val\":\"g-");
        
        if ( index == -1 ) return null;
        
        index = response.indexOf( "g-", index );
        
        return response.substring( 
        		index, 
        		response.indexOf( "\"", index ) 
        		); 
            
    }

}
