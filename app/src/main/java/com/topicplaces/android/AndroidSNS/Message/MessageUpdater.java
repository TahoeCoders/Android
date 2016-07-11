package com.topicplaces.android.AndroidSNS.Message;

import com.topicplaces.android.AndroidSNS.RESTCalls.Put;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by servicedog on 7/8/15.
 *
 *
 * FIX MEDIA
 *
 *
 *
 *
 *
 *
 */
public class MessageUpdater {

    private String ENDPOINT;

    public MessageUpdater(String end)
    {
        ENDPOINT = end;
    }

    public void addAttributes( Map<String, String> map, String gid, String authkey ) {

        if ( !map.isEmpty() ) {
            updateGame( null, null, null, map, null, gid, authkey );
        } else {
            System.out.println("No attributes to add.");
        }


    }
    public void addOptions( Map<String, String> map, String gid, String authkey ) {

        if ( !map.isEmpty() ) {

            updateGame( null, null, null, map, null, gid, authkey );
        } else {
            System.out.println("No options to add.");
        }


    }

    public void execute( String mess, String desc, String mediaID, String gid, String authkey ) {

        updateGame( mess, desc, mediaID, null, null, gid, authkey);
    }

    public void updateGame(String mess, String desc, String mediaID, Map<String, String> attributes, Map<String, String> options, String gid, String authkey)
    {

    	Put put = new Put( ENDPOINT + "games/" + gid, authkey );

        JSONObject json = new JSONObject();

        try {
            if (mess != null) json.put("text", mess);

            if (desc != null) {
                JSONObject meta = new JSONObject();
                meta.put("description", desc);
                json.put("metadata", meta);
            }

            if (mediaID != null) {
                JSONObject context = new JSONObject();
                context.put("media", mediaID);
                json.put("context", context);
            }

            if (attributes != null) {

                JSONArray jsar = new JSONArray();

                Iterator<String> i = attributes.keySet().iterator();

                ArrayList<String> arl = new ArrayList<String>();


                while (i.hasNext()) {
                    arl.add(i.next());
                }

                for (String name : arl) {
                    JSONObject j = new JSONObject();
                    String value = attributes.get(name);
                    j.put("type", "STRING");
                    j.put("name", name);
                    j.put("value", value);

                    jsar.put(j);
                }

                json.put("attributes", jsar);

            }


            if (options != null) {

                JSONArray jsar = new JSONArray();

                ArrayList<String> opts = new ArrayList<String>();
                Iterator<String> it = options.keySet().iterator();
                while (it.hasNext()) {
                    opts.add(it.next());
                }

                Collections.sort(opts);

                for (String name : opts) {
                    JSONObject j = new JSONObject();
                    j.put("text", name);

                    jsar.put(j);
                }

                json.put("options", jsar);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        put.addJson( json );
        
        String response = put.execute();
    }

}
