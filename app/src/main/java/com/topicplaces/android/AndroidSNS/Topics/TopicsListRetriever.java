package com.topicplaces.android.AndroidSNS.Topics;

import com.topicplaces.android.AndroidSNS.RESTCalls.Get;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TahoeCommie on 7/5/2016.
 */
public class TopicsListRetriever {

    private String ENDPOINT;

    public TopicsListRetriever(String end) {
        ENDPOINT = end;
    }

    public Map<String, String> getList(String userID) {

        Get get = new Get(ENDPOINT + "topics/by_owner/" + userID, null);

        String response = get.execute();
        return parseJSON( response );
    }

    public Map<String, String> parseJSON( String json ) {

        Map<String, String> topics = new HashMap<String, String>();

        String name = null;
        String grpID = null;

        int index = json.indexOf( ":{\"id\":\"t-" );

        while( index != -1 ) {

            index = json.indexOf( "t-", index );
            grpID = json.substring( index, json.indexOf("\"", index) );
            index = json.indexOf( "name", index);
            index = json.indexOf("\"", index);
            index = json.indexOf( "\"", index + 1) + 1;
            name = json.substring(index, json.indexOf("\"", index));
            topics.put(name, grpID);
            index = json.indexOf( ":{\"id\":\"t-", index );
        }

        return topics;
    }
}
