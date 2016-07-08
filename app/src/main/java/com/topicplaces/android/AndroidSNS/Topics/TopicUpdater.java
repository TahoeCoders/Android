package com.topicplaces.android.AndroidSNS.Topics;

import com.topicplaces.android.AndroidSNS.RESTCalls.Put;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stanley R on 7/8/2016.
 */
public class TopicUpdater {

    private String ENDPOINT;

    public TopicUpdater(String end)
    {
        ENDPOINT = end;
    }

    public void updateTopic(String mess, String desc, String mediaID, Boolean priv, String tid, String authkey) {
        Put put = null;

        if ( priv ) {
            put = new Put(ENDPOINT + "groups/" + tid, authkey );
        } else {
            put = new Put(ENDPOINT + "topics/" + tid, authkey );
        }

        JSONObject json = new JSONObject();

        if ( mess != null ) {
            try {
                json.put( "name", mess );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if ( desc != null ) {
            try {
                json.put("description", desc );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        put.addJson( json );
        put.execute();
    }

}
