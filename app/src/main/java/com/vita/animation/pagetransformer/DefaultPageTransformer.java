package com.vita.animation.pagetransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @FileName: com.vita.animation.pagetransformer.DefaultPageTransformer.java
 * @Author: Vita
 * @Date: 2018-02-12 13:37
 * @Usage:
 */
public class DefaultPageTransformer implements ViewPager.PageTransformer {

    @Override
    public void transformPage(View page, float position) {
        page.setAlpha(1);
        page.setTranslationX(0);
        page.setTranslationY(0);
        page.setPivotX(page.getWidth() / 2);
        page.setPivotY(page.getHeight() / 2);
        page.setScaleX(1);
        page.setScaleY(1);
        page.setRotation(0);
    }
}
