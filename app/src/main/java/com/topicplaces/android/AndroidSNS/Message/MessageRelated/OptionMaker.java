package com.topicplaces.android.AndroidSNS.Message.MessageRelated;

import com.topicplaces.android.AndroidSNS.RESTCalls.Post;

import org.json.JSONException;
import org.json.JSONObject;

public class OptionMaker {
	
	private String ENDPOINT;

	public OptionMaker( String end )
	{
		ENDPOINT = end;
	}
	
	public String createOptionForGame( String optionText, String gameID, String authC)
	{
		
		Post post = new Post( ENDPOINT + "options", authC );
		
		JSONObject json = new JSONObject();
		try {
			json.put("game", gameID);
			json.put("text", optionText);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		post.addJson( json );
		
		String response = post.execute();
		
		int index = response.indexOf("{\"val\":\"go-");
        
        if ( index == -1 ) return null;
        
        index = response.indexOf( "go-", index );
        
        return response.substring(index, response.indexOf( "\"", index ));
	}

}
