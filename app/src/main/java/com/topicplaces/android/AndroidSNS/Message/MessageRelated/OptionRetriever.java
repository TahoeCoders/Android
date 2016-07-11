package com.topicplaces.android.AndroidSNS.Message.MessageRelated;


import com.topicplaces.android.AndroidSNS.Message.MessageRetriever;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by servicedog on 7/11/15.
 */
public class OptionRetriever {

    private String ENDPOINT;


    public OptionRetriever(String end) {

        ENDPOINT = end;

    }

    public Map<String, String> getIDMap(String GID, Boolean priv, String authkey) {

        return getOptionsAndIDsFromJSON(getOptionsJSON(GID, priv, authkey));
    }

    public Map<String, String> getAnswerMap(String GID, Boolean priv, String authkey) {

        return getOptionsAndAnswersFromJSON(getOptionsJSON(GID, priv, authkey));
    }

    public String getOptionsJSON(String GID, Boolean priv, String authkey) {

        MessageRetriever gameRetriever = new MessageRetriever( ENDPOINT );
        String json = gameRetriever.getMessageJSON(GID, priv, authkey);

        return json;

    }

    public Map<String, String> getOptionsAndIDsFromJSON(String json) {

        Map<String,String> opts = new HashMap<String,String>();
        int index = json.indexOf("\"id\":\"go-");

        while( index != -1 ) {
            index = json.indexOf("go-", index);
            String OID = json.substring( index, json.indexOf( "\"", index ) );
            index = json.indexOf( "text", index );
            index = json.indexOf( "\"", index );
            index = json.indexOf( "\"", index + 1) + 1;
            String text = json.substring(index, json.indexOf("\"", index));
            opts.put( text, OID );
            index = json.indexOf( "\"id\":\"go-", index );
        }

        return opts;
    }

    public Map<String, String> getOptionsAndAnswersFromJSON(String json) {

        Map<String,String> opts = new HashMap<String,String>();
        int index = json.indexOf("\"id\":\"go-");

        while( index != -1 ) {
            index = json.indexOf( "text", index );
            index = json.indexOf( "\"", index );
            index = json.indexOf( "\"", index + 1) + 1;
            String text = json.substring(index, json.indexOf("\"", index));
            index = json.indexOf( "answered_count", index );
            index = json.indexOf( "\":", index ) + 2;
            String answered_count = json.substring(index, json.indexOf(",", index));
            opts.put( text, answered_count );
            index = json.indexOf( "\"id\":\"go-", index );
        }

        return opts;
    }


}


