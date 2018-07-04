package cn.com.hxx.fakewaterfall.myView.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.uti.httputil.MyUtils;

/**
 * Created by apple on 2018/7/2.
 */

public class MyBanner extends FrameLayout implements ViewPager.OnPageChangeListener{

    private Context context;
    private List<String> imageList;
    private List<View> viewList;

    private int currentItem = 1;
    private boolean isAutoPlay;
    private Handler mHandler = new Handler();
    private int delaytime = 2000;
    private int count;
    private int drawableUnselected = R.drawable.indicator_bai;
    private int drawableSelected = R.drawable.indicator_hui;
    private int lastPosition = 1;

    private BannerViewPager viewPager;
    private MyBannerPagerAdapter myBannerPagerAdapter;
    private LinearLayout ll_indicator1;

    private List<ImageView> indicatorList;


    private BannerViewLoaderInterface bannerViewLoaderInterface;

    public MyBanner(@NonNull Context context) {
        super(context);
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initView();
    }


    private void initAttrs(AttributeSet attrs) {
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyBanner);
//
//        ta.recycle();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.mybanner_layout, this, true);       //将banner布局添加进framelayout
        viewPager = view.findViewById(R.id.vp_banner);
        ll_indicator1 = view.findViewById(R.id.ll_indicator1);
        viewList = new ArrayList<>();
        indicatorList = new ArrayList<>();
    }

    public void start(){
        currentItem = 0;
        loadImages();
        initAdapter();
   //     startAutoPlay();
    }

    private void loadImages() {
        //在原集合两端各增加一个
        for (int i = 0; i <= count + 1 ; i++){
            String url;
            if (i == 0) {
                url = imageList.get(count - 1);
            } else if (i == count + 1) {
                url = imageList.get(0);
            } else {
                url = imageList.get(i - 1);
            }
            View imageView = null;
            if (bannerViewLoaderInterface != null){
                imageView = bannerViewLoaderInterface.createView(getContext());
                bannerViewLoaderInterface.loadView(getContext(), imageView, url);
            }else {
                imageView = new ImageView(getContext());
                //默认使用Glide加载
                Glide.with(context).load(url).into((ImageView) imageView);
            }
            viewList.add(imageView);
        }
        initIndicator();
    }

    private void initAdapter() {
        if (myBannerPagerAdapter == null){
            myBannerPagerAdapter = new MyBannerPagerAdapter();
        }
        viewPager.setAdapter(myBannerPagerAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.setFocusable(true);
        viewPager.addOnPageChangeListener(this);
    }



    public MyBanner setImageUrl(List<String> imageList1){
        this.imageList = imageList1;
        count = imageList.size();
        return this;
    }

    private void initIndicator() {
        for (int i = 0; i < count ; i++){
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(context.getResources().getDrawable(i == 0 ? drawableSelected : drawableUnselected));
            ll_indicator1.addView(imageView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.setMargins(10,0,0,0);
            imageView.setLayoutParams(layoutParams);
            indicatorList.add(imageView);
        }
    }


    public void startAutoPlay(){
        mHandler.removeCallbacks(task);
        isAutoPlay = true;
        mHandler.postDelayed(task, delaytime);
    }

    public void pauseAutoPlay(){
        mHandler.removeCallbacks(task);
    }

    public int toRealPosition(int position) {
        int realPosition = (position - 1) % count;
        if (realPosition < 0)
            realPosition += count;
        return realPosition;
    }


    public Runnable task = new Runnable() {
        @Override
        public void run() {
            currentItem = currentItem % (count + 1) + 1;
            viewPager.setCurrentItem(currentItem);
            mHandler.postDelayed(task, delaytime);
        }
    };

    public class MyBannerPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = viewList.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtils.t(getContext(), position+"");
                }
            });
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    MyUtils.t(getContext(), "Long:"+position);
                    return false;
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int i = position;
    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        indicatorList.get((lastPosition - 1 + count) % count).setImageResource(drawableUnselected);
        indicatorList.get((position - 1 + count) % count).setImageResource(drawableSelected);
        lastPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //保证左右滑动到边界时依然可以滑动
        switch (state) {
            case 0://No operation
                if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                } else if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case 1://start Sliding
                if (currentItem == count + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(count, false);
                }
                break;
            case 2://end Sliding
                break;
        }
    }

    //用于控制轮播的暂停、开始
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pauseAutoPlay();
                break;
            case MotionEvent.ACTION_UP:
                startAutoPlay();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    public MyBanner setLoader(BannerViewLoaderInterface bannerViewLoaderInterface_){
        this.bannerViewLoaderInterface = bannerViewLoaderInterface_;
        return this;
    }
}
