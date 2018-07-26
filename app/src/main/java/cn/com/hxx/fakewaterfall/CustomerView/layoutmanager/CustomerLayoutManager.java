package cn.com.hxx.fakewaterfall.CustomerView.layoutmanager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * Created by apple on 2018/7/16.
 */

public class CustomerLayoutManager extends RecyclerView.LayoutManager {

    private Context context;
    private int verticalScrollOffset = 0;
    private int totalHeight = 0;
    private SparseArray<Rect> itemPositionSparseArray = new SparseArray<>();    //记录每个点点位置
    private SparseBooleanArray isShow = new SparseBooleanArray();   //true表示出现过屏幕中，且未被回收。false表示出现过屏幕中，但未被回收


    public CustomerLayoutManager(Context context_) {
        this.context = context_;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {    //参看RecyclerView源码，onLayoutChildren 会执行两次，一次RecyclerView的onMeasure() 一次onLayout()
        super.onLayoutChildren(recycler, state);
        totalHeight = 0;
        //如果没有item，直接返回
        if (getItemCount() <= 0) return;
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++){
            View viewForPosition = recycler.getViewForPosition(i);
            addView(viewForPosition);
            measureChildWithMargins(viewForPosition, 0, 0);
            int decoratedMeasuredWidth = getDecoratedMeasuredWidth(viewForPosition);
            int decoratedMeasuredHeight = getDecoratedMeasuredHeight(viewForPosition);
    //        layoutDecorated(viewForPosition, 0, offsetY, decoratedMeasuredWidth, offsetY + decoratedMeasuredHeight);
            //记录每个item的坐标位置
            Rect rect = itemPositionSparseArray.get(i);
            if (rect == null){
                rect = new Rect();
            }
            rect.set(0, offsetY, decoratedMeasuredWidth, offsetY + decoratedMeasuredHeight);
            itemPositionSparseArray.put(i, rect);

            offsetY += decoratedMeasuredHeight;
            totalHeight += decoratedMeasuredHeight;
        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());
        recycleAndFillitems(recycler, state);
    }

    private void recycleAndFillitems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()){
            return;
        }
        //获得当前显示区域
        Rect rect = new Rect(0, verticalScrollOffset, getHorizontalSpace(), verticalScrollOffset + getVerticalSpace() );
        /*
          将滑出屏幕的item收回
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++){
            View childAt = getChildAt(i);
            childFrame.left = getDecoratedLeft(childAt);
            childFrame.top = getDecoratedTop(childAt);
            childFrame.right = getDecoratedRight(childAt);
            childFrame.bottom = getDecoratedBottom(childAt);
            if (!Rect.intersects(childFrame, rect)){
                removeAndRecycleView(childAt, recycler);
            }
        }
        //重新显示
        for (int i = 0; i < getItemCount(); i++){
            if (Rect.intersects(rect, itemPositionSparseArray.get(i))){
                View viewForPosition = recycler.getViewForPosition(i);
                measureChild(viewForPosition, 0, 0);
                addView(viewForPosition);
                Rect rect1 = itemPositionSparseArray.get(i);
                layoutDecorated(viewForPosition, rect1.left, rect1.top - verticalScrollOffset, rect1.right, rect1.bottom - verticalScrollOffset);
            }
        }
    }

    /**
     *
     *
     * //两列形式
     if (i % 2 == 0){
     //左列
     layoutDecorated(viewForPosition, 0, offsetY, DensityUtils.getScreenWidth(context)/2, offsetY + decoratedMeasuredHeight);
     totalHeight += decoratedMeasuredHeight;

     }else {
     //右列
     layoutDecorated(viewForPosition, DensityUtils.getScreenWidth(context)/2, offsetY, DensityUtils.getScreenWidth(context), offsetY + decoratedMeasuredHeight);
     offsetY += decoratedMeasuredHeight;//准备换行
     }
     */

    @Override
    public boolean canScrollVertically() {
        return  true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {

        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy + getHeight()> totalHeight) {//如果滑动到最底部
            travel = 0;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 平移容器内的item
        offsetChildrenVertical(-travel);
        //随时检测item的显示与否
        recycleAndFillitems(recycler, state);
        Log.d("--->", " childView count:" + getChildCount());
        return travel;
    }
    //我们设置给recycleview的高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}
