package cn.com.hxx.fakewaterfall.myView.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by apple on 2018/7/3.
 */

public class BannerViewPager extends ViewPager {
    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
////        if (getChildCount() <= 1) {
////            super.onTouchEvent(ev);
////        }
////        switch (ev.getAction()) {
////            case MotionEvent.ACTION_DOWN:
////                if (getParent() != null) {
////                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
////                }
////                break;
////            case MotionEvent.ACTION_MOVE:
////                if (getParent() != null) {
////                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
////                }
////                break;
////            case MotionEvent.ACTION_CANCEL:
////            case MotionEvent.ACTION_UP:
////                if (getParent() != null) {
////                    getParent().requestDisallowInterceptTouchEvent(true);   //让事件不再分发
////                }
////                break;
////        }
////        super.onTouchEvent(ev);
////        return true;        //让事件不再分发
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        super.onTouchEvent(ev);
//        return true;
//    }
}
