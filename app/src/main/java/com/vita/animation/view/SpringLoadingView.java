package com.vita.animation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;

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
    private float mItemWidth, mItemHeight;
    private ImageView mIvItem;
    private ElasticTextView mElasticTextView;
    private Handler mHandler = new Handler();

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

        if (getChildCount() == 0) {
            addItem();
            addText();
        }
    }

    private void addItem() {
        mIvItem = new ImageView(getContext());
        mIvItem.setImageResource(mItemResId[mCurItemPos]);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        mIvItem.setLayoutParams(lp);

        if (mItemWidth <= 0) {
            mItemWidth = mIvItem.getDrawable().getIntrinsicWidth();
        }
        if (mItemHeight <= 0) {
            mItemHeight = mIvItem.getDrawable().getIntrinsicHeight();
        }

        addView(mIvItem);

        mIvItem.startAnimation(getFallAllAnim());
    }

    private Animation getFallAllAnim() {
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(getRotateAnim());
        animSet.addAnimation(getFallAnim());
        animSet.setInterpolator(new AccelerateInterpolator());
        return animSet;
    }

    private Animation getBackAllAnim() {
        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(getRotateAnim());
        animSet.addAnimation(getBackAnim());
        animSet.setInterpolator(new DecelerateInterpolator());
        return animSet;
    }

    private Animation getFallAnim() {
        Animation fallAnim = getTranslateAnim(0, mFallHeight);
        fallAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvItem.clearAnimation();
                changeIcon();
                mIvItem.startAnimation(getBackAllAnim());
                mElasticTextView.setStatus(ElasticTextView.STATUS_HIT);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return fallAnim;
    }

    private Animation getBackAnim() {
        Animation backAnim = getTranslateAnim(mFallHeight, 0);
        backAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mElasticTextView.setStatus(ElasticTextView.STATUS_RESTORE);
                    }
                }, 100);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvItem.clearAnimation();
                mIvItem.startAnimation(getFallAllAnim());
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return backAnim;
    }

    private Animation getTranslateAnim(float fromYDelta, float toYDelta) {
        TranslateAnimation animTranslate = new TranslateAnimation(0, 0, fromYDelta, toYDelta);
        animTranslate.setDuration(1000);
        animTranslate.setFillAfter(true);
        return animTranslate;
    }

    private Animation getRotateAnim() {
        RotateAnimation animRotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animRotate.setDuration(1000);
        return animRotate;
    }

    private void changeIcon() {
        mCurItemPos = (++mCurItemPos) % mItemResId.length;
        mIvItem.setImageResource(mItemResId[mCurItemPos]);
    }

    private void addText() {
        mElasticTextView = new ElasticTextView(getContext());
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL);
        mElasticTextView.setLayoutParams(lp);
        mElasticTextView.setY(mHeight / 2 + mFallHeight + mItemHeight*3/2);

        addView(mElasticTextView);
    }
}

class ElasticTextView extends View {

    private static final String TAG = "ElasticTextView";
    private Paint mRefLinesPaint, mTextPaint;
    private String mText = "Loading";
    private int mTextSize = 48;
    private Path mPath;
    private int mWidth, mHeight;
    private float mFactor;
    private float mTextWidth;

    public static final int STATUS_HIT = 0;//向下弯曲的状态
    public static final int STATUS_RESTORE = 1;//向上恢复的状态

    public ElasticTextView(Context context) {
        super(context);
        init();
    }

    public ElasticTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ElasticTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        setBackgroundColor(0x33aaccff);
        mRefLinesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRefLinesPaint.setColor(Color.MAGENTA);
        mRefLinesPaint.setStrokeWidth(4);
        mRefLinesPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mTextWidth = mTextPaint.measureText(mText);
        setMeasuredDimension((int) (mTextWidth + 64), mTextSize + 16);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawRefLines(canvas);
        drawText(canvas);
    }

    private void drawRefLines(Canvas canvas) {
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mRefLinesPaint);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mRefLinesPaint);
    }

    private void drawText(Canvas canvas) {
        if (mPath == null) {
            mPath = new Path();
        }
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.quadTo(mWidth / 2, mFactor * mHeight, mWidth, 0);
//        canvas.drawPath(mPath, mRefLinesPaint);
        float hOffset = mWidth / 2 - mTextWidth / 2;
        float vOffset = mTextSize;
        canvas.drawTextOnPath(mText, mPath, hOffset, vOffset, mTextPaint);
    }

    public void setStatus(int status) {
        switch (status) {
            case STATUS_HIT:
                mFactor = 0.4f;
                break;
            case STATUS_RESTORE:
                mFactor = 0f;
                break;
            default:
                mFactor = 0;
                break;
        }
        invalidate();
    }
}
