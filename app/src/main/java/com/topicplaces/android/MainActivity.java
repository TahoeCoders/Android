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

    private Button VerifyEmailButton, CreatePrivateTopicButon, UpdateTopicButton;
    private String authKey, userID;
    private String user, pass, email;
    final String ENDPOINT = "http://tse.topicplaces.com/api/2/";
    private String topicTitle = "Generic Test Name";
    private String TID, UID;
    private String topicDescription = "Generic test Description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VerifyEmailButton = (Button)findViewById(R.id.VerifyEmailButon);
        VerifyEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                user = "stanleyr001";
                pass = "stanleyr001";
                email = "stanleyr001@gmail.com";

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

                UID = sns.verifyEmail(email);

                Log.d("UID", UID);

            }
        });

        UpdateTopicButton = (Button)findViewById(R.id.UpdateTopicButton);
        UpdateTopicButton.setOnClickListener(new View.OnClickListener() {
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

                sns.updateTopic(topicTitle, topicDescription, null, true, TID, authKey);
                Log.d("UpdateTopic", "Topic updated");

            }
        });

        CreatePrivateTopicButon = (Button)findViewById(R.id.CreatePrivateTopicButton);
        CreatePrivateTopicButon.setOnClickListener(new View.OnClickListener() {
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

                Log.d("PrivateTID", TID);

            }
        });
    }
}
