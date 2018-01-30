package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;
import com.vita.animation.evaluator.BezierEvaluator;

import java.util.Random;

/**
 * @FileName: com.vita.animation.view.ElegantLeavesView.java
 * @Author: Vita
 * @Date: 2018-01-30 10:56
 * @Usage:
 */
public class FlutterView extends FrameLayout {

    private static final String TAG = "FlutterLayout";

    private LayoutParams mItemLp;

    private Random mRandom = new Random();
    private Paint mPathPaint;

    private int mWidth, mHeight;
    private int mItemWidth, mItemHeight;

    public FlutterView(Context context) {
        super(context);
        init();
    }

    public FlutterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlutterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0xaaccff);

        mItemLp = new LayoutParams(
                mItemWidth == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mItemWidth,
                mItemHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mItemHeight,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private ValueAnimator getItemPathAnim(final View target) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(getRandomPoint(2), getRandomPoint(1));

        PointF startPoint = new PointF((mWidth - mItemWidth) / 2, mHeight - mItemHeight);
        PointF endPoint = new PointF(mRandom.nextInt(getWidth()), 0);
        ValueAnimator valueAnim = ValueAnimator.ofObject(bezierEvaluator,
                startPoint, endPoint);
        valueAnim.setTarget(target);
        valueAnim.setDuration(3000);
        valueAnim.addUpdateListener(new BezierAnimUpdateListener(target));
        valueAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                removeView(target);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        return valueAnim;
    }

    private PointF getRandomPoint(int scale) {
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt((mWidth - 100));
        pointF.y = mRandom.nextInt((mHeight - 100)) / scale;

        return pointF;
    }

    private AnimatorSet getItemAddAnim(View target) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", 0.2f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", 0.2f, 1);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, "alpha", 0.2f, 1);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animSet.setDuration(800);
        animSet.playTogether(scaleX, scaleY, alpha);
        animSet.setTarget(target);

        return animSet;
    }

    private Animator getItemAllAnim(View target) {
        AnimatorSet itemAddAnim = getItemAddAnim(target);
        ValueAnimator itemPathAnim = getItemPathAnim(target);

        AnimatorSet animSet = new AnimatorSet();
        animSet.playSequentially(itemAddAnim);
        animSet.playSequentially(itemAddAnim, itemPathAnim);
        animSet.setTarget(target);

        return animSet;
    }

    public void addItem() {
        ImageView ivItem = new ImageView(getContext());
        ivItem.setImageResource(R.drawable.ic_favorite_24dp);
        ivItem.setLayoutParams(mItemLp);

        Log.d(TAG, "addItem: " + ivItem.getDrawable().getIntrinsicWidth() + ", "
                + ivItem.getDrawable().getIntrinsicHeight());
        if (mItemWidth == 0) {
            mItemWidth = ivItem.getDrawable().getIntrinsicWidth();
        }
        if (mItemHeight == 0) {
            mItemHeight = ivItem.getDrawable().getIntrinsicHeight();
        }

        addView(ivItem);
        Log.d(TAG, "addItem: childCount:: " + getChildCount());

        getItemAllAnim(ivItem).start();
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        Log.d(TAG, "onViewRemoved");
        child = null;
    }

    private class BezierAnimUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierAnimUpdateListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animator) {
            PointF pointF = (PointF) animator.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            target.setAlpha(1 - animator.getAnimatedFraction());
        }
    }
}
