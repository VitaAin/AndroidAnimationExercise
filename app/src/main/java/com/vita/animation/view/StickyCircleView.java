package com.vita.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @FileName: com.vita.animation.view.StickyCircleView.java
 * @Author: Vita
 * @Date: 2018-01-31 17:34
 * @Usage:
 */
public class StickyCircleView extends View {

    private Paint mPaint;
    private float[] mCircleCenter = {300, 300};
    private float mCircleRadius = 40;
    private float[] mMoveCircleCenter = {400, 400};
    private float mMoveCircleRadius = 20;

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
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
//        drawPathTest(canvas);
        drawPath(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCircleCenter[0], mCircleCenter[1], mCircleRadius, mPaint);
        canvas.drawCircle(mMoveCircleCenter[0], mMoveCircleCenter[1], mMoveCircleRadius, mPaint);
    }

    private void drawPath(Canvas canvas) {
        PointF[] staticPointFs = {new PointF(), new PointF()};
        PointF[] movePointFs = {new PointF(), new PointF()};
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
