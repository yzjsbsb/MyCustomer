package cn.com.hxx.fakewaterfall.myView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import cn.com.hxx.fakewaterfall.R;

/**
 * Created by apple on 2018/6/29.
 */

public class ButtonCircleProgressBar extends ProgressBar {

    private int progress_unreached_color;
    private int progress_reached_color;
    private int progress_reached_bar_height;
    private int progress_unreached_bar_height;
    private int radius = dp2px(18);
    private int triangleLength;
    private int exactlyWidth;
    private int exactlyHeight;
    public Status mStatus = Status.End;



    private Paint paint = new Paint();
    private Paint tanglePaint = new Paint();
    private Path path = new Path();

    private Context context;

    public ButtonCircleProgressBar(Context context) {
        super(context);
    }

    public ButtonCircleProgressBar(Context context, AttributeSet attrs) {
     //   super(context, attrs); 造成getprogress始终为0
        this(context, attrs, 0);
        this.context = context;
        initAttrs(attrs);

    }

    public ButtonCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    <!--无进度时的颜色-->
        <attr name="progress_unreached_color" format="color" />
        <!--进度颜色-->
        <attr name="progress_reached_color" format="color" />
        <!--进度条的高-->
        <attr name="progress_reached_bar_height" format="dimension" />
        <!--无进度时的边框高-->
        <attr name="progress_unreached_bar_height" format="dimension" />
        <!--圆的半径-->
        <attr name="radius" format="dimension" />
     */

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ButtonCircleProgressBar);
        progress_unreached_color = ta.getColor(R.styleable.ButtonCircleProgressBar_progress_unreached_color, getResources().getColor(R.color.about_follow));
        progress_reached_color = ta.getColor(R.styleable.ButtonCircleProgressBar_progress_reached_color, getResources().getColor(R.color.buy_give_color));
        progress_reached_bar_height = (int)ta.getDimension(R.styleable.ButtonCircleProgressBar_progress_reached_bar_height, 4);
        progress_unreached_bar_height = (int)ta.getDimension(R.styleable.ButtonCircleProgressBar_progress_unreached_bar_height, 4);
        radius = (int)ta.getDimension(R.styleable.ButtonCircleProgressBar_radius, radius);
        ta.recycle();
        triangleLength = radius;

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        //用于构造三角形
        float x = (float) (2 * radius - Math.sqrt(3) * triangleLength/2)/2;
        x = 1.2f * x;
        float y = radius - triangleLength/2;
        //第一个点
        path.moveTo(x, y);
        //第二个点
        path.lineTo(x, y + triangleLength);
        //第三个点
        path.lineTo(x + (float) (Math.sqrt(3) * triangleLength/2), y + triangleLength/2);
//        float leftX = (float) ((2*radius-Math.sqrt(3.0)/2*triangleLength)/2);
//        float realX = (float) (leftX+leftX*0.2);
//        path.moveTo(realX,radius-(triangleLength/2));
//        path.lineTo(realX,radius+(triangleLength/2));
//        path.lineTo((float) (realX+Math.sqrt(3.0)/2*triangleLength),radius);
//        path.lineTo(realX,radius-(triangleLength/2));

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paintWidth = Math.max(progress_reached_bar_height, progress_unreached_bar_height);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        if (heightMode != MeasureSpec.EXACTLY){
//            exactlyHeight = getPaddingBottom() + getPaddingTop() + radius * 2 + paintWidth;
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exactlyHeight, MeasureSpec.EXACTLY) + 10;
//        }
//        if (widthMode != MeasureSpec.EXACTLY){
//            exactlyWidth = getPaddingLeft() + getPaddingRight() + radius * 2 + paintWidth;
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exactlyWidth, MeasureSpec.EXACTLY) + 10;
//        }
        exactlyHeight = getPaddingBottom() + getPaddingTop() + radius * 2 + paintWidth;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(exactlyHeight, MeasureSpec.EXACTLY);
        exactlyWidth = getPaddingLeft() + getPaddingRight() + radius * 2 + paintWidth;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(exactlyWidth, MeasureSpec.EXACTLY);
        exactlyHeight = resolveSize(exactlyHeight, heightMeasureSpec);      //resolveSize返回符合父View限制的修正之后的尺寸
        exactlyWidth = resolveSize(exactlyWidth, widthMeasureSpec);

    //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(exactlyWidth, exactlyHeight);      //保存尺寸
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //先画底层的圆
        canvas.translate(getPaddingLeft(), getPaddingTop());
        paint.setColor(progress_unreached_color);
        paint.setStrokeWidth(progress_unreached_bar_height);
        canvas.drawCircle(radius, radius, radius, paint);

        //再画上层的圆
        paint.setColor(progress_reached_color);
        paint.setStrokeWidth(progress_reached_bar_height);
        float sweepAngle = (getProgress() * 1.0f / getMax()) * 360;
        canvas.drawArc(new RectF(0,0, radius *2, radius * 2), 0, sweepAngle, false, paint);

    //    canvas.restore();
        //画三角形
        tanglePaint.setStyle(Paint.Style.FILL);
        tanglePaint.setColor(progress_reached_color);
        tanglePaint.setAntiAlias(true);
        if (mStatus == Status.End){
            canvas.drawPath(path, tanglePaint);
        }else if (mStatus == Status.Starting){
            canvas.drawLine(radius*2/3,radius*2/3,radius*2/3,2*radius*2/3,tanglePaint);
            canvas.drawLine(2*radius-(radius*2/3),radius*2/3,2*radius-(radius*2/3),2*radius*2/3,tanglePaint);
        }
        canvas.restore();
        //画暂停
    }

    public enum Status{
        End,
        Starting
    }

    protected int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public void setStatus(Status status){
        mStatus = status;
        invalidate();
    }

    public Status getStatus(){
        return mStatus;
    }
}
