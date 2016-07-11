package com.topicplaces.android.AndroidSNS.Users;


import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by servicedog on 7/10/15.
 */
public class UserInviter {

    private String ENDPOINT;

    public UserInviter(String end) {
        ENDPOINT = end;
    }

    public String inviteUserToPID( String userID, String PID, String authC )
    {
    	Post post = new Post( ENDPOINT + "invitations/groups", authC );
        
    	JSONObject json = new JSONObject();
        JSONArray jsar = new JSONArray();

        JSONObject j = new JSONObject();

        try {
            j.put("target", PID);
            j.put("recipient", userID);
            jsar.put(j);
            json.put("invitations", jsar);
            json.put("autoAccept", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        post.addJson( json );
        
        String response = post.execute();
        
        int index = response.indexOf( "{\"val\":[\"i-" );
    	
	    if ( index == -1 ) return null;
	    
	    index = response.indexOf( "i-", index );
	    
	    return response.substring(index, response.indexOf( "\"", index ));
    }
}
