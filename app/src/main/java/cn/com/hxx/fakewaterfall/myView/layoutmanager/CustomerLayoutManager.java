package cn.com.hxx.fakewaterfall.myView.layoutmanager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

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
            layoutDecorated(viewForPosition, 0, offsetY, decoratedMeasuredWidth, offsetY + decoratedMeasuredHeight);
            offsetY += decoratedMeasuredHeight;
            totalHeight += decoratedMeasuredHeight;
        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());
    }

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

        return travel;
    }
    //我们设置给recycleview的高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}
