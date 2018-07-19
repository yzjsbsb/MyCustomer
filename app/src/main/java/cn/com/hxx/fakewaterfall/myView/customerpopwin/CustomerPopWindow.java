package cn.com.hxx.fakewaterfall.myView.customerpopwin;

import android.content.Context;
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
    private int resourceId;
    private PopupWindow mPopupWindow;

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
        View inflate = LayoutInflater.from(context).inflate(resourceId, null);
        mPopupWindow.setContentView(inflate);


        return mPopupWindow;
    }



    public static class Builder{

        private CustomerPopWindow customerPopWindow;

        public Builder(Context context){
            customerPopWindow = new CustomerPopWindow(context);
            PopupWindow popupWindow = new PopupWindow();
        }

        public Builder setSize(int winWidth, int winHeight){
            customerPopWindow.winWidth = winWidth;
            customerPopWindow.winHeight = winHeight;
            return this;
        }

        public Builder setContentView(int resourceId){
            customerPopWindow.resourceId = resourceId;
            return this;
        }

        public CustomerPopWindow create(){
            customerPopWindow.build();
            return customerPopWindow;
        }
    }
}
