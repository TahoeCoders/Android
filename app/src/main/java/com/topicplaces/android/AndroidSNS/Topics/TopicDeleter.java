package com.topicplaces.android.AndroidSNS.Topics;

import com.topicplaces.android.AndroidSNS.RESTCalls.Delete;

/**
 * Created by Stanley R on 7/7/2016.
 */
public class TopicDeleter {

    private String ENDPOINT;

    public TopicDeleter(String end)
    {
        ENDPOINT = end;
    }

    public void execute( String tid, Boolean isPrivate, String authkey ) {

        Delete delete = null;
        if ( isPrivate ) {
            delete = new Delete( ENDPOINT + "groups/" + tid, authkey );
        } else {
            delete = new Delete(ENDPOINT + "topics/" + tid, authkey );
        }

        delete.execute();
    }

}
