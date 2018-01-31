package com.vita.animation.view;

import android.animation.FloatEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * @FileName: com.vita.animation.view.Jelly1View.java
 * @Author: Vita
 * @Date: 2018-01-31 16:06
 * @Usage:
 * @From: http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0318/7697.html
 */
public class Jelly1View extends View {

    private static final String TAG = "Jelly1View";

    private static final int CIRCLE_RADIUS = 80;
    private Paint mPaint;
    private float mFactor; // 形变系数（0~1）

    public Jelly1View(Context context) {
        super(context);
        init();
    }

    public Jelly1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Jelly1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff01a7fa);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
    }

    /**
     * 通过Bezier画圆
     */
    private void drawCircle(Canvas canvas) {
        float extra = CIRCLE_RADIUS * 2 * mFactor / 5;

        // A, B, C, D四个点分别为圆的上右下左四个点
        float xA, yA, xB, yB, xC, yC, xD, yD;
        xA = CIRCLE_RADIUS;
        xB = CIRCLE_RADIUS * 2 + extra;
        xC = CIRCLE_RADIUS;
        xD = 0;
        yA = extra;
        yB = CIRCLE_RADIUS;
        yC = CIRCLE_RADIUS * 2 - extra;
        yD = CIRCLE_RADIUS;

        // 当offSet为（圆形直径/3.6）时刚好是一个整圆
        float offset = CIRCLE_RADIUS * 2 / 3.6f;

        Path path = new Path();
        path.moveTo(xA, yA);
        // A -> B
        path.cubicTo(xA + offset, yA, xB, yB - offset, xB, yB);
        // B -> C
        path.cubicTo(xB, yB + offset, xC + offset, yC, xC, yC);
        // C -> D
        path.cubicTo(xC - offset, yC, xD, yD + offset, xD, yD);
        // D -> A
        path.cubicTo(xD, yD - offset, xA - offset, yA, xA, yA);
        canvas.drawPath(path, mPaint);
    }

    // 1 - (e^(-5*x)) * cos(30*x)
    private void startAnim() {
        ValueAnimator anim = ValueAnimator.ofObject(new FloatEvaluator(), 1, 0);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();

                mFactor = (float) (1 - Math.pow(Math.E, -5 * value) * Math.cos(30 * value));

                invalidate();
            }
        });
        anim.start();
    }
}
