package com.example.venkateshwaran.videoplayer;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.zip.Inflater;


public class customtoast extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_customtoast);
    }
    public  void showcustomtoast(View v) {
        Toast toast = new Toast(this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        LayoutInflater inflater=getLayoutInflater();
        View appearence= inflater.inflate(R.layout.toastlayout,(ViewGroup)findViewById(R.id.roote));
        toast.setView(appearence);
        toast.show();


    }






}
