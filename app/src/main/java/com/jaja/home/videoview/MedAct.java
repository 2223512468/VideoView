package com.jaja.home.videoview;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import java.io.IOException;

/**
 * Created by tu on 2017/12/30.
 */
public class MedAct extends Activity implements SurfaceHolder.Callback, SeekBar.OnSeekBarChangeListener {

    private SurfaceView mSurfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private SeekBar seekBar;
    private String netUrl = "http://192.168.100.101:8080/VideoPlayer/test.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_media_player);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);
        /**
         * 加载raw文件下视频
         */
        // mediaPlayer = MediaPlayer.create(this, R.raw.hl);
        ///////////////////////////////////////////

        /**
         * 加载sd卡内的视频
         */
     /*   mediaPlayer = new MediaPlayer();
        String sd = Environment.getExternalStorageDirectory().toString();
        File file = new File(sd, "/test.mp4");
        Toast.makeText(this, "exist" + file.exists(), Toast.LENGTH_SHORT).show();
        try {
            mediaPlayer.setDataSource(this, Uri.fromFile(file));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        ///////////////////////////////////////////////////////////

        /**
         * 加载网络视频
         */
        seekBar.setOnSeekBarChangeListener(this);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, Uri.parse(netUrl));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void player(View v) {
        switch (v.getId()) {
            case R.id.player:
                mediaPlayer.start();
                break;
            case R.id.sdPlayer:
                mediaPlayer.start();
                break;
            case R.id.netPlayer:
                mediaPlayer.start();
                new MyThread().start();
                break;
            case R.id.takePlayer:
                Intent intent = new Intent(this, TextAct.class);
                startActivity(intent);
                break;
            case R.id.listPlayer:
                Intent intent1 = new Intent(this, ListAct.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mediaPlayer != null) {
//            mediaPlayer.reset();
            mediaPlayer.release();
        }
        flag = false;
    }

    private boolean flag = true;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        mediaPlayer.seekTo(progress);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (flag) {
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
        flag = false;
    }
}
