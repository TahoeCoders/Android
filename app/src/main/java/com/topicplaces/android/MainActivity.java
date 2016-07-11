package com.topicplaces.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.topicplaces.android.AndroidSNS.AndroidSNS;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button FollowTestButton, VerifyUserButton, MessageTestButton;
    private String authKey, userID;
    private String user, pass, email, name;
    final String ENDPOINT = "http://tse.topicplaces.com/api/2/";

    private String privateGID = "g-2mir3sdgdwhdkbhzf6i";
    private String privateTID = "grp-2mi8328tt5ap0vvtaxt";
    private String publicTID ="t-2millmwkjg5job59e4a";
    private String publicGID = "g-2mj6c9p0k5t3ixtz88w";
    private String UID;

    private String messageDescription = "A message description to test adding a new message";
    private String messageTitle = "A new message from AndroidSNS!!";
    private Map privateMap, publicMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerifyUserButton = (Button)findViewById(R.id.VerifyUserButton);
        VerifyUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                user = "stanleyr001";
                pass = "stanleyr001";

                AndroidSNS sns = new AndroidSNS(ENDPOINT);

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    authKey = sns.acquireKey(user, pass);
                }else{
                    Log.d("Network", "Failure to connect");
                }

                UID = sns.verifyUsername(user);
                Log.d("UID", UID);
                String userJson = sns.getUserJson(UID);
                Log.d("UserJson", userJson);

            }
        });

        MessageTestButton = (Button)findViewById(R.id.MessageTestButton);
        MessageTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                user = "stanleyr001";
                pass = "stanleyr001";;

                AndroidSNS sns = new AndroidSNS(ENDPOINT);

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();


                if (networkInfo != null && networkInfo.isConnected()) {
                    authKey = sns.acquireKey(user, pass);
                }else{
                    Log.d("Network", "Failure to connect");
                }

                /*
                Map<String,String> optionsID = sns.getOptionsIDMap(privateGID, true, authKey);
                Map<String,String> optionsAnswers = sns.getOptionsAnswerMap(privateGID, true, authKey);

                for (Map.Entry<String, String> entry : optionsID.entrySet())
                {
                    Log.d("OptionsID", entry.getKey() + "=" + entry.getValue());
                }

                for (Map.Entry<String, String> entry : optionsAnswers.entrySet())
                {
                    Log.d("OptionsAnswers", entry.getKey() + "=" + entry.getValue());
                }
                */

                Map<String, String> attributesMap = new HashMap<String, String>();
                attributesMap.put("Jane", "Black");
                attributesMap.put("Ken", "White");

                Map<String,String> attributes = sns.getAttributes(privateGID, true, authKey);
                for (Map.Entry<String, String> entry : attributes.entrySet())
                {
                    Log.d("Attributes", entry.getKey() + " = " + entry.getValue());
                }

            }
        });

        FollowTestButton = (Button)findViewById(R.id.FollowTestButton);
        FollowTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                user = "stanleyr001";
                pass = "stanleyr001";

                AndroidSNS sns = new AndroidSNS(ENDPOINT);

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    authKey = sns.acquireKey(user, pass);
                }else{
                    Log.d("Network", "Failure to connect");
                }

                Map<String,String> subMap = sns.getFollowerSubMap(sns.verifyUsername(user));
                for (Map.Entry<String, String> entry : subMap.entrySet()) {
                    Log.d("Subscribers", entry.getKey() + " = " + entry.getValue());
                }
                Map<String,String> IDMap = sns.getFollowerIDMap(sns.verifyUsername(user));
                for (Map.Entry<String, String> entry : IDMap.entrySet()) {
                    Log.d("ID's", entry.getKey() + " = " + entry.getValue());
                }

            }
        });
    }
}
