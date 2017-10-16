package com.example.mark.SongFeed;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public final static int RETURN = 0;
    public final static int OTHER = 1;
    public final static int FEED = 2;


    TextView messageTextView;
    //Button getMessageButton;
    Button otherButton;
    Button feed;

    Typeface typeface;
    TypefaceSpan typefaceSpan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        messageTextView = (TextView) findViewById(R.id.textView);

        setTitle("SongFeed");

        //getMessageButton = (Button) findViewById(R.id.button);
        otherButton = (Button) findViewById(R.id.other);
        feed = (Button) findViewById(R.id.feed);

        typeface = Typeface.createFromAsset(getAssets(), "fonts/Sawasdee.ttf");
        messageTextView.setTypeface(typeface);


        otherButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent getResult = new Intent(getApplicationContext(), MusicPlayingActivity.class);
                startActivityForResult(getResult, OTHER);

            }
        });

        feed.setOnClickListener(new View.OnClickListener(){
            //@Override
            public void onClick(View v) {
                Intent getResult =  new Intent(getApplicationContext(), SubActivity.class);
                startActivityForResult(getResult, FEED);
            }

        });

    }

}