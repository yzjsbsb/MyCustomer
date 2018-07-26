package cn.com.hxx.fakewaterfall.CustomerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.CustomerView.banner.MyBannerFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerview.CustomerViewFragment;
import cn.com.hxx.fakewaterfall.CustomerView.customerviewgroup.CustomerViewGroupFragment;
import cn.com.hxx.fakewaterfall.CustomerView.expandview.ExpandFragment;
import cn.com.hxx.fakewaterfall.CustomerView.layoutmanager.CustomLayoutManagerFragemtn;
import cn.com.hxx.fakewaterfall.MyViewPagerAdapter;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;

/**
 * Created by apple on 2018/7/26.
 */

public class CustomerViewActivity extends BaseActivity {


    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int setLayoutView() {
        return R.layout.activity_cutomerview_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        fragmentList.add(ExpandFragment.getInstatnce());
        fragmentList.add(CustomerViewFragment.getInstatnce());
        fragmentList.add(MyBannerFragment.getInstance());
        fragmentList.add(CustomerViewGroupFragment.getInstance());
        fragmentList.add(CustomLayoutManagerFragemtn.getInstance());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager  = findViewById(R.id.viewPager);
        viewPager.setAdapter(myViewPagerAdapter);
        //设置每个fragment左右两边最多的缓存数量，这样，就不会反复重新加载fragment了
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);

    }
}
