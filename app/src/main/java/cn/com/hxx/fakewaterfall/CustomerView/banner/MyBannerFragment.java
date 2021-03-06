package cn.com.hxx.fakewaterfall.CustomerView.banner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.CustomerView.tablayout.MyTabLayout;
import cn.com.hxx.fakewaterfall.CustomerView.tablayout.MyTabLayoutActivity;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.CustomerView.banner.imageloader.GlideImageViewLoader;
import cn.com.hxx.fakewaterfall.uti.httputil.HttpResult;
import cn.com.hxx.fakewaterfall.uti.httputil.ICallback;
import cn.com.hxx.fakewaterfall.uti.httputil.MyHttpUtils;
import cn.com.hxx.fakewaterfall.uti.MyUtils;
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
        getView().findViewById(R.id.btn_startTablayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyTabLayoutActivity.class));
            }
        });
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
        myBanner.addOnBannerClickListenner(new MyBanner.BannerClickListenner() {
            @Override
            public void OnBannerItemClick(View view, int position) {
                MyUtils.t(getContext(), "回调:" + position);
            }
        });
        myBanner.addOnBannerLongClickListenner(new MyBanner.BannerLongClickListenner() {
            @Override
            public void OnBanneItemLongClick(View view, int position) {
                MyUtils.t(getContext(), "Long:" + position);
            }
        });


        myBanner.setLoader(new GlideImageViewLoader())
                .setImageUrl(images)
                .start();
    }
}
