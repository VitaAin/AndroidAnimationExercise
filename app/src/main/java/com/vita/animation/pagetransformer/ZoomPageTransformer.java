package com.vita.animation.pagetransformer;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * @FileName: com.vita.animation.pagetransformer.ZoomPageTransformer.java
 * @Author: Vita
 * @Date: 2018-02-12 10:52
 * @Usage:
 */
public class ZoomPageTransformer implements ViewPager.PageTransformer {

    private static final String TAG = "ZoomPageTransformer";

    @Override
    public void transformPage(View page, float position) {
//        Log.d(TAG, "transformPage: " + position);
        int width = page.getWidth();
        int height = page.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);
            page.setTranslationX(0);
        } else if (position <= 1) { // [-1,1]
            // Fade the page out.
            // Counteract the default slide transition
            page.setTranslationX(-width * position);
            page.setPivotX(width / 2);
            page.setPivotY(height / 2);
            page.setScaleX(1 - position);
            page.setScaleY(1 - position);
            page.setAlpha(1);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);
            page.setTranslationX(0);
        }
    }
}
