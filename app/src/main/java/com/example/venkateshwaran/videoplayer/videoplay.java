package com.example.venkateshwaran.videoplayer;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.view.GestureDetector.SimpleOnGestureListener;


public class videoplay extends Activity implements GestureDetection.SimpleGestureListener {
     VideoView videoView;
    int pos;
    ArrayList<video> videos;
    AudioManager audioManager;
    GestureDetection detector;
    int currentPosition;
    int currentVolume;
    LayoutInflater inflater;
    View appearence;
    TextView x;


    String TAG = "videoplayer";
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_videoplay);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        videoView = (VideoView) findViewById(R.id.videoView);
        detector = new GestureDetection(this, this);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int h = displaymetrics.heightPixels;
        int w = displaymetrics.widthPixels;
        videoView.setLayoutParams(new FrameLayout.LayoutParams(w,h));

        x=(TextView)findViewById(R.id.textView2);
         pos = getIntent().getIntExtra("i", 0);
          videos=(ArrayList<video>)getIntent().getSerializableExtra("e");

        long currSong = videos.get(pos).getID();
        Uri trackUri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                currSong);
        videoView.setVideoURI(
                trackUri);

        MediaController mediaController = new
                MediaController(this);

        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
                long currSong = videos.get(pos).getID();
                Uri trackUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        currSong);
                videoView.setVideoURI(
                        trackUri);
                videoView.start();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
               videoView.start();
                long currSong = videos.get(pos).getID();
                Uri trackUri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        currSong);
                videoView.setVideoURI(
                        trackUri);
            }
        });

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new
                                                MediaPlayer.OnPreparedListener()  {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        Log.i(TAG, "Duration = " +
                                                                videoView.getDuration());
                                                    }
                                                });

        videoView.start();




    }


    public void playPrev(){
        pos--;
        if(pos<0) pos=videos.size()-1;


    }
    public void playNext(){
        pos++;
        if(pos>=videos.size()) pos=0;

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSwipe(int direction) {

       final  Toast toast ;
        switch (direction) {




            case GestureDetection.SWIPE_RIGHT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() + 2000;
                videoView.seekTo(currentPosition);
                toast = Toast.makeText(getApplicationContext()," ",Toast.LENGTH_SHORT);
                 inflater=getLayoutInflater();
                 appearence= inflater.inflate(R.layout.toastlayout,(ViewGroup)findViewById(R.id.roote));
                //toast.setView(appearence);
                toast.setText(" " + TimeUnit.MILLISECONDS.toMinutes((long) currentPosition) + " : " +
                        (TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentPosition))));


                toast.setGravity(Gravity.CENTER,0,0);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(30);
                toast.show();
               Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 20);
                break;
            case GestureDetection.SWIPE_LEFT:

                currentPosition = videoView.getCurrentPosition();
                currentPosition = videoView.getCurrentPosition() - 2000;
                videoView.seekTo(currentPosition);
                toast = Toast.makeText(getApplicationContext()," ",Toast.LENGTH_SHORT);
                inflater=getLayoutInflater();
                appearence= inflater.inflate(R.layout.toastlayout,(ViewGroup)findViewById(R.id.roote));
                toast.setText(" " + TimeUnit.MILLISECONDS.toMinutes((long) currentPosition) + " : " +
                        (TimeUnit.MILLISECONDS.toSeconds((long) currentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) currentPosition))));


                toast.setGravity(Gravity.CENTER,0,0);
                group = (ViewGroup) toast.getView();
                 messageTextView = (TextView) group.getChildAt(0);
               // messageTextView.setBackgroundColor(0000);
                messageTextView.setTextSize(30);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 20);
                break;


            case GestureDetection.SWIPE_DOWN:

                currentVolume = audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        currentVolume - 2, 0);
                toast = Toast.makeText(getApplicationContext()," ",Toast.LENGTH_SHORT);
                 inflater=getLayoutInflater();
                 appearence= inflater.inflate(R.layout.toastlayout,(ViewGroup)findViewById(R.id.roote));

                int v=currentVolume;
                v=((v*100)/15);

                toast.setText(" "+ v+" % ");
                toast.setGravity(Gravity.CENTER,0,0);
               // toast.setView(appearence);
                //x.setText(currentVolume);
                group = (ViewGroup) toast.getView();
                 messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(30);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 20);
                break;
            case GestureDetection.SWIPE_UP:

                currentVolume = audioManager
                        .getStreamVolume(AudioManager.STREAM_MUSIC);

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        currentVolume + 2, 0);


                inflater=getLayoutInflater();
                appearence= inflater.inflate(R.layout.toastlayout,(ViewGroup)findViewById(R.id.roote));
                  v=currentVolume;
                 v=((v*100)/15);
                toast = Toast.makeText(getApplicationContext(),"  ",Toast.LENGTH_SHORT);
                toast.setText(" "+ v+" % ");
                toast.setGravity(Gravity.CENTER,0,0);
                 group = (ViewGroup) toast.getView();
                messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(30);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 20);
                break;

        }
    }


}
