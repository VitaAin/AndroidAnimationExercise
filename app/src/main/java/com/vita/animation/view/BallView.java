package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

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
    private Paint mRectPaint;
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

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.BLUE);
        mRectPaint.setStrokeWidth(2);
        mRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        setBackgroundColor(Color.LTGRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "BallView onDraw");
        if (mCurPoint == null) {
            mCurPoint = new Point(BALL_RADIUS, getWidth() / 2, BALL_RADIUS);
        }

        drawLine(canvas);
        drawCircle(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        triggerAnim();
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mBallPaint);
    }

    private void drawCircle(Canvas canvas) {
        float x = mCurPoint.getX();
        float y = mCurPoint.getY();
        Log.d(TAG, "drawCircle: " + mCurPoint.getWidth());
//        canvas.drawCircle(x, y, BALL_RADIUS, mBallPaint);
        RectF rectF = new RectF(x - mCurPoint.getWidth() / 2,
                y - mCurPoint.getHeight() / 2,
                x + mCurPoint.getWidth() / 2,
                y + mCurPoint.getHeight() / 2);
        canvas.drawOval(rectF, mRectPaint);
    }

    public void triggerAnim() {
        final int width = getWidth();
        int height = getHeight();
        if (mCurPoint == null) {
            mCurPoint = new Point(BALL_RADIUS, width / 2, BALL_RADIUS);
        }

        Point startP = new Point(BALL_RADIUS, width / 2, BALL_RADIUS);
        Point endP = new Point(BALL_RADIUS, width / 2, height - BALL_RADIUS);
        Log.i(TAG, "triggerAnim: startP: " + startP);
        Log.i(TAG, "triggerAnim: endP: " + endP);

        int duration = 1000;

        ObjectAnimator fallAnim = ObjectAnimator.ofFloat(mCurPoint,
                "y", startP.getY(), endP.getY());
        fallAnim.setDuration(duration);
        fallAnim.setInterpolator(new AccelerateInterpolator());
        fallAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurPoint.setX(width / 2);
//                mCurPoint.setY((float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });

        ObjectAnimator widthAnim = ObjectAnimator.ofFloat(mCurPoint,
                "width", mCurPoint.getWidth(), mCurPoint.getWidth() + BALL_RADIUS * 2 / 3);
        widthAnim.setDuration(duration / 4);
        widthAnim.setRepeatCount(1);
        widthAnim.setRepeatMode(ValueAnimator.REVERSE);
        widthAnim.setInterpolator(new DecelerateInterpolator());
        widthAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mCurPoint.setWidth((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });

        ObjectAnimator heightAnim = ObjectAnimator.ofFloat(mCurPoint,
                "height", mCurPoint.getHeight(), mCurPoint.getHeight() - BALL_RADIUS / 3);
        heightAnim.setDuration(duration / 4);
        heightAnim.setRepeatCount(1);
        heightAnim.setRepeatMode(ValueAnimator.REVERSE);
        heightAnim.setInterpolator(new DecelerateInterpolator());
        heightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mCurPoint.setHeight((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });

        ObjectAnimator bounceAnim = ObjectAnimator.ofFloat(mCurPoint,
                "y", endP.getY(), startP.getY());
        bounceAnim.setDuration(duration * 2);
        bounceAnim.setInterpolator(new DecelerateInterpolator());
        bounceAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                mCurPoint.setY((Float) valueAnimator.getAnimatedValue());
                postInvalidate();
            }
        });

        mAnimSet = new AnimatorSet();
//        mAnimSet.play(fallAnim).before(squashAnim);
//        mAnimSet.play(squashAnim).with(widthAnim);
        mAnimSet.play(fallAnim).before(widthAnim);
        mAnimSet.play(widthAnim).with(heightAnim);
        mAnimSet.play(bounceAnim).after(heightAnim);
//        mAnimSet.setDuration(duration);
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
