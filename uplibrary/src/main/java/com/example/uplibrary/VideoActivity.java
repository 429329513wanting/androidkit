package com.example.uplibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.maning.mnvideoplayerlibrary.listener.OnCompletionListener;
import com.maning.mnvideoplayerlibrary.listener.OnNetChangeListener;
import com.maning.mnvideoplayerlibrary.listener.OnScreenOrientationListener;
import com.maning.mnvideoplayerlibrary.player.MNViderPlayer;

public class VideoActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    MNViderPlayer mnViderPlayer;
    int playPosition = 0;
    String videoUrl = "https://hqctv.oss-cn-beijing.aliyuncs.com/%E7%BE%8E%E5%9B%BD/%E5%A1%9E%E7%8F%AD%E5%B2%9B/MGSB001junjiandao.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        mnViderPlayer = findViewById(R.id.mn_player);
        mnViderPlayer.setWidthAndHeightProportion(16,9);
        mnViderPlayer.setIsNeedBatteryListen(true);
        mnViderPlayer.setIsNeedNetChangeListen(true);
        //mnViderPlayer.setDataSource(videoUrl,"塞班岛");
        mnViderPlayer.playVideo(videoUrl,"塞班岛",SPUtils.getInstance().getInt("position",0));

        mnViderPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                LogUtils.d("播放完成");
            }
        });
        mnViderPlayer.setOnNetChangeListener(new OnNetChangeListener() {
            @Override
            public void onWifi(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onMobile(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onNoAvailable(MediaPlayer mediaPlayer) {

            }
        });
        mnViderPlayer.setOnScreenOrientationListener(new OnScreenOrientationListener() {
            @Override
            public void orientation_landscape() {



            }

            @Override
            public void orientation_portrait() {

            }
        });

    }

    @Override
    protected void onDestroy() {

        if (mnViderPlayer != null){
            SPUtils.getInstance().put("position",mnViderPlayer.getVideoCurrentPosition());
            mnViderPlayer.destroyVideo();
            mnViderPlayer = null;
        }
        super.onDestroy();
    }
}
