package com.topicplaces.android.AndroidSNS.Message.MessageRelated;

import com.topicplaces.android.AndroidSNS.RESTCalls.Put;

import org.json.JSONException;
import org.json.JSONObject;

public class LinkMaker {

	private String ENDPOINT;

	public LinkMaker(String end)
	{
		ENDPOINT = end;
	}
	
	public void createLinkForGame( String url, String GID, String authC)
	{
		Put put = new Put( ENDPOINT + "games/" + GID, authC );
		
		JSONObject json = new JSONObject();
		JSONObject context = new JSONObject();
		try {
			context.put("id", url);
			context.put("description", "Link to " + url);
			context.put("site_name", url.replaceAll("http://", ""));
			context.put("title", "LINK");
			context.put("url", url);
			json.put("context", context);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		put.addJson( json );

		put.execute();
	}

}
