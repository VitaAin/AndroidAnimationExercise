package com.vita.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @FileName: com.vita.animation.view.PathTextView.java
 * @Author: Vita
 * @Date: 2018-02-06 15:34
 * @Usage:
 */
public class PathTextView extends View {

    private static final String TAG = "PathTextView";

    private Paint mPathPaint, mTextPaint;
    private Path mPath1;
    private Path mPath2;
    private Path mPath3;

    public PathTextView(Context context) {
        super(context);
        init();
    }

    public PathTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.MAGENTA);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(56);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPath(canvas);
        drawText(canvas);
    }

    private void drawPath(Canvas canvas) {
        mPath1 = new Path();
        mPath1.moveTo(200, 200);
        mPath1.cubicTo(180, 360, 640, 270, 700, 700);
        canvas.drawPath(mPath1, mPathPaint);

        mPath2 = new Path();
        mPath2.moveTo(1000, 1000);
        mPath2.lineTo(900, 1200);
        mPath2.quadTo(400, 1000, 300, 600);
        canvas.drawPath(mPath2, mPathPaint);

        float radius = 100;
        // 当offSet为（圆形直径/3.6）时刚好是一个整圆
        float offset = radius * 2 / 3.6f;
        float xA = 300, yA = 1000,
                xB = 300 + radius, yB = 1000 + radius,
                xC = 300, yC = 1000 + 2 * radius,
                xD = 300 - radius, yD = 1000 + radius;
        mPath3 = new Path();
        mPath3.moveTo(xA, yA);
        mPath3.cubicTo(xA + offset, yA, xB, yB - offset, xB, yB);
        mPath3.cubicTo(xB, yB + offset, xC + offset, yC, xC, yC);
        mPath3.cubicTo(xC - offset, yC, xD, yD + offset, xD, yD);
        mPath3.cubicTo(xD, yD - offset, xA - offset, yA, xA, yA);
        canvas.drawPath(mPath3, mPathPaint);
    }

    private void drawText(Canvas canvas) {
        canvas.drawTextOnPath("Hello hello hello hello hello", mPath1, 0, 0, mTextPaint);
        canvas.drawTextOnPath("World world world world world", mPath2, 0, -20, mTextPaint);
        canvas.drawTextOnPath("I am a nice circle! ", mPath3, 30, 10, mTextPaint);
    }
}
