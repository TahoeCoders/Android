package com.topicplaces.android.AndroidSNS.Topics;

import com.topicplaces.android.AndroidSNS.RESTCalls.Get;

public class TopicRetriever {

	private String ENDPOINT;

	public TopicRetriever(String end)
	{
		ENDPOINT = end;
	}

    public String getTopicInfoJSON( String TID, boolean priv, String authkey ) {
        String end = ENDPOINT;
        if ( priv ) {
            end = end + "groups/" + TID;
        } else {
            end = end + "topics/" + TID;
        }
        
        Get get = new Get( end, authkey );
        return get.execute();
    }

	public String getTopicStreamJSON(String TID, boolean priv, String authkey)
	{
		
		String end = ENDPOINT + "stream/" + TID + ":out?limit=200";
		Get get = new Get( end, authkey );
		
        return get.execute();
	}

    public String getTitleFromJSON( String json ) {

		int ind = json.indexOf("\"name\":\"");

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
