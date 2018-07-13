package cn.com.hxx.fakewaterfall.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 2018/7/13.
 */

public class CustomerViewGroup extends ViewGroup {
    public CustomerViewGroup(Context context) {
        super(context);
    }

    public CustomerViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = 0;
        int maxWidth = 0;
        int currentRowWidth = 0;
        int currentRowMaxHeight = 0;

        int measureSpecWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();    //可用宽度需要加入两边的padding

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View childAt = getChildAt(i);
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);
            maxWidth += childAt.getMeasuredWidth();
            currentRowWidth += childAt.getMeasuredWidth();
            if (currentRowWidth > measureSpecWidth){
                //此时需要换行
                maxHeight += currentRowMaxHeight;
                //换行
                currentRowWidth = childAt.getMeasuredWidth();
                currentRowMaxHeight = childAt.getMeasuredHeight();
            }else {
                //仍然在同一行
                currentRowMaxHeight = Math.max(currentRowMaxHeight, childAt.getMeasuredHeight());
            }
        }
        //加上最后一行和padingtop&padingbottom
        maxHeight += currentRowMaxHeight + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec) , resolveSize(maxHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //l,t,r,b是CustomerViewGroup在父布局中的位置
        //cursorLeft,scursorTop是第一个子view的左上顶点，
        int cursorLeft = getPaddingLeft();
        int cursorTop = getPaddingTop();
        int cursorRight = 0;
        int cursorBottom = 0;
        //
        int widthAll = r - l - getPaddingRight() - getPaddingLeft();  //可用宽度
        int widthTotal = 0; //每行的宽度,随着该行item的增加而增大
        int maxHeightInRow = 0; //每行最高的item
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++){
            View childAt = getChildAt(i);
            widthTotal += childAt.getMeasuredWidth();
            if (widthTotal > widthAll){
                //此时需要换行
                cursorLeft = getPaddingLeft();
                cursorTop += maxHeightInRow;
                cursorRight = cursorLeft + childAt.getMeasuredWidth();
                cursorBottom = cursorTop + childAt.getMeasuredHeight();
                childAt.layout(cursorLeft, cursorTop, cursorRight, cursorBottom);
                //点坐标、等参数重新初始化
                widthTotal = childAt.getMeasuredWidth();
                cursorLeft = childAt.getMeasuredWidth() + getPaddingLeft();
                maxHeightInRow = childAt.getMeasuredHeight();
            }else {
                cursorRight = cursorLeft + childAt.getMeasuredWidth();
                cursorBottom = cursorTop + childAt.getMeasuredHeight();
                childAt.layout(cursorLeft, cursorTop, cursorRight, cursorBottom);
                cursorLeft = cursorRight;
                maxHeightInRow = Math.max(maxHeightInRow, childAt.getMeasuredHeight());
            }
        }
    }
}
