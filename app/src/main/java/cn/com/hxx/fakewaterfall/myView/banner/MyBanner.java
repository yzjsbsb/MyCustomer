package cn.com.hxx.fakewaterfall.myView.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/7/2.
 */

public class MyBanner extends FrameLayout implements ViewPager.OnPageChangeListener{

    private Context context;
    private List<String> imageList;
    private List<View> viewList;
    private List<ImageView> indicatorList;
    private int lastPosition = 1;
    private int currentItem = 1;
    private boolean isAutoPlay = true;
    private Handler mHandler = new Handler();
    private int count;

    private int delaytime = 2000;
    private int drawableUnselected = R.drawable.indicator_bai;
    private int drawableSelected = R.drawable.indicator_hui;
    private DisplayMetrics dm;
    private int indicatorSize;
    private int scrollDuration = 300;
    private int indicator_style = 0;    //0. 图案类型  1.数字类型eg:1/5

    private BannerViewPager viewPager;
    private MyBannerPagerAdapter myBannerPagerAdapter;
    private LinearLayout ll_indicator1;
    private LinearLayout ll_indicator2;
    private TextView tv_indicator;

    private BannerClickListenner bannerClickListenner;
    private BannerLongClickListenner bannerLongClickListenner;


    private BannerViewLoaderInterface bannerViewLoaderInterface;

    public MyBanner(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public MyBanner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
        initView();
    }


    private void initAttrs(AttributeSet attrs) {
        dm = context.getResources().getDisplayMetrics();
        indicatorSize = dm.widthPixels/80;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyBanner);
        drawableSelected = ta.getResourceId(R.styleable.MyBanner_selected_icon, R.drawable.indicator_hui);
        drawableUnselected = ta.getResourceId(R.styleable.MyBanner_unselected_icon, R.drawable.indicator_bai);
        indicatorSize = ta.getDimensionPixelSize(R.styleable.MyBanner_indicatorSize, indicatorSize);
        scrollDuration = ta.getInteger(R.styleable.MyBanner_scroll_duration, scrollDuration);
        indicator_style = ta.getInt(R.styleable.MyBanner_indicator_style, indicator_style);
        ta.recycle();
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.mybanner_layout, this, true);       //将banner布局添加进framelayout
        viewPager = view.findViewById(R.id.vp_banner);
        ll_indicator1 = view.findViewById(R.id.ll_indicator1);
        ll_indicator2 = view.findViewById(R.id.ll_indicator2);
        tv_indicator = view.findViewById(R.id.tv_indicator);
        viewList = new ArrayList<>();
        indicatorList = new ArrayList<>();
        changeScrollSpeed();
    }

    private void changeScrollSpeed() {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            BannerScroller scroller = new BannerScroller(context,
                    new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setScrollDuration(scrollDuration);
        } catch (Exception e) {

        }
    }

    public void start(){
        currentItem = 1;
        loadImages();
        initAdapter();
        if (isAutoPlay){
            startAutoPlay();
        }
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

        switch (indicator_style){
            case 0:
                for (int i = 0; i < count ; i++){
                    ll_indicator1.setVisibility(VISIBLE);
                    ImageView imageView = new ImageView(context);
                    imageView.setImageDrawable(context.getResources().getDrawable(i == 0 ? drawableSelected : drawableUnselected));
                    ll_indicator1.addView(imageView);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                    layoutParams.setMargins(0,0,10,0);
                    layoutParams.width = indicatorSize;
                    imageView.setLayoutParams(layoutParams);
                    indicatorList.add(imageView);
                }
                break;
            case 1:
                ll_indicator2.setVisibility(VISIBLE);
                break;
        }
    }


    public void startAutoPlay(){
        mHandler.removeCallbacks(task);
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
                    if (bannerClickListenner!= null){
                        bannerClickListenner.OnBannerItemClick(v, position);
                    }
                }
            });
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (bannerLongClickListenner != null){
                        bannerLongClickListenner.OnBanneItemLongClick(v, position);
                    }
                    return true;    //返回true代表事件被消耗，false则会造成onClick的回调
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
        switch (indicator_style){
            case 0:
                indicatorList.get((lastPosition - 1 + count) % count).setImageResource(drawableUnselected);
                indicatorList.get((position - 1 + count) % count).setImageResource(drawableSelected);
                break;
            case 1:
                tv_indicator.setText((((position - 1 + count) % count)+1)+"/"+(count));
                break;
        }
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
        if (isAutoPlay){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    pauseAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                    startAutoPlay();
                    break;
            }
        }

        return super.dispatchTouchEvent(event);
    }

    public MyBanner setLoader(BannerViewLoaderInterface bannerViewLoaderInterface_){
        this.bannerViewLoaderInterface = bannerViewLoaderInterface_;
        return this;
    }

    public MyBanner setAutoPlay(boolean isAutoPlay_){
        this.isAutoPlay = isAutoPlay_;
        return this;
    }

    public interface BannerClickListenner{
        void OnBannerItemClick(View view, int position);
    }

    public void addOnBannerClickListenner(BannerClickListenner bannerClickListenner_){
        this.bannerClickListenner = bannerClickListenner_;
    }

    public interface BannerLongClickListenner{
        void OnBanneItemLongClick(View view, int position);
    }

    public void addOnBannerLongClickListenner(BannerLongClickListenner bannerLongClickListenner_){
        this.bannerLongClickListenner = bannerLongClickListenner_;
    }
}
