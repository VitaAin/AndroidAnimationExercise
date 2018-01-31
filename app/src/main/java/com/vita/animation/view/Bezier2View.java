package com.vita.animation.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.vita.animation.R;

import java.util.Random;

/**
 * @FileName: com.vita.animation.view.Bezier1View.java
 * @Author: Vita
 * @Date: 2018-01-30 15:02
 * @Usage:
 */
public class Bezier2View extends FrameLayout {

    private static final String TAG = "Bezier2View";

    private int[] mItemResId = {
            R.drawable.ic_favorite_blue_24dp, R.drawable.ic_favorite_cyan_24dp,
            R.drawable.ic_favorite_green_24dp, R.drawable.ic_favorite_orange_24dp,
            R.drawable.ic_favorite_pink_24dp, R.drawable.ic_favorite_purpose_24dp,
            R.drawable.ic_favorite_red_24dp, R.drawable.ic_favorite_yellow_24dp
    };
    private Random mRandom = new Random();
    private Paint mPathPaint;
    private int mWidth;
    private int mHeight;
    private LayoutParams mItemLp;
    private Animator mItemAnim;
    private static final int ADD_ITEM = 0x90;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADD_ITEM:
                    addItem();
                    sendEmptyMessageDelayed(ADD_ITEM, 400);
                    break;
            }
        }
    };

    public Bezier2View(Context context) {
        super(context);
        init();
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setColor(Color.BLUE);
        mPathPaint.setStrokeWidth(4);
        mPathPaint.setStyle(Paint.Style.STROKE);

        mItemLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        if (mItemAnim == null) {
            mHandler.sendEmptyMessage(ADD_ITEM);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
        path.moveTo(mWidth / 2, mHeight);
        path.cubicTo(mWidth, mHeight * 3 / 4,
                0, mHeight / 4,
                mWidth / 2, 0);
        canvas.drawPath(path, mPathPaint);
    }

    public void addItem() {
        Log.d(TAG, "addItem: ");
        final ImageView ivItem = new ImageView(getContext());
        ivItem.setImageResource(mItemResId[mRandom.nextInt(mItemResId.length)]);
        ivItem.setLayoutParams(mItemLp);

        addView(ivItem);

        mItemAnim = getItemAnim(ivItem);
        mItemAnim.start();
        mItemAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                removeView(ivItem);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator getItemAnim(final View target) {
        Path path = new Path();
        path.moveTo(mWidth / 2, mHeight);
        path.cubicTo(mWidth, mHeight * 3 / 4,
                0, mHeight / 4,
                mWidth / 2, 0);

        ObjectAnimator anim = ObjectAnimator.ofFloat(target, View.X, View.Y, path);
        anim.setDuration(5000);

        return anim;
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        child = null;
    }
}
