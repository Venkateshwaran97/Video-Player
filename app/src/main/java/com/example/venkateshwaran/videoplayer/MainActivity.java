package com.example.venkateshwaran.videoplayer;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    int i = 1;
    ListView l;
    private ArrayList<video> videos = new ArrayList<video>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            // Permission has already been granted
            initialSetup(savedInstanceState);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void initialSetup(Bundle savedInstanceState) {
        l = (ListView) findViewById(R.id.list);
        updateplaylist();
        l.setOnItemClickListener(this);
      /*  bar.setBackgroundDrawable(new ColorDrawable("black"));
        List<String> your_array_list = new ArrayList<String>();
        String path = Environment.getExternalStorageDirectory().toString();

        File f = new File(path);
        File[] files = f.listFiles();
        for (File inFile : files) {
            if (inFile.isDirectory()) {
                if (inFile.listFiles().length > 0) {
                your_array_list.add(inFile.getName());
            }}
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );
        l.setAdapter(arrayAdapter);*/
    }


    public void updateplaylist() {
        /*int id = **"The Video's ID"**
        ImageView iv = (ImageView ) convertView.findViewById(R.id.imagePreview);
        ContentResolver crThumb = getContentResolver();
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
        iv.setImageBitmap(curThumb);*/

        ContentResolver musicResolver = getContentResolver();
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(videoUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Video.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Video.Media._ID);
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                long id = thisId;
                videos.add(new video(thisId, thisTitle));
            }
            while (musicCursor.moveToNext());
        }
        Collections.sort(videos, new Comparator<video>() {
            public int compare(video a, video b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        videoadapter songAdt = new videoadapter(this, videos);
        l.setAdapter(songAdt);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(this, videoplay.class);
        myIntent.putExtra("i", position);
        myIntent.putExtra("e", videos);
        startActivity(myIntent);
      /* myIntent.putExtra("i",position);
        final VideoView videoView =
                (VideoView) findViewById(R.id.videoView2);
        long currSong = videos.get(position).getID();
        Uri trackUri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                currSong);
        videoView.setVideoURI(
                trackUri);
        MediaController mediaController = new
                MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new
                                                MediaPlayer.OnPreparedListener()  {
                                                    @Override
                                                    public void onPrepared(MediaPlayer mp) {
                                                        Log.i("hii", "Duration = " +
                                                                videoView.getDuration());
                                                    }
                                                });
        videoView.start();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
