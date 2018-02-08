package com.vita.animation.interpolator;

import android.view.animation.Interpolator;

/**
 * @FileName: com.vita.animation.interpolator.HesitateInterpolator.java
 * @Author: Vita
 * @Date: 2018-02-08 14:13
 * @Usage:
 */
public class HesitateInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float t) {
        float x = 2.0f * t - 1.0f;
        return 0.5f * (x * x * x + 1.0f);
    }
}
