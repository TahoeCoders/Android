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

    Button testButton;
    String authKey;
    String username;
    String password;
    final String ENDPOINT = "http://tse.topicplaces.com/api/2/";
    String topicName = "OfficialTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testButton = (Button)findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrictMode.ThreadPolicy policy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);

                username = "stanleyr001";
                password = "stanleyr001";

                AndroidSNS AndroidSNSController = new AndroidSNS(ENDPOINT);

                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    authKey = AndroidSNSController.acquireKey(username, password);
                }else{
                    Log.d("Network", "Failure to connect");
                }
                Log.d("authKey ", authKey);


                //Now create new topic

                String tid = AndroidSNSController.newPublicTopic(topicName, authKey);
                Log.d("TID", tid);
























            }
        });

    }
}
