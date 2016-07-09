package com.topicplaces.android.AndroidSNS.Message;

import com.topicplaces.android.AndroidSNS.Topics.TopicRetriever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by servicedog on 7/11/15.
 */
public class MessageListRetriever {

    private String ENDPOINT;

    public MessageListRetriever(String end) {

        ENDPOINT = end;

    }

    public Map<String, String> getMap(String TID, boolean priv, String authkey) {

        String json = null;
        if ( priv ) {
            json = getPrivateTopicJSON( TID, authkey );
        } else {
            json = getTopicJSON( TID, authkey );
        }

        return parseJSONForMap(json);
    }

    public List<String> getGIDList(String TID, boolean priv, String authkey) {

        String json = null;
        if ( priv ) {
            json = getPrivateTopicJSON( TID, authkey );
        } else {
            json = getTopicJSON( TID, authkey );
        }

        return parseJSONForGIDList(json);
    }

    public String getTopicJSON(String TID, String authkey) {

        TopicRetriever tr = new TopicRetriever( ENDPOINT );
        return tr.getTopicStreamJSON(TID, false, authkey);

    }

    public String getPrivateTopicJSON(String TID, String authkey) {

        TopicRetriever tr = new TopicRetriever( ENDPOINT );
        return tr.getTopicStreamJSON(TID, true, authkey);

    }


    public Map<String, String> parseJSONForMap(String json) {

        Map<String, String> games = new HashMap<String, String>();

        String name = null;
        String gid = null;

        int index = json.indexOf( "\"id\":\"g-" );

        while( index != -1 ) {
            index = json.indexOf( "g-", index );
            gid = json.substring( index, json.indexOf("\"", index) );
            index = json.indexOf("text", index);
            index = json.indexOf("\"", index);
            index = json.indexOf( "\"", index + 1) + 1;
            name = json.substring(index, json.indexOf("\"", index));
            games.put( name, gid );
            index = json.indexOf( "\"id\":\"g-", index );
        }

        return games;
    }


    public List<String> parseJSONForGIDList(String json) {

        List<String> games = new ArrayList<String>();

        //String name = null;
        String gid = null;

        int index = json.indexOf( "\"id\":\"g-" );

        while( index != -1 ) {
            index = json.indexOf( "g-", index );
            gid = json.substring( index, json.indexOf("\"", index) );
            index = json.indexOf("text", index);
            index = json.indexOf("\"", index);
            index = json.indexOf( "\"", index + 1) + 1;
            //name = json.substring(index, json.indexOf("\"", index));
            games.add( gid );
            index = json.indexOf( "\"id\":\"g-", index );
        }

        return games;
    }


}


