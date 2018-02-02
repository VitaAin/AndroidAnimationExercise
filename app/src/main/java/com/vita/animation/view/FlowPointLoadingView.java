package com.vita.animation.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @FileName: com.vita.animation.view.FlowPointLoadingView.java
 * @Author: Vita
 * @Date: 2018-02-01 17:23
 * @Usage:
 */
public class FlowPointLoadingView extends View {

    private static final String TAG = "FlowPointLoadingView";
    private Paint mRefLinePaint;
    private Paint mCirclePaint;
    private int mWidth, mHeight;
    private float mCircleRadius = 20;
    private float mFlowPointRadius = mCircleRadius / 2;
    private float mMixedCircleRadius = mCircleRadius + mFlowPointRadius;
    private PointF mFlowPoint;
    private ValueAnimator mAnim;
    private int mCurPos;
    private Path mPath;

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

        updateCurPos();

        if (mAnim == null || !mAnim.isRunning()) {
            startAnim();
        }
    }

    private void updateCurPos() {
        for (int i = 0; i < 6; i++) {
            if (Math.abs(mFlowPoint.x - mWidth / 7 * (i + 1)) < mWidth / 7 / 5 * 2) {
                mCurPos = i + 1;
                return;
            }
        }
        mCurPos = 0;
    }

    private void drawRefLine(Canvas canvas) {
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mRefLinePaint);
    }

    private void drawFixedPoints(Canvas canvas) {
        for (int i = 1; i < 7; i++) {
            if (i == mCurPos) {
                canvas.drawCircle(mWidth / 7 * i, mHeight / 2, mMixedCircleRadius, mCirclePaint);
            } else {
                canvas.drawCircle(mWidth / 7 * i, mHeight / 2, mCircleRadius, mCirclePaint);
            }
        }
    }

    private void drawFlowPoint(Canvas canvas) {
        if (mFlowPoint == null) {
            mFlowPoint = new PointF(mWidth / 7, mHeight / 2);
        }
        canvas.drawCircle(mFlowPoint.x, mFlowPoint.y, mCircleRadius / 2, mCirclePaint);
    }

    private void drawPath(Canvas canvas) {
        if (mCurPos == 0) {
            return;
        }

        if (mPath == null) {
            mPath = new Path();
        }
        mPath.reset();

        PointF ctrlPoint = getCtrlPoint();
        PointF[] fixedCutoffPoints0 = getFixedCutoffPoints();

        mPath.moveTo(fixedCutoffPoints0[0].x, fixedCutoffPoints0[0].y);
        mPath.quadTo(ctrlPoint.x, ctrlPoint.y, mFlowPoint.x, mFlowPoint.y - mFlowPointRadius);
        mPath.lineTo(mFlowPoint.x, mFlowPoint.y + mFlowPointRadius);
        mPath.quadTo(ctrlPoint.x, ctrlPoint.y, fixedCutoffPoints0[1].x, fixedCutoffPoints0[1].y);
        mPath.lineTo(fixedCutoffPoints0[0].x, fixedCutoffPoints0[0].y);
        mPath.close();
        canvas.drawPath(mPath, mCirclePaint);
    }

    private PointF getCtrlPoint() {
        PointF fixedCircleCenter = new PointF(mWidth / 7 * mCurPos, mHeight / 2);
        float x = (fixedCircleCenter.x + mFlowPoint.x) / 2;
        float y = mHeight / 2;
        return new PointF(x, y);
    }

    private PointF[] getFixedCutoffPoints() {
        float x = mWidth / 7 * (mCurPos);
        PointF[] pointFs = new PointF[2];
        pointFs[0] = new PointF(x, mHeight / 2 - mCircleRadius);
        pointFs[1] = new PointF(x, mHeight / 2 + mCircleRadius);
        return pointFs;
    }

    private void startAnim() {
        mAnim = ValueAnimator.ofFloat(mFlowPoint.x - mFlowPointRadius * 4,
                mWidth / 7 * 6 + mFlowPointRadius * 4);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.setRepeatMode(ValueAnimator.REVERSE);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                mFlowPoint.x = value;
                invalidate();
            }
        });
        mAnim.start();
    }
}
