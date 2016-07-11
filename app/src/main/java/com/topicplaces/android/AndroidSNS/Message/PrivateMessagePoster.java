package com.topicplaces.android.AndroidSNS.Message;


/**
 * Created by servicedog on 7/8/15.
 */
public class PrivateMessagePoster {

    private String ENDPOINT;

    public PrivateMessagePoster(String end)
    {
        ENDPOINT = end;
    }

    /*
    public String execute( String mess, String desc, String mediaID, String authkey, String tid )
    {

    	Post post = new Post( ENDPOINT + "games", authkey );
    	
    	// Create JSON request
        JSONObject json = new JSONObject();

        try {

            JSONArray grou = new JSONArray();
            grou.put(new JsonPrimitive(tid));
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
        }

        // Add JSON entity to post
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
    */
}
