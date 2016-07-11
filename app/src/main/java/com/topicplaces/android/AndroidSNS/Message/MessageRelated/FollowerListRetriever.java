package com.topicplaces.android.AndroidSNS.Message.MessageRelated;


import com.topicplaces.android.AndroidSNS.RESTCalls.Get;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by servicedog on 7/11/15.
 */
public class FollowerListRetriever {

    private String ENDPOINT;

    public FollowerListRetriever(String end) {

        ENDPOINT = end;

    }
    
    public String getFollowerJSON(String identifier) {

    	Get get = new Get( ENDPOINT + "subscriptions/by_followee/" + identifier, null );
        
        return get.execute();
    }

    public Map<String, String> getUserSubscriptionMap(String UID) {

        return parseUserSubsJSON(getFollowerJSON(UID));
    }

    public Map<String, String> getUserIDMap( String UID ) {

        return parseUserIDJSON(getFollowerJSON(UID));
    }

    public Map<String, String> parseUserIDJSON( String json ) {

        Map<String, String> map = new HashMap<String, String>();

        int index = json.indexOf( "refs" );
        index = json.indexOf( "\"sub-", index );

        if ( index == -1 ) return map;

        String key = null;

        while( index != -1 ) {
            index++;
            //String value = json.substring(index, json.indexOf("\"", index) );

            index = json.indexOf("\"follower\"", index);
            index = json.indexOf(":", index);
            index = json.indexOf( "\"", index ) + 1;
            key = json.substring(index, json.indexOf("\"", index));

            map.put(key, key);

            index = json.indexOf( "\"sub-", index );

        }

        return map;
    }

    public Map<String, String> parseUserSubsJSON(String json) {

        Map<String, String> map = new HashMap<String, String>();

        int index = json.indexOf( "refs" );
        index = json.indexOf( "\"sub-", index );

        if ( index == -1 ) return map;

        String key = null;
        String value = null;

        while( index != -1 ) {
            index++;
            value = json.substring(index, json.indexOf("\"", index) );

            index = json.indexOf("\"follower\"", index);
            index = json.indexOf(":", index);
            index = json.indexOf( "\"", index ) + 1;
            key = json.substring(index, json.indexOf("\"", index));

            map.put(key, value);

            index = json.indexOf( "\"sub-", index );

        }

        return map;
    }
}


