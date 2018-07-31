package cn.com.hxx.fakewaterfall.CustomerView.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/7/31.
 */

public class MyTabLayout extends LinearLayout {

    private List<TabDate> tabDateList;
    private Context context;
    private DisplayMetrics dm;
    private ViewPager viewPager;
    private List<LinearLayout> childLayoutList = new ArrayList<>();

    private int defautItemSize; //icon的宽高

    public MyTabLayout(Context context) {
        super(context);
        this.context = context;
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public MyTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        dm = context.getResources().getDisplayMetrics();
        defautItemSize = dm.widthPixels/10;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayout);
        defautItemSize = ta.getInteger(R.styleable.MyTabLayout_itemSize, defautItemSize);

        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {



        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void setTabDate(List<TabDate> tabDates){
        tabDateList = tabDates;
        for(int i = 0; i < tabDateList.size(); i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(i == 0 ? tabDateList.get(i).getDra_selected() : tabDateList.get(i).getDra_un_selected());
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(tabDateList.get(i).getTitle());
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
            layoutParams.height = defautItemSize;
            layoutParams.width = defautItemSize;
            //addview之后会调用onMeasure
            addView(linearLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            final int index = i;
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(index);
                }
            });
            childLayoutList.add(linearLayout);
        }
    }

    public void setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeColor(int index){
//        ImageView imageView = (ImageView) childLayoutList.get(index).getChildAt(0);
//        imageView.setImageResource(tabDateList.get(index).getDra_selected());
//        ImageView imageViewLeft;
//        if (index - 1 >= 0){
//            //所选图标左边的图标变色
//            imageViewLeft = (ImageView) childLayoutList.get(index - 1).getChildAt(0);
//            imageViewLeft.setImageResource(tabDateList.get(index - 1).getDra_selected());
//        }else {
//            //最右侧图标变色
//            imageViewLeft = (ImageView) childLayoutList.get(childLayoutList.size() - 1).getChildAt(0);
//            imageViewLeft.setImageResource(tabDateList.get(childLayoutList.size() - 1).getDra_selected());
//        }
//
//        ImageView imageViewRight;
//        if (index + 1 > childLayoutList.size() - 1){
//
//            //最右侧图标变色
//            imageViewRight = (ImageView) childLayoutList.get(childLayoutList.size() - 1).getChildAt(0);
//            imageViewRight.setImageResource(tabDateList.get(childLayoutList.size() - 1).getDra_selected());
//        }else {
//            //左侧图标变色
//            imageViewRight = (ImageView) childLayoutList.get(index - 1).getChildAt(0);
//            imageViewRight.setImageResource(tabDateList.get(index - 1).getDra_selected());
//        }

        for (int i = 0; i < childLayoutList.size(); i++){
            ImageView imageView = (ImageView) childLayoutList.get(i).getChildAt(0);
            if (i == index){
                imageView.setImageResource(tabDateList.get(i).getDra_selected());
            }else {
                imageView.setImageResource(tabDateList.get(i).getDra_un_selected());
            }
        }
    }
}
