package com.topicplaces.android.AndroidSNS.Users;


import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by servicedog on 7/10/15.
 */
public class UserCreator {

    private String ENDPOINT;

    public UserCreator( String end ) {
        ENDPOINT = end;
    }

    public String createUser( String name, String username, String email, String password )
    {
    	Post post = new Post( ENDPOINT + "users", null );

        // Create the message object.
        JSONObject json = new JSONObject();
        try {
            json.put("name", name );
            json.put("username", username );
            json.put( "email", email );
            json.put( "password", password );
            json.put( "password2", password );
            json.put( "isHidden", false );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        
        post.addJson( json );
        
        String response = post.execute();
        
        int index = response.indexOf( "{\"val\":\"u-" );
    	
	    if ( index == -1 ) return null;
	    
	    index = response.indexOf( "u-", index );
	    
	    return response.substring(index, response.indexOf( "\"", index ));
    }
}
