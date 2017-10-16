package com.example.mark.SongFeed;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import static com.example.mark.SongFeed.MusicPlayingActivity.TAG;


public class SubActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    GoogleApiClient nGoogleApiClient;
    MessageListener mMessageListener;
    SubscribeOptions options;


    TextView track1;
    TextView album1;
    TextView artist1;

    Typeface typeface;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_sub);

        setTitle("SongFeed");

        /*
        the Google API Client allows integration with Google Play Services which]
        in turn, allow access to all the Google APIs including Nearby Messages
        */
        nGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .build();

        options = new SubscribeOptions.Builder()
                //.setCallback(myCallback)
                .build();

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Sawasdee.ttf");  //changes the font of the text

                    //data to receive
        track1 = (TextView) findViewById(R.id.track1);
        album1 = (TextView) findViewById(R.id.album1);      //defining the text fields as variables
        artist1 = (TextView) findViewById(R.id.artist1);

        track1.setTypeface(typeface);
        album1.setTypeface(typeface);                      //sets the text to the font above
        artist1.setTypeface(typeface);



///////////////////////////////////////////////////////////////////////////////////////////////

        //Track
        mMessageListener = new MessageListener() {
            @Override
            public void onFound(Message message) {
                String messageAsString = new String(message.getContent());
                Log.i(TAG, "Found message: " + messageAsString);
                track1.setText(messageAsString);
            }

            @Override
            public void onLost(Message message) {
                String messageAsString = new String(message.getContent());
                Log.i(TAG, "Lost sight of message: " + messageAsString);
            }
        };


    }

    // Subscribe to receive messages.
    private void subscribe() {
        try{
        Log.i(TAG, "Subscribing.");
        Nearby.Messages.subscribe(nGoogleApiClient, mMessageListener, options );

    }
        catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    private void unsubscribe() {
        Log.i(TAG, "Unsubscribing.");
        Nearby.Messages.unsubscribe(nGoogleApiClient, mMessageListener);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////


         //put subscribed data in try/catch statement- catch unknown track/album/artist display as textview


    protected void onSaveInstanceState(Bundle savedInstanceState){
        SubActivity.super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("saved_message", track1.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        SubActivity.super.onRestoreInstanceState(savedInstanceState);

        track1.setText(savedInstanceState.getString("saved_message"));
    }


        @Override
    public void onConnected(Bundle b) {
        subscribe();
    }


    @Override
    public void onStop() {
        unsubscribe();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}