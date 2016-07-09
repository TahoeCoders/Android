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

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button DeleteUserButton, VerifyUserButton, MessageTestButton;
    private String authKey, userID;
    private String user, pass, email, name;
    final String ENDPOINT = "http://tse.topicplaces.com/api/2/";
    private String topicTitle = "Generic Test Name";
    private String privateGID = "g-2mir3ps0sjso48b087f";
    private String privateTID = "grp-2mi8328tt5ap0vvtaxt";
    private String publicTID ="t-5snnt6hf4giebtxts6";
    private String publicGID = "g-2kubn36i3h14aou13hh";
    private String UID;
    private String topicDescription = "Generic test Description";
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

                String messageTitle = sns.getMessageTitle(publicGID, false, authKey);
                Log.d("MessageTitle", messageTitle);


            }
        });

        DeleteUserButton = (Button)findViewById(R.id.DeleteUserButton);
        DeleteUserButton.setOnClickListener(new View.OnClickListener() {
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

                sns.deleteUser(UID, authKey);
                Log.d("DeleteUser", "User Deleted");
            }
        });
    }
}
