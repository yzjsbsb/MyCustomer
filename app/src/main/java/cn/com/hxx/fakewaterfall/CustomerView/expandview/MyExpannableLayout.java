package cn.com.hxx.fakewaterfall.CustomerView.expandview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/6/26.
 */

public class MyExpannableLayout extends LinearLayout {

    private Context context;
    private int index;
    private String stringtoshow;
    private String stringtohide;
    private boolean isShow = false;
    private TextView tv_bottom;

    public MyExpannableLayout(Context context) {
        super(context);
    }

    public MyExpannableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        setOrientation(VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyExpannableLayout);
        index = typedArray.getInt(R.styleable.MyExpannableLayout_num2show, 1);
        stringtohide = typedArray.getString(R.styleable.MyExpannableLayout_tohideWord);
        stringtoshow = typedArray.getString(R.styleable.MyExpannableLayout_toshowWord);
        typedArray.recycle();
    }

    public MyExpannableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }


    public void setView(View view){
        int childCount = getChildCount();
        if (childCount < index){
            addView(view);
        }else if (childCount == index){
            initBottom();
            addView(view, getChildCount() - 1);
        }else {
            addView(view, getChildCount() - 1);
        }
        initExpandedItem();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initExpandedItem();
    }

    private void initExpandedItem() {
        if (isShow){
            expand();
        }else {
            hide();
        }
    }

    private void hide() {
        int childCount = getChildCount();
        for (int i = 0 ; i < childCount  ; i ++){
            if (i <= index - 1){
                getChildAt(i).setVisibility(VISIBLE);
            }else if (i > index - 1 && i < childCount - 1){
                getChildAt(i).setVisibility(GONE);
            }else if (i == childCount - 1 ){
                getChildAt(i).setVisibility(VISIBLE);
            }
        }
    }

    private void expand() {
        int childCount = getChildCount();
        for (int i = 0 ; i < childCount  ; i ++){
            getChildAt(i).setVisibility(VISIBLE);
        }
    }

    private void initBottom() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = inflater.inflate(R.layout.bottom, null, false);
        tv_bottom = inflate.findViewById(R.id.tv_bottom);
        tv_bottom.setText(stringtoshow);
        inflate.findViewById(R.id.tv_bottom).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        addView(inflate);

    }

    private void toggle(){
        tv_bottom.setText(isShow ? stringtoshow : stringtohide);
        isShow = !isShow;
        initExpandedItem();
    }

    public interface ItemClickListner{
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(final ItemClickListner listener){
        int childCount = getChildCount();
        for (int i = 0; i < childCount - 1; i++){
            final View childAt = getChildAt(i);
            final int position = i;
            childAt.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(childAt, position);
                }
            });
        }
    }


    @Override
    public void setOrientation(int orientation) {
        if (orientation == HORIZONTAL){
            throw new IllegalArgumentException("orientation can not be horizontal");
        }
        super.setOrientation(VERTICAL);
    }
}
