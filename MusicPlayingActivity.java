package com.example.mark.SongFeed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;


public class MusicPlayingActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "track";
    public static final String SERVICECMD = "com.android.music.musicservicecommand";

    GoogleApiClient mGoogleApiClient;
    Message mActiveMessage;
    Typeface typeface;

    TextView artist;
    TextView album;
    TextView title;



    private void publish(String message) {
        try {
            Log.i(TAG, "Publishing message: " + message);
            mActiveMessage = new Message(message.getBytes());
            Nearby.Messages.publish(mGoogleApiClient, mActiveMessage);

        }catch(RuntimeException r){
            Context context = getApplicationContext();
            CharSequence text = r+" Cannot publish. Try launch music player first, then open SongFeed!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
 }

    private void unpublish() {
        Log.i(TAG, "Unpublishing.");
        if (mActiveMessage != null)
                {
            Nearby.Messages.unpublish(mGoogleApiClient, mActiveMessage);
                    mActiveMessage = null;

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Nearby.MESSAGES_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .enableAutoManage(this, this)
                .build();

        setTitle("SongFeed");

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Sawasdee.ttf");

        setContentView(R.layout.activity_music_playing);
        artist = (TextView) findViewById(R.id.artist);
        album = (TextView) findViewById(R.id.album);
        title = (TextView) findViewById(R.id.title);


        artist.setTypeface(typeface);
        album.setTypeface(typeface);
        title.setTypeface(typeface);

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");                //intentFilters assigned to music-playing activities in other applications
        iF.addAction("com.android.music.queuechanged");
        iF.addAction("com.android.music.musicservicecommand.next");
        iF.addAction("com.android.music.musicservicecommand.previous");
/*
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.sonyericsson.music.metachanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.andrew.apollo.metachanged");
*/

         registerReceiver(mReceiver, iF);                                                         //BroadcastReceiver listens for data broadcast by other activities

    }



    public BroadcastReceiver mReceiver = new BroadcastReceiver() {       //new instance of BroadcastReceiver

        //@Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            Log.v("tag ",action+" / "+cmd);
            String artists = intent.getStringExtra("artist");                                     //receiving data through IntentFilters, converting received data to String
            String albums = intent.getStringExtra("album");
            String tracks = intent.getStringExtra("track");
            Log.v("music ", artists + ":" + albums + ":" + tracks);

            artist.setText(artists);
            album.setText(albums);                                                                //setting String data to TextView
            title.setText(tracks);

            publish("Someone is listening to "+tracks+" from "+albums+" by "+artists);

            unregisterReceiver(mReceiver);

        }

    };

    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onStop() {
        unpublish();
        super.onStop();
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    protected void onSaveInstanceState(Bundle savedInstanceState){
        MusicPlayingActivity.super.onSaveInstanceState(savedInstanceState);             //saves data before screen orientation changes
        savedInstanceState.putString("saved_message1", artist.getText().toString());
        savedInstanceState.putString("saved_message2", album.getText().toString());
        savedInstanceState.putString("saved_message3", title.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        MusicPlayingActivity.super.onRestoreInstanceState(savedInstanceState);

        artist.setText(savedInstanceState.getString("saved_message1"));          //loads saved data after screen orientation changes
        album.setText(savedInstanceState.getString("saved_message2"));
        title.setText(savedInstanceState.getString("saved_message3"));

    }


}