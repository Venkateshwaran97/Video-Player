package com.example.venkateshwaran.videoplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

public class StretchVideoView extends VideoView {

    private int mVideoWidth;
    private int mVideoHeight;
    public StretchVideoView(Context context) {
        super(context);
    }

    public StretchVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StretchVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVideoWidth = 0;
        mVideoHeight = 0;
    }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            //Log.i("@@@@", "onMeasure");
            int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
            int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if ( mVideoWidth * height  > width * mVideoHeight ) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth;
                } else if ( mVideoWidth * height  < width * mVideoHeight ) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight;
                } else {
                    //Log.i("@@@", "aspect ratio is correct: " +
                    //width+"/"+height+"="+
                    //mVideoWidth+"/"+mVideoHeight);
                }

            }
            //Log.i("@@@@@@@@@@", "setting size: " + width + 'x' + height);
            setMeasuredDimension(width, height);
        }

}