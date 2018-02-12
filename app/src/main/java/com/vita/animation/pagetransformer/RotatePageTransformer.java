package com.vita.animation.pagetransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @FileName: com.vita.animation.pagetransformer.CubePageTransformer.java
 * @Author: Vita
 * @Date: 2018-02-12 11:10
 * @Usage:
 */
public class RotatePageTransformer implements ViewPager.PageTransformer {

    private static final float MAX = 45;

    @Override
    public void transformPage(View page, float position) {
        float rotate = MAX * position;
        int width = page.getMeasuredWidth();
        int height = page.getMeasuredHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
        } else if (position <= 0) { // [-1,0]
            // moving to the left page
            page.setPivotX(width);
            page.setPivotY(height);
            page.setRotation(rotate);
        } else if (position <= 1) { // (0,1]
            page.setPivotX(0);
            page.setPivotY(height);
            page.setRotation(rotate);
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
        }
    }
}
