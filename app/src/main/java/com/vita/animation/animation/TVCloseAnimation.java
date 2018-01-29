package com.vita.animation.animation;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @FileName: com.vita.animation.animation.TVCloseAnimation.java
 * @Author: Vita
 * @Date: 2018-01-26 13:19
 * @Usage:
 */
public class TVCloseAnimation extends Animation {

    private int mCenterWidth, mCenterHeight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        // default duration
        setDuration(4000);
        // stay status when animation finished
        setFillAfter(true);

        mCenterWidth = width / 2;
        mCenterHeight = height / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        Matrix matrix = t.getMatrix();
        matrix.preScale(1, 1 - interpolatedTime, mCenterWidth, mCenterHeight);
    }
}
