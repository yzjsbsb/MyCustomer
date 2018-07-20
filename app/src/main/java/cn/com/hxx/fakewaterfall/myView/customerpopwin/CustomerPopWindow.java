package cn.com.hxx.fakewaterfall.myView.customerpopwin;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by apple on 2018/7/19.
 */

public class CustomerPopWindow {

    private int winWidth;
    private int winHeight;
    private View contentView;
    private PopupWindow mPopupWindow;
    private boolean focusable;
    private boolean touchable;
    private boolean outSidetouchable;
    private Drawable background;
    private int animation;

    private Context context;

    public CustomerPopWindow(Context context_) {
        this.context = context_;
    }

    public void showAsDropDown(View anchor){
        mPopupWindow.showAsDropDown(anchor);
    }

    public void showAsDropDown(View anchor, int offx, int offy){
        mPopupWindow.showAsDropDown(anchor, offx, offy);
    }

//    public void showAsDropDown(View anchor, int offx, int offy, int gravity){
//        mPopupWindow.showAsDropDown(anchor, offx, offy, gravity);
//    }
    public void showAtLocation(View parent, int gravity, int offx, int offy){
        mPopupWindow.showAtLocation(parent, gravity,offx, offy);
    }

    public void dismiss(){
        mPopupWindow.dismiss();
    }

    public PopupWindow build(){
        //创建popwin并初始化各种输入的属性
        if (mPopupWindow == null){
            mPopupWindow = new PopupWindow();
        }
        //设置宽高
        mPopupWindow.setHeight(winHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : winHeight);
        mPopupWindow.setWidth(winWidth == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : winWidth);
        //设置View
        mPopupWindow.setContentView(contentView);

        setAble();
        //设置背景
        if (background != null){
            mPopupWindow.setBackgroundDrawable(background);
        }
        //设置动画
        if (animation != 0){
            mPopupWindow.setAnimationStyle(animation);
        }
        return mPopupWindow;
    }

    private void setAble() {
        mPopupWindow.setFocusable(focusable);
        mPopupWindow.setTouchable(touchable);
        mPopupWindow.setOutsideTouchable(outSidetouchable);
    }


    public static class Builder{

        private CustomerPopWindow customerPopWindow;

        public Builder(Context context){
            customerPopWindow = new CustomerPopWindow(context);
        }

        public Builder setSize(int winWidth, int winHeight){
            customerPopWindow.winWidth = winWidth;
            customerPopWindow.winHeight = winHeight;
            return this;
        }

        public Builder setContentView(View contentView){
            customerPopWindow.contentView = contentView;
            return this;
        }

        public Builder setFocusable(boolean focusable){
            customerPopWindow.focusable = focusable;
            return this;
        }

        public Builder setTouchable(boolean touchable){
            customerPopWindow.touchable = touchable;
            return this;
        }

        public Builder setOutsideTouchable(boolean outSidetouchable){
            customerPopWindow.outSidetouchable = outSidetouchable;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable background){
            customerPopWindow.background = background;
            return this;
        }

        public Builder setAnimationStyle(int animation){
            customerPopWindow.animation = animation;
            return this;
        }

        public CustomerPopWindow create(){
            customerPopWindow.build();
            return customerPopWindow;
        }
    }
}
