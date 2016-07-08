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

public class MainActivity extends AppCompatActivity {

    private Button testButton_PublicTopic, testButton_PrivateTopic, verifyUserID, testButton_DeletePrivateTopic;
    private String authKey, userID;
    private String user, pass;
    final String ENDPOINT = "http://tse.topicplaces.com/api/2/";
    private String topicTitle = "Generic Name";
    private String TID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton_PublicTopic = (Button)findViewById(R.id.testButton_PublicTopic);
        testButton_PublicTopic.setOnClickListener(new View.OnClickListener() {
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
                Log.d("authKey ", authKey);

            }
        });

        testButton_DeletePrivateTopic = (Button)findViewById(R.id.testButton_DeletePrivateTopic);
        testButton_DeletePrivateTopic.setOnClickListener(new View.OnClickListener() {
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
                Log.d("authKey ", authKey);

                sns.deleteTopic(TID, true, authKey);


            }
        });

        testButton_PrivateTopic = (Button)findViewById(R.id.testButton_PrivateTopic);
        testButton_PrivateTopic.setOnClickListener(new View.OnClickListener() {
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
                Log.d("authKey ", authKey);

                TID = sns.newPrivateTopic(topicTitle, authKey);

            }
        });

        verifyUserID = (Button)findViewById(R.id.verifyUserID);
        verifyUserID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    userID = sns.verifyUsername(user);
                    Log.d("u-[id] ", userID);
                }else{
                    Log.d("Network", "Failure to connect");
                }
            }
        });
    }
}
