package com.topicplaces.android.AndroidSNS.Users;


import com.topicplaces.android.AndroidSNS.RESTCalls.Delete;

/**
 * Created by servicedog on 7/8/15.
 */
public class UserDeleter {

    private String ENDPOINT;

    public UserDeleter(String end)
    {
        ENDPOINT = end;
    }

    public void execute( String uid, String authkey )
    {
    	Delete delete = new Delete( ENDPOINT + "users/" + uid, authkey );
    	delete.execute();
    }

}
