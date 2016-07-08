package com.topicplaces.android.AndroidSNS;

import com.topicplaces.android.AndroidSNS.Topics.TopicCreator;
import com.topicplaces.android.AndroidSNS.Topics.TopicDeleter;
import com.topicplaces.android.AndroidSNS.Topics.TopicsListRetriever;
import com.topicplaces.android.AndroidSNS.Users.RESTLogin;
import com.topicplaces.android.AndroidSNS.Users.UserRetriever;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class AndroidSNS{

    private String ENDPOINT;

    /**
     *
     * Constructor for the SNSController.
     *
     * @param end The endpoint. ex. http://tse.topicplaces.com/api/2/
     */
    public AndroidSNS(String end){
        ENDPOINT = end;
    }
    //ENDPOINT will be set to http://tse.topicplaces.com/api/2/

    /**
     *
     * Checks that a connection can be made with the endpoint.
     * If not, program stalls until a connection can be reestablished.
     *
     */
    private void ensureConnection() {

        boolean connected = false;

        while ( !connected ) {
            try {
                URLConnection connection = new URL( ENDPOINT ).openConnection();
                connection.connect();

                connected = true;
                //System.out.println("Connection to " + ENDPOINT + " established.");
            } catch ( MalformedURLException e ) {
                throw new IllegalStateException("Bad URL: " + ENDPOINT, e);
            } catch ( IOException e ) {

                System.out.println( "Connection to " + ENDPOINT + " lost, attempting to reconnect..." );

                try {
                    Thread.sleep( 15000 );
                } catch ( InterruptedException s ) {
                    throw new IllegalStateException( "Sleep interrupted.");
                }
            }
        }
    }


    /**
     *
     * Uses an HttpURLConect to obtain an authentication key from the endpoint.
     *
     * @param username The username you'll be obtaining an authkey for.
     * @param password Password for the given username.
     * @return The authentication key for the endpoint.
     */

    public String acquireKey( String username, String password) {
        ensureConnection();

        RESTLogin logg = new RESTLogin(ENDPOINT);
        return logg.login(username, password);
    }

    /**
     *
     * Creates a new public topic.
     *
     * @param title The title/name of the new Public Topic
     * @param authkey The authentication key. See "acquireKey()"
     * @return The ID code of the newly created topic (in format "t-[id]")
     */

    public String newPublicTopic(String title, String authkey) {
        ensureConnection();

        TopicCreator tc = new TopicCreator( ENDPOINT );
        String topicID = tc.createTopic(title, false, authkey);

        if ( topicID.equals( "" ) )
        {
            System.err.println( "Unable to create new topic" );
            return "";
        }

        return topicID;
    }

    /**
     *
     * Retrieves all of a user's public topics.
     *
     * @param userID The user ID (in format "u-[id]") to retrieve public topics from.
     * @return A list of all of the user's public topics and their associated IDs (in format "t-[id]")
     */
    public Map<String, String> getPublicTopicMap(String userID) {
        ensureConnection();
        TopicsListRetriever ptlr = new TopicsListRetriever(ENDPOINT);

        return ptlr.getList(userID);
    }

    /**
     *
     * Gets the username associated with a valid user at the endpoint.
     *
     * @param user The user ID to retrieve the username for.
     * @return The ID of the user (in format "u-[id]"), or the empty string ("") if the user doesn't exist.
     */
    public String verifyUsername(String user) {

        ensureConnection();
        UserRetriever getUserID = new UserRetriever(ENDPOINT);

        return getUserID.getUserFromIDorEmail(user);
    }


    /**
     *
     * Creates a new private topic.
     *
     * @param title The title/name of the new Private Topic
     * @param authkey The authentication key. See "acquireKey()"
     * @return The ID code of the newly created topic (in format "grp-[id]")
     */

    public String newPrivateTopic(String title, String authkey)
    {
        ensureConnection();

        TopicCreator tc = new TopicCreator( ENDPOINT );
        String topicID = tc.createTopic(title, true, authkey);

        if ( topicID.equals( "" ) )
        {
            System.err.println( "Unable to create new topic" );
            return "";
        }

        return topicID;
    }

    /**
     *
     * Deletes a topic.
     *
     * @param id The ID of the topic to delete.
     * @param isPrivate True if the topic is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     */
    public void deleteTopic( String id, Boolean isPrivate, String authkey ) {
        ensureConnection();
        TopicDeleter tdel = new TopicDeleter( ENDPOINT );
        tdel.execute(id, isPrivate, authkey);
    }





}
