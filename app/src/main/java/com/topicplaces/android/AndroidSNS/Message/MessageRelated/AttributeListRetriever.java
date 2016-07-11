package com.topicplaces.android.AndroidSNS.Message.MessageRelated;

import com.topicplaces.android.AndroidSNS.Message.MessageRetriever;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by servicedog on 7/11/15.
 */
public class AttributeListRetriever {

    private String ENDPOINT;

    public AttributeListRetriever(String end) {

        ENDPOINT = end;

    }

    public Map<String, String> getList( String GID, Boolean priv, String authkey ) {

        return parseJSON( getAttributesJSON( GID, priv, authkey ) );
    }

    public String getAttributesJSON(String GID, Boolean priv, String authkey) {

        MessageRetriever gameRetriever = new MessageRetriever( ENDPOINT );
        String json = gameRetriever.getMessageJSON(GID, priv, authkey);

        return json;

    }

    public Map<String, String> parseJSON( String json ) {

        Map<String, String> atts = new HashMap<String,String>();
        int index = json.indexOf( "\"attributes\"" );

        if ( index == -1 ) return atts;

        String key = null;
        String value = null;
        int indexOfEnd = json.indexOf( "]", index );
        index = json.indexOf( "\"name\"", index );

        while( index != -1 && index < indexOfEnd ) {
            index = json.indexOf( ":", index );
            index = json.indexOf( "\"", index ) + 1;
            key = json.substring(index, json.indexOf("\"", index));

            index = json.indexOf("\"value\"", index);

            index = json.indexOf(":", index);
            index = json.indexOf( "\"", index ) + 1;
            value = json.substring(index, json.indexOf("\"", index));

            atts.put( key, value );
            index = json.indexOf("\"name\"", index);
        }

        return atts;
    }


}


