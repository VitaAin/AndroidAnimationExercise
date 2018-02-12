package com.vita.animation.pagetransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @FileName: com.vita.animation.pagetransformer.CubePageTransformer.java
 * @Author: Vita
 * @Date: 2018-02-12 11:10
 * @Usage:
 */
public class CubePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int width = page.getWidth();
        int pivotX = 0;
        if (position > 0 && position <= 1) { // (0,1]
            pivotX = 0;
        } else if (position == 0) { // [0,0]

        } else if (position >= -1 && position < 0) { // [-1,0)
            pivotX = width;
        }
        page.setPivotX(pivotX);
        page.setRotationY(90 * position);
    }
}
