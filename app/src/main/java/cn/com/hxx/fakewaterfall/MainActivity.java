package cn.com.hxx.fakewaterfall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.myView.MyViewPagerAdapter;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyConstantUtils.init();
        initView();
    }

    private void initView() {
        fragmentList.add(ExpandFragment.getInstatnce());
        fragmentList.add(CustomerViewFragment.getInstatnce());
        fragmentList.add(MyBannerFragment.getInstance());
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager  = findViewById(R.id.viewPager);
        viewPager.setAdapter(myViewPagerAdapter);

    }
}
