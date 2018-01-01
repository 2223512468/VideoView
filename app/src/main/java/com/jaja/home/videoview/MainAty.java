package com.jaja.home.videoview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import java.io.File;

public class MainAty extends AppCompatActivity {

    private VideoView videoView;
    private String netUrl = "http://192.168.100.101:8080/VideoPlayer/test.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aty);
        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.hl);

    }

    public void player(View v) {
        switch (v.getId()) {
            case R.id.player:
                videoView.start();
                break;
            case R.id.sdPlayer:
                String sd = Environment.getExternalStorageDirectory().toString();
                File file = new File(sd, "/test.mp4");
                videoView.setVideoURI(Uri.fromFile(file));
                break;
            case R.id.netPlayer:
                videoView.setVideoURI(Uri.parse(netUrl));
                break;
            case R.id.mediaPlayer:
                Intent intent = new Intent(this, MedAct.class);
                startActivity(intent);
                break;
        }

    }


}
