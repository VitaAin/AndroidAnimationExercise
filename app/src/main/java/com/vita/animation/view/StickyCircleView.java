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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * @FileName: com.vita.animation.view.StickyCircleView.java
 * @Author: Vita
 * @Date: 2018-01-31 17:34
 * @Usage:
 */
public class StickyCircleView extends View {

    private static final String TAG = "StickyCircleView";

    private Paint mPaint;
    private float mCircleRadiusDefault = 20;
    private float mFixedCircleRadius = mCircleRadiusDefault;
    private float mDraggedCircleRadius = mCircleRadiusDefault;
    private PointF mFixedCircleCenter = new PointF(mCircleRadiusDefault, mCircleRadiusDefault); // 固定圆的圆心坐标
    private PointF mDraggedCircleCenter = new PointF(mCircleRadiusDefault, mCircleRadiusDefault); // 拖拽圆的圆心坐标
    private float mMaxDistance = 120; // 两圆心间最大距离
    private boolean isOutOfRange = false;
    private boolean isDisappear = false;

    public StickyCircleView(Context context) {
        super(context);
        init();
    }

    public StickyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isOutOfRange = false;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);

        setBackgroundColor(0xffaaccff);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mFixedCircleRadius = calcFixedCircleRadius();
        mDraggedCircleRadius = calcFixedCircleRadius();
        drawRefCircle(canvas);
        if (!isDisappear) {
            drawCircles(canvas);
            if (!isOutOfRange) {
                drawPath(canvas);
            }
        }
    }

    // just do a reference
    private void drawRefCircle(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mFixedCircleCenter.x, mFixedCircleCenter.y, mMaxDistance, paint);
    }

    private void drawCircles(Canvas canvas) {
        canvas.drawCircle(mFixedCircleCenter.x, mFixedCircleCenter.y, mFixedCircleRadius, mPaint);
        canvas.drawCircle(mDraggedCircleCenter.x, mDraggedCircleCenter.y, mDraggedCircleRadius, mPaint);
    }

    private void drawPath(Canvas canvas) {
        PointF[] fixedRefPoints = calcRefPoints(mFixedCircleCenter, mFixedCircleRadius);
        PointF[] draggedRefPoints = calcRefPoints(mDraggedCircleCenter, mDraggedCircleRadius);
        PointF fixedRefP1 = fixedRefPoints[0];
        PointF fixedRefP2 = fixedRefPoints[1];
        PointF draggedRefP1 = draggedRefPoints[0];
        PointF draggedRefP2 = draggedRefPoints[1];
        PointF ctrlPointF = calcCtrlPoint();

        Path path = new Path();
        path.moveTo(fixedRefP1.x, fixedRefP1.y);
        path.quadTo(ctrlPointF.x, ctrlPointF.y, draggedRefP1.x, draggedRefP1.y);
        path.lineTo(draggedRefP2.x, draggedRefP2.y);
        path.quadTo(ctrlPointF.x, ctrlPointF.y, fixedRefP2.x, fixedRefP2.y);
        path.lineTo(fixedRefP1.x, fixedRefP2.y);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downX, downY;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOutOfRange = false;
                isDisappear = false;
//                mFixedCircleRadius = mCircleRadiusDefault;
                downX = event.getX();
                downY = event.getY();
                updateDraggedCircleCenter(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                downY = event.getY();
                updateDraggedCircleCenter(downX, downY);
                if (calcDistance() > mMaxDistance) {
                    isOutOfRange = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                // 拖拽超出范围，两圆断开
                if (isOutOfRange) {
                    // 范围外松手后图标消失
                    if (calcDistance() > mMaxDistance) {
                        isDisappear = true;
                        invalidate();
                    } else { // 超出范围后又移回范围内，松开后还原
                        updateDraggedCircleCenter(mFixedCircleCenter.x, mFixedCircleCenter.y);
                    }
                } else { // 拖拽未超出范围
                    // 松手后还原
                    final PointF dragPoint = new PointF(mDraggedCircleCenter.x, mDraggedCircleCenter.y);
                    ValueAnimator anim = ValueAnimator.ofFloat(1);
                    anim.setInterpolator(new OvershootInterpolator(2));
                    anim.setDuration(500);
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float fraction = valueAnimator.getAnimatedFraction();
                            PointF point = calcPointByPercent(dragPoint, mFixedCircleCenter, fraction);
                            updateDraggedCircleCenter(point.x, point.y);
                        }
                    });
                    anim.start();
                }
            }
            break;
        }

        return true;
    }

    private void updateDraggedCircleCenter(float x, float y) {
        mDraggedCircleCenter.set(x, y);
        invalidate();
    }

    /**
     * 通过百分比计算拖拽点，用于恢复动画中拖拽圆圆心的更新
     *
     * @param start   起始点，即拖拽后松开的点
     * @param end     固定圆的圆心
     * @param percent 动画进行的百分比
     */
    private PointF calcPointByPercent(PointF start, PointF end, float percent) {
        float x = start.x + (end.x - start.x) * percent;
        float y = start.y + (end.y - start.y) * percent;

        return new PointF(x, y);
    }

    /**
     * 计算两圆圆心之间的直线距离
     */
    private float calcDistance() {
        float xDistance = mDraggedCircleCenter.x - mFixedCircleCenter.x;
        float yDistance = mDraggedCircleCenter.y - mFixedCircleCenter.y;
        // d = (x^2 + y^2)^(1/2)
        return (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    /**
     * 计算拖拽圆的半径
     */
    private float calcDraggedCircleRadius() {
        float distance = calcDistance();
        distance = Math.min(distance, mMaxDistance);

        // 比例
        float percent = distance / mMaxDistance;

        // change between (100%-20%)*radius
        float start = mCircleRadiusDefault;
        float end = (float) (0.4 * mCircleRadiusDefault);
        return start - percent * (end - start);
    }

    /**
     * 计算固定圆的半径
     */
    private float calcFixedCircleRadius() {
        float distance = calcDistance();
        distance = Math.min(distance, mMaxDistance);

        // 比例
        float percent = distance / mMaxDistance;

        // change between (100%-20%)*radius
        float start = mCircleRadiusDefault;
        float end = (float) (0.4 * mCircleRadiusDefault);
        return start + percent * (end - start);
    }

    /**
     * 计算圆上的两个参考点
     *
     * @param circleCenter 圆心坐标
     * @param radius       不动圆/拖拽圆的半径
     */
    private PointF[] calcRefPoints(PointF circleCenter, float radius) {
        float xDistance = mDraggedCircleCenter.x - mFixedCircleCenter.x;
        float yDistance = mDraggedCircleCenter.y - mFixedCircleCenter.y;
        PointF[] pointFs = new PointF[2];

        // 穿过两个圆的直线: y=kx+b, 平移它可以求得它分别与两个圆的四个切点，即staticRefP1&2、moveRefP1&2
        // xDistance==0 -> line: x=mFixedCircleCenter.x
        // yDistance==0 -> line: y=mFixedCircleCenter.y
        // k为斜率，a为角度
        if (xDistance != 0) {
            double k = yDistance / xDistance; // k = tan(a)
            double a = Math.atan(k);

            float xOffset = (float) (radius * Math.sin(a));
            float yOffset = (float) (radius * Math.cos(a));

            pointFs[0] = new PointF(circleCenter.x + xOffset, circleCenter.y - yOffset);
            pointFs[1] = new PointF(circleCenter.x - xOffset, circleCenter.y + yOffset);

            return pointFs;
        }

        pointFs[0] = new PointF(circleCenter.x - radius, circleCenter.y);
        pointFs[1] = new PointF(circleCenter.x + radius, circleCenter.y);
        return pointFs;
    }

    /**
     * 计算两个圆之间两条贝塞尔曲线的控制点
     * 这里取两圆心连线的中点
     */
    private PointF calcCtrlPoint() {
        float xDistance = mDraggedCircleCenter.x - mFixedCircleCenter.x;
        float yDistance = mDraggedCircleCenter.y - mFixedCircleCenter.y;

        return new PointF(mFixedCircleCenter.x + xDistance / 2, mFixedCircleCenter.y + yDistance / 2);
    }

    // do a test
    private void drawPathTest(Canvas canvas) {
        Path path = new Path();
        path.moveTo(700, 700);
        path.quadTo(600, 750, 500, 700);
        path.lineTo(500, 800);
        path.quadTo(600, 750, 700, 800);
        path.lineTo(700, 700);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
