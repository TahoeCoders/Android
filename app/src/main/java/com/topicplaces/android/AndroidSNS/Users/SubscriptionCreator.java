package com.topicplaces.android.AndroidSNS.Users;


import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by servicedog on 7/8/15.
 */
public class SubscriptionCreator {

    private String ENDPOINT;

    public SubscriptionCreator(String end)
    {
        ENDPOINT = end;
    }

    public String execute( String userID, String userToFollow, String authC )
    {
    	
    	Post post = new Post( ENDPOINT + "subscriptions", authC );
    	        
    	JSONObject json = new JSONObject();

        try {
            json.put("follower", userID);
            json.put("followee", userToFollow);
            json.put("autoAccept", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post.addJson( json );

        String response = post.execute();
        
        int index = response.indexOf( "{\"val\":\"sub-" );
    	
	    if ( index == -1 ) return null;
	    
	    index = response.indexOf( "sub-", index );
	    
	    return response.substring( 
	    		index, 
	    		response.indexOf( "\"", index ) 
	    		);
    }

}
