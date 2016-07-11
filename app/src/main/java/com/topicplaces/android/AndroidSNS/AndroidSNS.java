package com.topicplaces.android.AndroidSNS;

import com.topicplaces.android.AndroidSNS.Message.MessageDeleter;
import com.topicplaces.android.AndroidSNS.Message.MessageListRetriever;
import com.topicplaces.android.AndroidSNS.Message.MessagePoster;
import com.topicplaces.android.AndroidSNS.Message.MessageRelated.OptionMaker;
import com.topicplaces.android.AndroidSNS.Message.MessageRetriever;
import com.topicplaces.android.AndroidSNS.Message.MessageUpdater;
import com.topicplaces.android.AndroidSNS.Message.PrivateMessagePoster;
import com.topicplaces.android.AndroidSNS.Topics.PrivateTopicsListRetriever;
import com.topicplaces.android.AndroidSNS.Topics.TopicCreator;
import com.topicplaces.android.AndroidSNS.Topics.TopicDeleter;
import com.topicplaces.android.AndroidSNS.Topics.TopicRetriever;
import com.topicplaces.android.AndroidSNS.Topics.TopicUpdater;
import com.topicplaces.android.AndroidSNS.Topics.TopicsListRetriever;
import com.topicplaces.android.AndroidSNS.Users.RESTLogin;
import com.topicplaces.android.AndroidSNS.Users.UserCreator;
import com.topicplaces.android.AndroidSNS.Users.UserDeleter;
import com.topicplaces.android.AndroidSNS.Users.UserRetriever;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
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
     * Verifies that the email belongs to a valid user at the endpoint.
     *
     * @param email The email to verify.
     * @return The ID of the user (in format "u-[id]"), or the empty string ("") if the user doesn't exist.
     */
    public String verifyEmail(String email)
    {
        ensureConnection();

        return verifyUsername(email);
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

    /**
     *
     * Updates an existing topic.
     *
     * @param title The updated title of the topic. If null, title remains unchanged.
     * @param desc The updated description of the topic. If null, description remains unchanged.
     * @param media The updated media of the topic. If null, media remains unchanged.
     * @param isPrivate True if the topic is private. False if public.
     * @param TID The ID of the topic to be updated.
     * @param authkey The authentication key. See "acquireKey."
     */
    public void updateTopic( String title, String desc, String media, Boolean isPrivate, String TID, String authkey ) {

        ensureConnection();
        TopicUpdater tupd = new TopicUpdater( ENDPOINT );
        tupd.updateTopic(title, desc, media, isPrivate, TID, authkey);
    }

    /**
     *
     * Obtains the description of a given topic.
     *
     * @param TID The ID of the topic (in format "g-[id]")
     * @param isPrivate True if the topic is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The description from the supplied topic ID.
     */
    public String getTopicDescription(String TID, boolean isPrivate, String authkey) {
        ensureConnection();
        TopicRetriever tRet = new TopicRetriever( ENDPOINT );
        return tRet.getDescriptionFromJSON(tRet.getTopicInfoJSON(TID, isPrivate, authkey));
    }

    /**
     *
     * Retrieves all of a user's private topics.
     *
     * @param userID The user ID (in format "u-[id]") to retrieve private topics from.
     * @return A list of all of the user's private topics and their associated IDs (in format "grp-[id]")
     */
    public Map<String, String> getPrivateTopicMap(String userID) {
        ensureConnection();
        PrivateTopicsListRetriever ptlr = new PrivateTopicsListRetriever( ENDPOINT );

        return ptlr.getList(userID);
    }

    /**
     *
     * Creates a new user.
     *
     * @param name User's actual name.
     * @param username User's desired username.
     * @param email User's email.
     * @param password User's desired password.
     * @return The ID of the new user (in format "u-[id]").
     */
    public String newUser(String name, String username, String email, String password)
    {

        ensureConnection();
        UserCreator uCreat = new UserCreator( ENDPOINT );

        return uCreat.createUser(name, username, email, password);

    }

    /**
     *
     * Deletes a user.
     *
     * @param id The ID of the user to delete (in format "u-[id]" )
     * @param authkey The authentication key. See "acquireKey." The authentication key
     *                must be obtained using the user-to-be-deleted's credentials.
     */
    public void deleteUser( String id , String authkey ) {
        ensureConnection();
        UserDeleter udel = new UserDeleter( ENDPOINT );
        udel.execute( id, authkey );
    }

    /**
     *
     * Returns the info contents of a given topic in JSON format.
     *
     * @param TID The ID of the topic (in format "t-[id]")
     * @param isPrivate True if the given topic is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The JSON string of a given Topic
     */
    public String getTopicInfoJson(String TID, boolean isPrivate, String authkey) {
        ensureConnection();
        TopicRetriever tRet = new TopicRetriever( ENDPOINT );
        return tRet.getTopicInfoJSON(TID, isPrivate, authkey);
    }


    /**
     *
     * Obtains the title/content of a given topic.
     *
     * @param TID The ID of the topic (in format "g-[id]")
     * @param isPrivate True if the topic is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The title from the supplied topic ID.
     */
    public String getTopicTitle(String TID, boolean isPrivate, String authkey) {
        ensureConnection();
        TopicRetriever tRet = new TopicRetriever( ENDPOINT );
        return tRet.getTitleFromJSON(tRet.getTopicInfoJSON( TID, isPrivate, authkey));
    }

    /**
     *
     * Retrieves a map of public messages to their corresponding message IDs.
     *
     * @param TID The public topic ID (in format "t-[id]") to obtain messages from.
     * @param authkey The authentication key. See "acquireKey"
     * @return A map of the topic's public messages to their corresponding message IDs.
     */
    public Map<String, String> getPublicMessageMap(String TID, String authkey) {
        ensureConnection();
        MessageListRetriever glr = new MessageListRetriever( ENDPOINT );

        Map<String,String> list = glr.getMap(TID, false, authkey);
        return list;
    }

    /**
     *
     * Retrieves a map of private messages to their corresponding message IDs.
     *
     * @param TID The private topic ID (in format "grp-[id]") to obtain messages from.
     * @param authkey The authentication key. See "acquireKey"
     * @return A map of the topic's private messages to their corresponding message IDs.
     */
    public Map<String, String> getPrivateMessageMap(String TID, String authkey) {
        ensureConnection();
        MessageListRetriever glr = new MessageListRetriever( ENDPOINT );

        Map<String, String> lis = glr.getMap(TID, true, authkey);

        return lis;
    }

    /**
     *
     * Returns the contents of a given message in JSON format.
     *
     * @param GID The ID of the message (in format "g-[id]")
     * @param isPrivate True if the given message is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The JSON string of a given message.
     */
    public String getMessageJSON(String GID, boolean isPrivate, String authkey) {
        ensureConnection();

        MessageRetriever gret = new MessageRetriever( ENDPOINT );
        return gret.getMessageJSON(GID, isPrivate, authkey);
    }

    /**
     *
     * Obtains the title/content of a given message.
     *
     * @param GID The ID of the message (in format "g-[id]")
     * @param isPrivate True if the message is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The title from the supplied message ID.
     */
    public String getMessageTitle(String GID, boolean isPrivate, String authkey) {
        ensureConnection();
        MessageRetriever gret = new MessageRetriever( ENDPOINT );
        return gret.getTitleFromJSON(gret.getMessageJSON(GID, isPrivate, authkey));
    }

    /**
     *
     * Updates an existing message.
     *
     * @param mess The content/title of the message. If null, message remains unchanged.
     * @param desc The description of the message. If null, description remains unchanged.
     * @param mediaID The media (in format "m-[id]") of the message. If null, media remains unchanged.
     * @param GID The message ID (in format "g-[id]").
     * @param authkey The authentication key. See "acquireKey"
     */
    public void updateMessage(String mess, String desc, String mediaID, String GID, String authkey) {
        ensureConnection();
        MessageUpdater gupd = new MessageUpdater( ENDPOINT );
        gupd.execute(mess, desc, mediaID, GID, authkey);
    }

    /**
     *
     * Posts a new message to a specific private topic.
     *
     * @param title The title/name of the message.
     * @param desc The description of the message.
     * @param mediaID The media (in format "m-[id]")of the message. See "newMediaFromLocal" or "newMediaFromURL."
     * @param topicID The ID code of the private topic (in format "grp-[id]")
     * @param authkey The authentication key. See "acquireKey."
     * @return The ID code of the newly created message (in format "g-[id]")
     */

    public String newPrivateMessage(String title, String desc, String mediaID, String topicID, String authkey)
    {
        ensureConnection();

        PrivateMessagePoster tp = new PrivateMessagePoster( ENDPOINT );
        String pM = tp.execute(title, desc, mediaID, authkey, topicID);

        return pM;
    }

    /**
     *
     * Posts a new message to a specific public topic.
     *
     * @param title The title/name of the message.
     * @param desc The description of the message.
     * @param mediaID The media (in format "m-[id]") of the message. See "newMediaFromLocal" or "newMediaFromURL."
     * @param topicID The ID code of the private topic (in format "t-[id]")
     * @param authkey The authentication key. See "acquireKey."
     * @return The ID code of the newly created message (in format "g-[id]")
     */
    public String newPublicMessage(String title, String desc, String mediaID, String topicID, String authkey)
    {
        ensureConnection();

        MessagePoster tp = new MessagePoster( ENDPOINT );

        return tp.execute(title, desc, mediaID, authkey, topicID);
    }

    /**
     *
     * Deletes a message.
     *
     * @param GID The ID of the message to delete (in format "g-[id]" )
     * @param authkey The authentication key. See "acquireKey."
     */
    public void deleteMessage(String GID, String authkey) {
        ensureConnection();
        MessageDeleter gd = new MessageDeleter( ENDPOINT );
        gd.execute( GID, authkey );
    }

    /**
     *
     * Gets the username associated with a valid user at the endpoint.
     *
     * @param user The user ID to retrieve the username for.
     * @return The ID of the user (in format "u-[id]"), or the empty string ("") if the user doesn't exist.
     */
    public String getUsernameFromID(String user) {
        ensureConnection();
        UserRetriever uGett = new UserRetriever( ENDPOINT );

        return uGett.getUsernameFromID(user);
    }

    /**
     * Gets the Json associated with the user.
     *
     * @param UID The userID.
     * @return The JSON of the user in String form.
     */

    public String getUserJson(String UID) {
        UserRetriever ur = new UserRetriever(ENDPOINT);
        return ur.getJSON(UID);
    }

    /**
     * Get the Stream JSON associated with the topic.
     *
     * @param TID The topicID.
     * @param isPrivate True if private. False if public.
     * @param authKey The auth key of the user.
     * @return The Stream JSON in String form.
     */

    public String getTopicStreamJson(String TID, Boolean isPrivate, String authKey) {
        ensureConnection();
        TopicRetriever tr = new TopicRetriever(ENDPOINT);
        return tr.getTopicStreamJSON(TID, isPrivate, authKey);
    }

    /**
     *
     * Obtains the description of a given message.
     *
     * @param GID The ID of the message (in format "g-[id]")
     * @param isPrivate True if the message is private. False if public.
     * @param authkey The authentication key. See "acquireKey"
     * @return The description from the supplied message ID.
     */
    public String getMessageDescription(String GID, boolean isPrivate, String authkey) {
        ensureConnection();
        MessageRetriever gret = new MessageRetriever( ENDPOINT );
        return gret.getDescriptionFromJSON(gret.getMessageJSON(GID, isPrivate, authkey));
    }

    /**
     *
     * Retrieves a list of private message IDs in a given topic.
     *
     * @param TID The private topic ID (in format "grp-[id]") to obtain messages from.
     * @param authkey The authentication key. See "acquireKey"
     * @return A map of the topic's private messages to their corresponding message IDs.
     */
    public List<String> getPrivateMessageGIDList(String TID, String authkey) {
        ensureConnection();
        MessageListRetriever glr = new MessageListRetriever( ENDPOINT );

        List<String> lis = glr.getGIDList( TID, true, authkey);

        return lis;
    }

    /**
     *
     * Adds an option to a message.
     *
     * @param text The option name/content.
     * @param gameID The message ID (in format "g-[id]") to add an option to.
     * @param authkey The authentication key. See "acquireKey"
     * @return The ID of the created option.
     */
    public String newMessageOption(String text, String gameID, String authkey)
    {
        ensureConnection();

        OptionMaker oMak = new OptionMaker( ENDPOINT );
        String optionID = oMak.createOptionForGame(text, gameID, authkey);

        if ( optionID.equals( "" ) )
        {
            //System.err.println( "Unable to create new option" );
            return "";
        }

        return optionID;
    }

}
