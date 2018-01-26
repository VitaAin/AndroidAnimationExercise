package com.vita.animation.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.vita.animation.R;
import com.vita.animation.evaluator.PointSinEvaluator;

/**
 * @FileName: com.vita.animation.view.PointSinView.java
 * @Author: Vita
 * @Date: 2018-01-26 09:26
 * @Usage:
 */
public class PointSinView extends View {

    private static final String TAG = "PointSinView";

    private static final float RADIUS = 20f;

    private int color;
    private float radius = RADIUS;

    private TimeInterpolator interpolatorType = new AccelerateDecelerateInterpolator();

    private Point mCurPoint;
    private Paint mPaint;
    private Paint mLinePaint;
    private AnimatorSet mAnimSet;

    public PointSinView(Context context) {
        super(context);
        initView(context);
    }

    public PointSinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PointSinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        Log.d(TAG, "PointSinView init");
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurPoint == null) {
            mCurPoint = new Point(RADIUS, RADIUS);
        }
        drawCircle(canvas);
        drawLine(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(TAG, "PointSinView onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
        initAnim();
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(10, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint); // x轴
        canvas.drawLine(10, getHeight() / 2 - 200, 10, getHeight() / 2 + 200, mLinePaint); // y轴
        canvas.drawPoint(mCurPoint.getX(), mCurPoint.getY(), mLinePaint);
    }

    private void drawCircle(Canvas canvas) {
        float x = mCurPoint.getX();
        float y = mCurPoint.getY();
        canvas.drawCircle(x, y, radius, mPaint);
    }

    public void initAnim() {
        Point startP = new Point(RADIUS, RADIUS);
        Point endP = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator valueAnim = ValueAnimator.ofObject(new PointSinEvaluator(), startP, endP);
        valueAnim.setRepeatCount(-1);
        valueAnim.setRepeatMode(ValueAnimator.REVERSE);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurPoint = (Point) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });

        ObjectAnimator colorAnim = ObjectAnimator.ofObject(this, "color", new ArgbEvaluator(),
                Color.GREEN, Color.YELLOW, Color.BLUE, Color.CYAN, Color.RED);
        colorAnim.setRepeatCount(-1);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);

        ValueAnimator scaleAnim = ValueAnimator.ofFloat(20, 80, 60, 10, 35, 55, 10);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnim.setDuration(5000);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radius = (float) valueAnimator.getAnimatedValue();
            }
        });

        mAnimSet = new AnimatorSet();
        mAnimSet.play(valueAnim).with(colorAnim).with(scaleAnim);
        mAnimSet.setDuration(5000);
        mAnimSet.setInterpolator(interpolatorType);
        mAnimSet.start();
    }

    public void startAnim() {
        if (mAnimSet != null) {
            mAnimSet.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void pauseAnim() {
        if (mAnimSet != null) {
            mAnimSet.pause();
        }
    }

    public void stopAnim() {
        if (mAnimSet != null) {
            mAnimSet.cancel();
            clearAnimation();
        }
    }

    public void setInterpolatorType(int type) {
        switch (type) {
            case 1:
                interpolatorType = new BounceInterpolator();
                break;
            case 2:
                interpolatorType = new AccelerateDecelerateInterpolator();
                break;
            case 3:
                interpolatorType = new DecelerateInterpolator();
                break;
            case 4:
                interpolatorType = new AnticipateInterpolator();
                break;
            case 5:
                interpolatorType = new LinearInterpolator();
                break;
            case 6:
                interpolatorType = new LinearOutSlowInInterpolator();
                break;
            case 7:
                interpolatorType = new OvershootInterpolator();
                break;
            case 8:
                interpolatorType = new AnticipateOvershootInterpolator();
                break;
            default:
                interpolatorType = new AccelerateDecelerateInterpolator();
                break;
        }
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(this.color);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
