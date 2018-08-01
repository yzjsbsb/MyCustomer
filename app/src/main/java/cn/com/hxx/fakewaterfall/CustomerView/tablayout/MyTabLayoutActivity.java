package cn.com.hxx.fakewaterfall.CustomerView.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.FourComponents.MyContentProvider.MyContentProviderFragment;
import cn.com.hxx.fakewaterfall.Module.Http.MyHttpFragment;
import cn.com.hxx.fakewaterfall.MyViewPagerAdapter;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseActivity;

/**
 * Created by apple on 2018/7/31.
 */

public class MyTabLayoutActivity extends BaseActivity {

    ViewPager viewPager;
    MyTabLayout mytablayout;
    List<TabDate> tabDateList = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    public int setLayoutView() {
        return R.layout.activity_mytablayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        mytablayout = findViewById(R.id.mytablayout);
        tabDateList.add(new TabDate(R.drawable.indent, R.drawable.indent_un, "first"));
        tabDateList.add(new TabDate(R.drawable.invite, R.drawable.invite_un, "second"));
        tabDateList.add(new TabDate(R.drawable.indent, R.drawable.indent_un, "third"));
        tabDateList.add(new TabDate(R.drawable.invite, R.drawable.invite_un, "forth"));
        mytablayout.setTabDate(tabDateList);
    }

    private void initAdapter() {
        fragmentList.add(new MyHttpFragment());
        fragmentList.add(MyContentProviderFragment.getInstance());
        fragmentList.add(new MyHttpFragment());
        fragmentList.add(MyContentProviderFragment.getInstance());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myViewPagerAdapter);
        //设置每个fragment左右两边最多的缓存数量，这样，就不会反复重新加载fragment了
        viewPager.setOffscreenPageLimit(fragmentList.size() - 1);
        mytablayout.setViewPager(viewPager);
    }
}
