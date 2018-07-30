package cn.com.hxx.fakewaterfall.FourComponents.MyService.BindService;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/7/27.
 */

public class MyBindService extends Service {

    public MyBinder myBinder;
    public MediaPlayer mediaPlayer;


    @Override
    public void onCreate() {
        myBinder = new MyBinder();
        mediaPlayer = MediaPlayer.create(this, R.raw.a);
        super.onCreate();
    }



    //2.onBind() 方法,该方法返回用于与服务交互的 IBinder
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    public class MyBinder extends Binder{

        public void play(){
            if (mediaPlayer != null && !mediaPlayer.isPlaying()){
                mediaPlayer.start();
            }
        }

        public void pause(){
            if (mediaPlayer != null && mediaPlayer.isPlaying()){
                mediaPlayer.pause();
            }
        }

        public void stop(){
            if (mediaPlayer != null ){
                mediaPlayer.reset();
                Uri setDataSourceuri = Uri.parse("android.resource://com.android.sim/"+R.raw.a);
                try {
                    mediaPlayer.setDataSource(MyBindService.this, setDataSourceuri);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
