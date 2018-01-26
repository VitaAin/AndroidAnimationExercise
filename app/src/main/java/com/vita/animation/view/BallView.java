package com.vita.animation.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.vita.animation.evaluator.BallFallEvaluator;

/**
 * @FileName: com.vita.animation.view.BallView.java
 * @Author: Vita
 * @Date: 2018-01-26 16:35
 * @Usage:
 */
public class BallView extends View {

    private static final String TAG = "BallView";

    private static final float BALL_RADIUS = 40;

    private Point mCurPoint;
    private Paint mBallPaint;
    private AnimatorSet mAnimSet;

    public BallView(Context context) {
        super(context);
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "BallView init");
        mBallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBallPaint.setColor(Color.BLUE);
        setBackgroundColor(Color.LTGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "BallView onDraw");
        if (mCurPoint == null) {
            mCurPoint = new Point(getWidth() / 2, BALL_RADIUS);
        }

        drawCircle(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        triggerAnim();
    }

    private void drawCircle(Canvas canvas) {
        float x = mCurPoint.getX();
        float y = mCurPoint.getY();
        canvas.drawCircle(x, y, BALL_RADIUS, mBallPaint);
    }

    public void triggerAnim() {
        Point startP = new Point(getWidth() / 2, BALL_RADIUS);
        Point endP = new Point(getWidth() / 2, getHeight() - BALL_RADIUS);

//        float startY = mCurPoint.getY();
//        float endY = getHeight() - 2 * BALL_RADIUS;
//        float height = getHeight();
        int duration = 5000;

        ValueAnimator valueAnim = ValueAnimator.ofObject(new BallFallEvaluator(), startP, endP);
//        valueAnim.setRepeatCount(-1);
//        valueAnim.setRepeatMode(ValueAnimator.REVERSE);
        valueAnim.setDuration(duration);
        valueAnim.setInterpolator(new AccelerateInterpolator());
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurPoint = (Point) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });

        mAnimSet = new AnimatorSet();
        mAnimSet.play(valueAnim);
        mAnimSet.setDuration(duration);
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
}
