package cn.com.hxx.fakewaterfall.FourComponents.MyService.StartService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/7/27.
 */

//当同步处理请求时，可以使用IntentService
public class MyStartService extends Service {

    MediaPlayer mediaPlayer;

    //onCreate()方法只会在首次调用startService时调用
    @Override
    public void onCreate() {
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.a);
        }

        super.onCreate();
    }

    //onStartCommand(Intent intent, int flags, int startId)方法会在每次调用startService时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String key = intent.getExtras().getString("key");
        switch (key){
            case "play":
                play();
                break;
            case "pause":
                pause();
                break;
            case "stop":
                stop();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void play(){
        if (mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    private void pause(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    private void stop(){
        if (mediaPlayer != null ){
            mediaPlayer.stop();
        }
        //stopSelf() 来自行停止运行或通过其他组件调用 stopService() 来停止服务。服务停止后，系统会将其销毁,因此stop之后start会重新生成MyStartService，重新onCreate()
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
