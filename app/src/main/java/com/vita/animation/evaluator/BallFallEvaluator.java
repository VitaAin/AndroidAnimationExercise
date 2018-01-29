package com.vita.animation.evaluator;

import android.animation.TypeEvaluator;
import android.util.Log;

import com.vita.animation.view.Point;

/**
 * @FileName: com.vita.animation.evaluator.BallFallEvaluator.java
 * @Author: Vita
 * @Date: 2018-01-26 16:42
 * @Usage:
 */
public class BallFallEvaluator implements TypeEvaluator {

    private static final String TAG = "BallFallEvaluator";

    private long duration;

    public BallFallEvaluator(long duration) {
        this.duration = duration / 1000;
    }

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Log.d(TAG, "evaluate: fraction: " + fraction +
                ", startValue: " + startValue + ", endValue: " + endValue);
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction;
        float y = startPoint.getY() + fraction * fraction * 100 * duration * duration / 2;
//        float y = startPoint.getY() + fraction * 300;
        Log.d(TAG, "evaluate: x: " + x + ", y: " + y);
        return new Point(x, y);
    }
}
