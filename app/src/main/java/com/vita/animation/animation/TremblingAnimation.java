package com.vita.animation.animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @FileName: com.vita.animation.animation.TremblingAnimation.java
 * @Author: Vita
 * @Date: 2018-01-26 11:21
 * @Usage:
 */
public class TremblingAnimation extends Animation {

    /*
    interpolatedTime: 当前动画进行的时间与动画总时间（Duration）的比值，从0逐渐增大到1
    t: 传递当前动画对象，一般可以通过代码 android.graphics.Matrix matrix = t.getMatrix() 获得 Matrix 矩阵对象，再设置 Matrix 对象
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        // 50越大频率越高，8越小振幅越小
        t.getMatrix().setTranslate((float) (Math.sin(interpolatedTime * 50) * 8),
                (float) (Math.sin(interpolatedTime * 50) * 8));
        super.applyTransformation(interpolatedTime, t);
    }
}
