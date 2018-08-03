package cn.com.hxx.fakewaterfall.Module.LruCache;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.hxx.fakewaterfall.Module.Http.Utils.AsynConnectionManager;
import cn.com.hxx.fakewaterfall.Module.LruCache.DiskLruCache.DiskLruCache;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/8/2.
 */

public class MyLruCacheFragment extends BaseFragment implements View.OnClickListener{

    LruCache<String, Bitmap> mMemoryCache;//内存缓存
    DiskLruCache mDiskLruCache;
    private ImageView iv_pic1;
    private ImageView iv_pic2;
    private TextView tv_cache1;
    private TextView tv_cache2;
    private Handler handler;

    final String imageUrl = "http://static.woqi.me/tshirt_style/preview_image/107/zhanshi-diy.jpg?imageView2/2/w/250/h/250/format/png/q/85";

    @Override
    public int getContentView() {
        return R.layout.fragment_mylrucache;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initLruCache();
    }
    private void initView() {
        getView().findViewById(R.id.btn_getpic1).setOnClickListener(this);
        getView().findViewById(R.id.btn_delpic1).setOnClickListener(this);
        getView().findViewById(R.id.btn_getpic2).setOnClickListener(this);
        getView().findViewById(R.id.btn_delpic2).setOnClickListener(this);
        iv_pic1 = getView().findViewById(R.id.iv_pic1);
        iv_pic2 = getView().findViewById(R.id.iv_pic2);
        tv_cache1 = getView().findViewById(R.id.tv_cache1);
        tv_cache2 = getView().findViewById(R.id.tv_cache2);
    }


    private void initLruCache() {
        //LruCache
        //获取程序最大内存
        int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);
        //1/8作为内存缓存的大小
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //以每张图片的大小作为size
                return value.getByteCount() / 1024;
            }
        };
        //DiskLruCache
        //参数1：缓存路径  参数2：app版本 参数3：一个key对应的value值个数，一般添1。 参数4：缓存文件最大值
        //标准的open写法
        try {
            File cacheDir = MyUtils.getDiskCacheDir(getContext(), "image");
            if (!cacheDir.exists()){
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, MyUtils.getAppVersion(getContext()), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        handler = new Handler();
    }


    @Override
    public void onClick(View v) {
        final String key = MyUtils.getMD5String(imageUrl);
        switch (v.getId()){
            case R.id.btn_getpic1:
                final Bitmap bitmap = mMemoryCache.get(imageUrl);
                if (bitmap == null){
                    AsynConnectionManager.downLoadPic(imageUrl, new AsynConnectionManager.CallBack<Bitmap>() {
                        @Override
                        public void onSucces(Bitmap reponse) {
                            iv_pic1.setImageBitmap(reponse);
                            mMemoryCache.put(imageUrl, reponse
                            );
                        }
                    });

                }else {
                    iv_pic1.setImageBitmap(bitmap);
                    tv_cache1.setText(mMemoryCache.size() + "");
                }

                break;
            case R.id.btn_delpic1:
                mMemoryCache.remove(imageUrl);
                tv_cache1.setText(mMemoryCache.size() + "");
                iv_pic1.setImageBitmap(null);
                break;
            case R.id.btn_getpic2:
                try {
                    DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
                    if (snapshot != null){
                        //代表已经有缓存了
                        InputStream inputStream = snapshot.getInputStream(0);
                        Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);
                        iv_pic2.setImageBitmap(bitmap1);
                        tv_cache2.setText(mDiskLruCache.size()+"");
                    }else {
                        //下载
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DiskLruCache.Editor edit = mDiskLruCache.edit(key);
                                    OutputStream outputStream = edit.newOutputStream(0);
                                    final Bitmap bitmap = downloadUrlToStream(outputStream, imageUrl);
                                    if (bitmap != null) {
                                        //commit()方法表示写入缓存成功
                                        edit.commit();
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                iv_pic2.setImageBitmap(bitmap);
                                            }
                                        });
                                    } else {
                                        edit.abort();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_delpic2:

                try {
                    mDiskLruCache.remove(key);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tv_cache2.setText(mDiskLruCache.size() + "");
                iv_pic2.setImageBitmap(null);
                break;
        }
    }

    @Override
    public void onDestroy() {
        try {
            /**
             * 这个方法用于将DiskLruCache关闭掉，是和open()方法对应的一个方法。关闭掉了之后就不能再调用DiskLruCache中任何操作缓存数据的方法，通常只应该在Activity的onDestroy()方法中去调用close()方法。
             */
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private Bitmap downloadUrlToStream(OutputStream outputStream, String path){
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            URL url = new URL(path);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            //将输入写进DiskLruCache
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int b = -1;
            while ((b = bufferedInputStream.read()) != -1){
                bufferedOutputStream.write(b);
            }
            return BitmapFactory.decodeStream(httpURLConnection.getInputStream());
        }catch (Exception e){
            return null;
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            try {
                if (bufferedInputStream != null){
                    bufferedInputStream.close();
                }
                if (bufferedOutputStream != null){
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
