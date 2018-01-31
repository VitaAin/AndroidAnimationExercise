package com.vita.animation.evaluator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @FileName: com.vita.animation.evaluator.ThirdOrderBezierEvaluator.java
 * @Author: Vita
 * @Date: 2018-01-30 13:04
 * @Usage:
 */
public class SecondOrderBezierEvaluator implements TypeEvaluator<PointF> {

    private PointF pointF;

    public SecondOrderBezierEvaluator(PointF pointF) {
        this.pointF = pointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        float timeLeft = 1 - fraction;
        PointF point = new PointF();

        // 二阶贝塞尔曲线
        // B(t) = P0*(1-t)^2 + 2*P1*t*(1-t) + P2*t^2, 0<=t<=1
        point.x = (float) (startValue.x * Math.pow(timeLeft, 2)
                + 2 * pointF.x * fraction * timeLeft
                + endValue.x * Math.pow(fraction, 2));
        point.y = (float) (startValue.y * Math.pow(timeLeft, 2)
                + 2 * pointF.y * fraction * timeLeft
                + endValue.y * Math.pow(fraction, 2));

        return point;
    }
}
