package com.vita.animation.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vita.animation.evaluator.ThirdOrderBezierEvaluator;

/**
 * @FileName: com.vita.animation.view.FlowPointLoadingView.java
 * @Author: Vita
 * @Date: 2018-02-01 17:23
 * @Usage:
 */
public class FlowPointLoadingView extends View {

    private static final String TAG = "FlowPointLoadingView";
    private Paint mRefLinePaint;
    private int mWidth, mHeight;
    private float mCircleRadius = 20;
    private Paint mCirclePaint;

    public FlowPointLoadingView(Context context) {
        super(context);
        init();
    }

    public FlowPointLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlowPointLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0xffaaccff);

        mRefLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRefLinePaint.setColor(Color.BLUE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
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

        drawRefLine(canvas);
        drawFixedPoints(canvas);
        drawFlowPoint(canvas);
        drawPath(canvas);
    }

    private void drawRefLine(Canvas canvas) {
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mRefLinePaint);
    }

    private void drawFixedPoints(Canvas canvas) {
        for (int i = 1; i < 7; i++) {
            canvas.drawCircle(mWidth / 7 * i, mHeight / 2, mCircleRadius, mCirclePaint);
        }
    }

    private void drawFlowPoint(Canvas canvas) {
        canvas.drawCircle(mWidth / 7, mHeight / 2, mCircleRadius / 2, mCirclePaint);
    }

    private void drawPath(Canvas canvas) {

    }

    private PointF getCtrlPoint() {

        return null;
    }

    private PointF[] getFixedCutoffPoints(int position) { // position: 0-6
        float x = mWidth / 7 * (position + 1);
        PointF[] pointFs = new PointF[2];
        pointFs[0] = new PointF(x, mHeight / 2 - mCircleRadius);
        pointFs[1] = new PointF(x, mHeight / 2 + mCircleRadius);
        return pointFs;
    }

    private void startAnim() {
//        ValueAnimator.ofObject(new ThirdOrderBezierEvaluator())
    }
}
