package cn.com.hxx.fakewaterfall.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by apple on 2018/6/25.
 */

public class MyView1 extends View {

    private Paint p = new Paint();


    public MyView1(Context context) {
        super(context);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL);
//        p.setColor(Color.argb(50,255,100,100));
//        canvas.drawRect(0,0,200,200,p); // 以原始Canvas画出一个矩形1
//        canvas.translate(300,300); // 将Canvas平移 (100,100)
//        p.setColor(Color.argb(50,100,255,100));
//        canvas.drawRect(0,0,200,200,p); // 矩形2
//        canvas.save();
//        canvas.rotate(30); //将Canvas旋转30
//        p.setColor(Color.argb(50,100,0,255));
//        canvas.drawRect(0,0,200,200,p); // 矩形3
//        canvas.scale(2, 2); // 将Canvas以原点为中心，放大两倍
//        p.setColor(Color.argb(50,255,255,0));
//        canvas.drawRect(0,0,200,200,p); // 矩形4
//        canvas.restore();
//        p.setColor(Color.BLUE);
//        canvas.drawRect(0,0,200,200,p); // 矩形2
//        p.setColor(Color.RED);
//        canvas.drawText("啦啦啦", 10, 100, p);
//        canvas.drawCircle(0, 0, 100, p);
//        canvas.drawLine(0,0,200,200, p);
//        Path path = new Path();
//        path.moveTo(50,0);
//        path.lineTo(100, 24);
//        path.lineTo(70, 100);
//        path.close();
//        p.setColor(Color.GREEN);
//        canvas.drawPath(path, p);
//        p.setColor(Color.YELLOW);
        Path path = new Path();
        path.addCircle(100, 100, 100, Path.Direction.CCW);
        path.addCircle(200, 100, 100, Path.Direction.CCW);
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(path, p);
//        path.lineTo(300, 300);
//        path.rLineTo(-100, 100);
//        canvas.drawPath(path,p);

    }
}
