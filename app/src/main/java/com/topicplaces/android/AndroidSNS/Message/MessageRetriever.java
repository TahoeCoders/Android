package com.topicplaces.android.AndroidSNS.Message;


import com.topicplaces.android.AndroidSNS.RESTCalls.Get;

public class MessageRetriever {
	
	private String ENDPOINT;

	public MessageRetriever(String end)
	{
		ENDPOINT = end;
	}
	
	public String getMessageJSON(String GID, Boolean priv, String authkey)
	{		
		Get get = new Get( ENDPOINT + "games/" + GID, authkey );
		
		return get.execute();
	}

	public String getTitleFromJSON( String json ) {

		int ind = json.indexOf("\"text\":\"");

		if ( ind == -1 ) return null;

		ind = json.indexOf( "\"", json.indexOf( ":", ind ) ) + 1;

		String title = "";

		while ( json.charAt( ind ) != '\"') {
			title += json.charAt(ind);
			ind++;
		}

		return title;

	}

	public String getDescriptionFromJSON( String json ) {

		int ind = json.indexOf("\"description\":\"");

		if ( ind == -1 ) return null;

		ind = json.indexOf( "\"", json.indexOf( ":", ind ) ) + 1;

		String desc = "";

		while ( json.charAt( ind ) != '\"') {
			desc += json.charAt(ind);
			ind++;
		}

		return desc;

	}

	public String getMediaFromJSON( String json ) {

		int ind = json.indexOf("\"media\":\"m-");

		if ( ind != -1 ) {

			ind = json.indexOf( "m-", ind );

			if ( ind == -1 ) return null;

			String MID = "";

			while ( json.charAt( ind ) != '\"') {
				MID += json.charAt(ind);
				ind++;
			}

			if ( MID.equals( "" ) ) {
				return null;
			}

			return MID;
		}

		ind = json.indexOf("\"image_url\":\"http");

		if ( ind != -1 ) {

			ind = json.indexOf( "http", ind );

			if ( ind == -1 ) return null;

			String MID = "";

			while ( json.charAt( ind ) != '\"') {
				MID += json.charAt(ind);
				ind++;
			}

			return MID;
		}

		return "";
	}

}
