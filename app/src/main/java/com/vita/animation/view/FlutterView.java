package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;
import com.vita.animation.evaluator.BezierEvaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    private int mWidth, mHeight; // 整个View（容器）的宽、高
    private int mItemWidth, mItemHeight; // item的宽、高
    private List<Interpolator> mInterpolators;
    private boolean isAuto = false;
    private float mAutoDurationSecond = 1.5f; // unit: s
    private static final int FLUTTER_START = 0x77;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FLUTTER_START:
                    addItem();
                    mHandler.sendEmptyMessageDelayed(FLUTTER_START,
                            (long) (mAutoDurationSecond * 1000));
                    break;
            }
        }
    };

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

        mInterpolators = new ArrayList<>();
        mInterpolators.add(new AccelerateDecelerateInterpolator());
        mInterpolators.add(new AccelerateInterpolator());
        mInterpolators.add(new DecelerateInterpolator());
        mInterpolators.add(new LinearInterpolator());

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

        if (isAuto && !mHandler.hasMessages(FLUTTER_START)) {
            mHandler.sendEmptyMessage(FLUTTER_START);
        }
    }

    /**
     * item运动路线（这里使用的是贝塞尔曲线）动画
     */
    private ValueAnimator getItemPathAnim(final View target) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(getRandomPoint(2), getRandomPoint(1));

        PointF startPoint = new PointF((mWidth - mItemWidth) / 2, mHeight - mItemHeight);
        PointF endPoint = new PointF(mRandom.nextInt(getWidth()), 0);
        ValueAnimator valueAnim = ValueAnimator.ofObject(
                bezierEvaluator, startPoint, endPoint);
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

    /**
     * 获取贝塞尔曲线中间路过的点
     */
    private PointF getRandomPoint(int scale) {
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt((mWidth - 100));// 减去100是为了控制x轴活动范围
        // 在y轴上，为了确保第二个点在第一个点之上，把y分成了上下两半，这样动画效果好一些，也可以用其他方法
        pointF.y = mRandom.nextInt((mHeight - 100)) / scale;

        return pointF;
    }

    /**
     * item被添加时的动画
     */
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
        animSet.setInterpolator(mInterpolators.get(mRandom.nextInt(mInterpolators.size())));
        animSet.playSequentially(itemAddAnim);
        animSet.playSequentially(itemAddAnim, itemPathAnim);
        animSet.setTarget(target);

        return animSet;
    }

    public void addItem() {
        ImageView ivItem = new ImageView(getContext());
        ivItem.setImageResource(R.drawable.ic_favorite_24dp);
        ivItem.setLayoutParams(mItemLp);

        if (mItemWidth == 0) {
            mItemWidth = ivItem.getDrawable().getIntrinsicWidth();
        }
        if (mItemHeight == 0) {
            mItemHeight = ivItem.getDrawable().getIntrinsicHeight();
        }

        addView(ivItem);
//        Log.d(TAG, "addItem: childCount:: " + getChildCount());

        getItemAllAnim(ivItem).start();
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
//        Log.d(TAG, "onViewRemoved");
        child = null;
    }

    public void setInterpolators(Interpolator... interpolators) {
        if (interpolators == null || interpolators.length == 0) return;
        mInterpolators.clear();
        Collections.addAll(mInterpolators, interpolators);
    }

    public void addInterpolators(Interpolator... interpolators) {
        if (interpolators == null || interpolators.length == 0) return;
        for (Interpolator i : interpolators) {
            if (mInterpolators.contains(i)) {
                continue;
            }
            mInterpolators.add(i);
        }
    }

    public void setAuto(boolean auto, float durSecond) {
        this.isAuto = auto;
        if (durSecond >= 0.1f) {
            this.mAutoDurationSecond = durSecond;
        }
        mHandler.removeMessages(FLUTTER_START);
        postInvalidate();
    }

    public int getItemWidth() {
        return mItemWidth;
    }

    public void setItemWidth(int itemWidth) {
        mItemWidth = itemWidth;
        mItemLp.width = itemWidth;
    }

    public int getItemHeight() {
        return mItemHeight;
    }

    public void setItemHeight(int itemHeight) {
        mItemHeight = itemHeight;
        mItemLp.height = itemHeight;
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
