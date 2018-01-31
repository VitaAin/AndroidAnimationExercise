package com.vita.animation.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @FileName: com.vita.animation.view.WaveView.java
 * @Author: Vita
 * @Date: 2018-01-31 09:13
 * @Usage:
 */
public class WaveView extends View {

    private static final String TAG = "WaveView";

    private int mWaveHeight = 120;
    private int mWaveRise = 0; // 涨幅
    private int mBasicLineHeight;
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int mOffset;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(0xff01a7fa);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mBasicLineHeight = mHeight / 2;

        startAnim();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        // 屏幕外的一周期正弦函数 start
        path.moveTo(-mWidth + mOffset, mBasicLineHeight);
        path.quadTo(-mWidth * 3 / 4 + mOffset, mBasicLineHeight - mWaveHeight,
                -mWidth / 2 + mOffset, mBasicLineHeight);
        path.quadTo(-mWidth / 4 + mOffset, mBasicLineHeight + mWaveHeight,
                0 + mOffset, mBasicLineHeight);
        // 屏幕外的一周期正弦函数 end
        // 一周期的正弦函数 start
        path.quadTo(mWidth / 4 + mOffset, mBasicLineHeight - mWaveHeight,
                mWidth / 2 + mOffset, mBasicLineHeight);
        path.quadTo(mWidth * 3 / 4 + mOffset, mBasicLineHeight + mWaveHeight,
                mWidth + mOffset, mBasicLineHeight);
        // 一周期的正弦函数 end
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        canvas.drawPath(path, mPaint);
        mBasicLineHeight -= mWaveRise;
    }

    private void startAnim() {
//        Log.d(TAG, "startAnim: " + mWidth);
        ValueAnimator anim = ValueAnimator.ofInt(0, mWidth);
        anim.setDuration(1500);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 当前平移的值
                mOffset = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        anim.start();
    }

    public int getWaveHeight() {
        return mWaveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        mWaveHeight = waveHeight;
    }

    public int getBasicLineHeight() {
        return mBasicLineHeight;
    }

    public void setBasicLineHeight(int basicLineHeight) {
        mBasicLineHeight = basicLineHeight;
    }
}
