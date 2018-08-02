package cn.com.hxx.fakewaterfall.CustomerView.tablayout;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/7/31.
 */

public class MyTabLayout extends FrameLayout implements ValueAnimator.AnimatorUpdateListener {

    private List<TabDate> tabDateList;
    private Context context;
    private DisplayMetrics dm;
    private ViewPager viewPager;
    private List<LinearLayout> childLayoutList = new ArrayList<>();
    private int color_selected = Color.BLACK;
    private int color_unselected = Color.GRAY;
    private int mUnderlineColor = Color.RED;
    private float mUnderlineHeight = 5;
    private float bottomLineIndicatorWidth = dp2px(20);

    private LinearLayout container;

    /** 用于绘制显示器 */
    private Rect mIndicatorRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();

    private Paint mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int defautItemSize; //icon的宽高

    private int currentItem;
    private int lastItem;

    /*indicator动画*/
    private ValueAnimator mValueAnimator;
    private OvershootInterpolator mInterpolator = new OvershootInterpolator(1.5f);

    private long mIndicatorAnimDuration = 350l;//动画持续时间

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
        setWillNotDraw(false);//onDraw会被调用
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        mValueAnimator = ValueAnimator.ofObject(new PointEvaluator(), mLastP, mCurrentP);
        mValueAnimator.addUpdateListener(this);
        addView(container);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        dm = context.getResources().getDisplayMetrics();
        defautItemSize = dm.widthPixels/10;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayout);
        defautItemSize = ta.getInteger(R.styleable.MyTabLayout_itemSize, defautItemSize);
        color_selected = ta.getColor(R.styleable.MyTabLayout_color_selected, color_selected);
        color_unselected = ta.getColor(R.styleable.MyTabLayout_color_unselected, color_unselected);
        bottomLineIndicatorWidth = ta.getDimension(R.styleable.MyTabLayout_bottomLineIndicatorWidth, bottomLineIndicatorWidth);
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
        int paddingLeft = getPaddingLeft();
        int height = getHeight();
        // draw underline
        mRectPaint.setColor(mUnderlineColor);
        int perWidthItem = container.getWidth()/tabDateList.size();
        bottomLineIndicatorWidth = Math.min(bottomLineIndicatorWidth, perWidthItem);
        float lineMarginLeftInItem = (perWidthItem - bottomLineIndicatorWidth)/2;
        float lineMarginLeft = currentItem * perWidthItem + lineMarginLeftInItem;
        canvas.drawRect(mIndicatorRect.left, height - mUnderlineHeight, mIndicatorRect.right, height, mRectPaint);
    }


    public void setTabDate(List<TabDate> tabDates){
        tabDateList = tabDates;
        currentItem = 0;
        for(int i = 0; i < tabDateList.size(); i++){
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(i == 0 ? tabDateList.get(i).getDra_selected() : tabDateList.get(i).getDra_un_selected());
            TextView textView = new TextView(getContext());
            textView.setTextColor(i == 0 ? color_selected : color_unselected);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(tabDateList.get(i).getTitle());
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.height = defautItemSize;
            layoutParams.width = defautItemSize;
            //addview之后会调用onMeasure
            container.addView(linearLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
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
        lastItem = currentItem;
        currentItem = index;
        for (int i = 0; i < childLayoutList.size(); i++){
            ImageView imageView = (ImageView) childLayoutList.get(i).getChildAt(0);
            TextView title = (TextView) childLayoutList.get(i).getChildAt(1);
            if (i == index){
                imageView.setImageResource(tabDateList.get(i).getDra_selected());
                title.setTextColor(color_selected);
            }else {
                imageView.setImageResource(tabDateList.get(i).getDra_un_selected());
                title.setTextColor(color_unselected);

            }
        }
        calcOffset();
    }


    private void calcOffset() {
        final View currentTabView = container.getChildAt(currentItem);
        mCurrentP.left = currentTabView.getLeft();
        mCurrentP.right = currentTabView.getRight();

        final View lastTabView = container.getChildAt(lastItem);
        mLastP.left = lastTabView.getLeft();
        mLastP.right = lastTabView.getRight();

//        Log.d("AAA", "mLastP--->" + mLastP.left + "&" + mLastP.right);
//        Log.d("AAA", "mCurrentP--->" + mCurrentP.left + "&" + mCurrentP.right);
        if (mLastP.left == mCurrentP.left && mLastP.right == mCurrentP.right) {
            invalidate();
        } else {
            mValueAnimator.setObjectValues(mLastP, mCurrentP);

            mValueAnimator.setInterpolator(mInterpolator);
            mValueAnimator.setDuration(mIndicatorAnimDuration);
            mValueAnimator.start();
        }
    }

    //当动画的属性更新时（不严谨的说，即每过 10 毫秒，动画的完成度更新时），这个方法被调用
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        View currentTabView = container.getChildAt(currentItem);
        IndicatorPoint p = (IndicatorPoint) animation.getAnimatedValue();
        mIndicatorRect.left = (int) p.left;
        mIndicatorRect.right = (int) p.right;

        float indicatorLeft = p.left + (currentTabView.getWidth() - bottomLineIndicatorWidth) / 2;

        mIndicatorRect.left = (int) indicatorLeft;
        mIndicatorRect.right = (int) (mIndicatorRect.left + bottomLineIndicatorWidth);
        invalidate();//根据动画完成情况实时绘制indicator的位置
    }


    private IndicatorPoint mCurrentP = new IndicatorPoint();
    private IndicatorPoint mLastP = new IndicatorPoint();

    //时间完成度与动画完成度的关系计算
    class PointEvaluator implements TypeEvaluator<IndicatorPoint> {
        @Override
        public IndicatorPoint evaluate(float fraction, IndicatorPoint startValue, IndicatorPoint endValue) {
            float left = startValue.left + fraction * (endValue.left - startValue.left);
            float right = startValue.right + fraction * (endValue.right - startValue.right);
            IndicatorPoint point = new IndicatorPoint();
            point.left = left;
            point.right = right;
            return point;
        }
    }

    class IndicatorPoint {
        public float left;
        public float right;
    }

    protected int dp2px(float dp) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
