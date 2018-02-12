package com.vita.animation.pagetransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @FileName: com.vita.animation.transform.DepthPageTransformer.java
 * @Author: Vita
 * @Date: 2018-02-12 10:37
 * @Usage:
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {

    private float mMinScale = 0.6f;
    private float mMinAlpha = 0.6f;

    public DepthPageTransformer() {
    }

    public DepthPageTransformer(float minScale) {
        mMinScale = minScale;
    }

    public void transformPage(View view, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
            view.setAlpha(mMinAlpha);
        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            float scaleFactor = mMinScale + (1 - mMinScale) * (1 - Math.abs(position));
            float alphaFactor = mMinAlpha + (1 - mMinAlpha) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(alphaFactor);
        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            // Counteract the default slide transition

            // Scale the page down (between mMinScale and 1)
            float scaleFactor = mMinScale + (1 - mMinScale) * (1 - Math.abs(position));
            float alphaFactor = mMinAlpha + (1 - mMinAlpha) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(alphaFactor);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
            view.setAlpha(mMinAlpha);
        }
    }
}