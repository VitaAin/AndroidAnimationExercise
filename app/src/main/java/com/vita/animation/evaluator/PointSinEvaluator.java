package com.vita.animation.evaluator;

import android.animation.TypeEvaluator;
import android.util.Log;

import com.vita.animation.view.Point;

/**
 * @FileName: com.vita.animation.evaluator.PointSinEvaluator.java
 * @Author: Vita
 * @Date: 2018-01-26 09:15
 * @Usage:
 */
public class PointSinEvaluator implements TypeEvaluator {

    private static final String TAG = "PointSinEvaluator";

    /*
    fraction: 当前动画完成的百分比
    startValue: 动画的的初始值
    endValue: 动画的结束值
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
//        Log.d(TAG, "evaluate: fraction: " + fraction +
//                ", startValue: " + startValue + ", endValue: " + endValue);
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = (float) ((Math.sin(x * Math.PI / 180) * 100) + endPoint.getY() / 2);
        return new Point(x, y);
    }
}
