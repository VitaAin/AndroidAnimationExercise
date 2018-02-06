package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.vita.animation.view.SpringLoadingView.java
 * @Author: Vita
 * @Date: 2018-02-06 16:15
 * @Usage:
 * @Rference: https://github.com/Huyamin150/-dampView-springingView
 */
public class SpringLoadingView extends FrameLayout {

    private static final String TAG = "SpringLoadingView";
    private int[] mItemResId = {
            R.drawable.p1, R.drawable.p3, R.drawable.p5, R.drawable.p7
    };
    private int mCurItemPos = 0;
    private int mWidth, mHeight;
    private float mFallHeight = 160;
    private AnimatorSet mAnimSet;
    private PointF mItemPoint;
    private float mItemWidth, mItemHeight;

    public SpringLoadingView(Context context) {
        super(context);
        init();
    }

    public SpringLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0xffeeeeee);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        addItem();
    }

    private void addItem() {
        ImageView ivItem = new ImageView(getContext());
        ivItem.setImageResource(mItemResId[mCurItemPos]);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        ivItem.setLayoutParams(lp);

        if (mItemWidth <= 0) {
            mItemWidth = ivItem.getDrawable().getIntrinsicWidth();
        }
        if (mItemHeight <= 0) {
            mItemHeight = ivItem.getDrawable().getIntrinsicHeight();
        }

        addView(ivItem);

        getAllAnim().start();
    }

    private Animator getAllAnim() {
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(getFallAnim());

        return animSet;
    }

    private Animator getFallAnim() {
        float startY = mHeight / 2;
        float endY = startY + mFallHeight;
        ValueAnimator animFall = ObjectAnimator.ofFloat(startY, endY);
        animFall.setDuration(1000);
        animFall.setInterpolator(new AccelerateInterpolator());
        animFall.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

            }
        });
        return animFall;
    }
}
