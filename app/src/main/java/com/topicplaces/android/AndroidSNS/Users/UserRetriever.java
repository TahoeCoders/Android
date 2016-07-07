package com.topicplaces.android.AndroidSNS.Users;

import com.topicplaces.android.AndroidSNS.RESTCalls.Get;

/**
 * Created by TahoeCommie on 7/6/2016.
 */
public class UserRetriever {

    private String ENDPOINT;

    public UserRetriever(String end) {

        ENDPOINT = end;

    }

    public String getJSON(String user) {

        Get get = new Get(ENDPOINT + "users/" + user, null);

        return get.execute();
    }

    public String getUserFromIDorEmail(String user) {

        String s = getJSON(user);

        int ind2 = s.indexOf("{\"val\":\"u-");

        if ( ind2 == -1 ) {
            return null;
        }

        ind2 = ind2 + 8;

        String userID = "";
        while (s.charAt(ind2) != '\"')
        {
            userID += s.charAt(ind2);
            ind2++;
        }

        if ( userID.equals( "" ) ) {
            return null;
        } else {
            return userID;
        }
    }
}
