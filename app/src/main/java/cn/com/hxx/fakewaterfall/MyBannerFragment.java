package cn.com.hxx.fakewaterfall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.myView.banner.GlideImageViewLoader;
import cn.com.hxx.fakewaterfall.myView.banner.MyBanner;
import cn.com.hxx.fakewaterfall.uti.httputil.HttpResult;
import cn.com.hxx.fakewaterfall.uti.httputil.ICallback;
import cn.com.hxx.fakewaterfall.uti.httputil.MyHttpUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.data.BannerData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.HomeData;

/**
 * Created by apple on 2018/7/2.
 */

public class MyBannerFragment extends Fragment {

    private MyBanner myBanner;
    private List<String> images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mybanner_fragment_layout, container, false);
    }

    public static MyBannerFragment getInstance(){
        MyBannerFragment myBannerFragment = new MyBannerFragment();
        return myBannerFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getData();
    }


    private void initView() {
        myBanner = getActivity().findViewById(R.id.myBanner);
    }

    private void getData() {
        MyHttpUtils.getMainWebService().getHomeData()
                .enqueue(new ICallback<HttpResult<HomeData>>() {
                    @Override
                    public void onSuccess(HttpResult<HomeData> result) throws Exception {
                        //加载banner数据
                        handlerHeader(result);
                    }
                });
    }

    private void handlerHeader(HttpResult<HomeData> result) {
        List<BannerData> banners = result.getBody().getActivities();
        for (BannerData banner : banners) {
            images.add(banner.getBanner());
        }
        myBanner.setLoader(new GlideImageViewLoader())
                .setImageUrl(images)
                .start();
    }
}
