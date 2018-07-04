package cn.com.hxx.fakewaterfall.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by apple on 2018/6/29.
 */

public class MyParentRelativeLayout extends RelativeLayout {
    public MyParentRelativeLayout(Context context) {
        super(context);
    }

    public MyParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


}
