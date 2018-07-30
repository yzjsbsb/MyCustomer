package cn.com.hxx.fakewaterfall.Module.Handler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/30.
 * Handler：是一个消息分发对象，进行发送和处理消息，并且其 Runnable 对象与一个线程的 MessageQueue 关联。
 作用：调度消息，将一个任务切换到某个指定的线程中去执行,通过 Handler 切换到 UI 线程，解决子线程中无法访问 UI 的问题。
 */

public class MyHandlerFragment extends BaseFragment {

    private Myhandler myhandler = new Myhandler(this.getActivity());
    private static Button btn_test;

    @Override
    public int getContentView() {
        return R.layout.fragment_myhandler;
    }

    public static MyHandlerFragment getInstance(){
        return new MyHandlerFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        btn_test = getView().findViewById(R.id.btn_test);
        //handler使用方式1：
        /***
         * 创建一个工作线程，继承 Thread，重新 run 方法，处理耗时操作
         创建一个 Message 对象，设置 what 标志及数据
         通过 sendMessage 进行投递消息
         创建一个handler，重写 handleMessage 方法，根据 msg.what 信息判断，接收对应的信息，再在这里更新 UI。
         */
        getView().findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            //模拟耗时操作，如网络访问
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = 1;
                        message.obj = 2;
                        myhandler.sendMessage(message);
                    }
                }.start();

            }
        });

        //handler使用方式2
        /***
         * 创建一个工作线程，实现 Runnable 接口，实现 run 方法，处理耗时操作
         创建一个 handler，通过 handler.post/postDelay，投递创建的 Runnable，在 run 方法中进行更新 UI 操作。
         */
        getView().findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            //模拟耗时操作，如网络访问
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        myhandler.post(new Runnable() {
                            @Override
                            public void run() {
                                //当 Activity finish 时,在 onDestroy 方法中释放了一些资源。此时 Handler 执行到 handlerMessage 方法,但相关资源已经被释放,从而引起空指针的异常。因此需要try catch
                                try {
                                    //更新ui的操作
                                    btn_test.setVisibility(View.VISIBLE);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });

                    }
                }.start();
            }
        });
    }

    private static class Myhandler extends Handler{

        WeakReference<Activity> weakReference;

        public Myhandler(Activity activity){
            weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //当 Activity finish 时,在 onDestroy 方法中释放了一些资源。此时 Handler 执行到 handlerMessage 方法,但相关资源已经被释放,从而引起空指针的异常。因此需要try catch
            try {
                if (msg.what == 1){
                    btn_test.setVisibility(View.INVISIBLE);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
