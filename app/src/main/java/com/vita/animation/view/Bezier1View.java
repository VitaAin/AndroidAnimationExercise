package com.vita.animation.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;

/**
 * @FileName: com.vita.animation.view.Bezier1View.java
 * @Author: Vita
 * @Date: 2018-01-30 15:02
 * @Usage:
 */
public class Bezier1View extends FrameLayout {

    private static final int CIRCLE_RADIUS = 40;

    private Paint mPathPaint;
    private int mWidth;
    private int mHeight;

    public Bezier1View(Context context) {
        super(context);
        init();
    }

    public Bezier1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bezier1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        // 不绘制，仅移动画笔
        path.moveTo(CIRCLE_RADIUS, CIRCLE_RADIUS);
        // 绘制直线
        path.lineTo(300 + CIRCLE_RADIUS, 300 + CIRCLE_RADIUS);
        // 绘制圆滑曲线，即贝塞尔曲线
        // quadTo(x1, y1, x2, y2), (x1,y1)为控制点，(x2,y2)为结束点
        path.quadTo(50 + CIRCLE_RADIUS, 500 + CIRCLE_RADIUS,
                300 + CIRCLE_RADIUS, 700 + CIRCLE_RADIUS);
        // 绘制贝塞尔曲线
        // cubicTo(x1, y1, x2, y2, x3, y3), (x1,y1)为控制点，(x2,y2)为控制点，(x3,y3)为结束点
        path.cubicTo(600 + CIRCLE_RADIUS, 600 + CIRCLE_RADIUS,
                500 + CIRCLE_RADIUS, 250 + CIRCLE_RADIUS,
                50 + CIRCLE_RADIUS, 800 + CIRCLE_RADIUS);
        path.quadTo(500 + CIRCLE_RADIUS, CIRCLE_RADIUS,
                CIRCLE_RADIUS, CIRCLE_RADIUS);
        canvas.drawPath(path, mPathPaint);

        /*
        另外：arcTo 用于绘制弧线（实际是截取圆或椭圆的一部分）
        arcTo(ovalRectF, startAngle, sweepAngle),
        ovalRectF 为椭圆的矩形，startAngle 为开始角度，sweepAngle 为结束角度
        例：
        mRectF = new RectF(10, 10, 600, 600);
        mPath.arcTo(mRectF, 0, 90);
        canvas.drawPath(mPath, mPaint);
         */

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public View addCircle() {
        ImageView ivCircle = new ImageView(getContext());
        ivCircle.setImageResource(R.drawable.ic_lens_24dp);
        LayoutParams lp = new LayoutParams(CIRCLE_RADIUS * 2, CIRCLE_RADIUS * 2);
        ivCircle.setLayoutParams(lp);
        addView(ivCircle);

        Path circlePath = new Path();
        circlePath.moveTo(0, 0);
        circlePath.lineTo(300, 300);
        circlePath.quadTo(50, 500, 300, 700);
        circlePath.cubicTo(600, 600, 500, 250, 50, 800);
        circlePath.quadTo(500, 0, 0, 0);

        ObjectAnimator anim = ObjectAnimator.ofFloat(ivCircle, View.X, View.Y, circlePath);
        anim.setDuration(5000);
        anim.setStartDelay(300);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.start();

        return ivCircle;
    }

//    private void startAnim(){
//        ObjectAnimator anim = ObjectAnimator.ofObject(mPoint,"x")
//    }
}
