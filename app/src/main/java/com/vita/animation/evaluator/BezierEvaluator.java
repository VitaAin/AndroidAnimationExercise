package com.vita.animation.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @FileName: com.vita.animation.evaluator.BezierEvaluator.java
 * @Author: Vita
 * @Date: 2018-01-30 13:04
 * @Usage:
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF1, pointF2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float timeLeft = 1 - fraction;
        PointF point = new PointF();

        // B(t) = P0*(1-t)^3 + 3*P1*t*(1-t)^2 + 3*P2*t^2*(1-t) + P3*t^3
        point.x = (float) (startValue.x * Math.pow(timeLeft, 3)
                + 3 * pointF1.x * fraction * Math.pow(timeLeft, 2)
                + 3 * pointF2.x * Math.pow(fraction, 2) * timeLeft
                + endValue.x * Math.pow(fraction, 3));
        point.y = (float) (startValue.y * Math.pow(timeLeft, 3)
                + 3 * pointF1.y * fraction * Math.pow(timeLeft, 2)
                + 3 * pointF2.y * Math.pow(fraction, 2) * timeLeft
                + endValue.y * Math.pow(fraction, 3));

        return point;
    }
}
