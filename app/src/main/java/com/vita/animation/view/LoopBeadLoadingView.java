package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: com.vita.animation.view.LoopBeadLoadingView.java
 * @Author: Vita
 * @Date: 2018-02-02 13:29
 * @Usage:
 * @Reference: http://blog.csdn.net/u010360371/article/details/50582844
 */
public class LoopBeadLoadingView extends View {

    private static final String TAG = "LoopBeadLoadingView";

    private Paint mRefLinePaint;
    private Paint mCirclePaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    private float mRingRadius = 100;
    private static final int FIXED_CIRCLE_NUM = 6;
    private float mFixedCircleRadius = 16;
    private List<Circle> mFixedCircles;
    private float mBeadRadius = 8;
    private Circle mBead;
    private float mTextHeight = 48;
    private int mRingCenterX;
    private int mRingCenterY;
    private AnimatorSet mAnimSet;
    private boolean isFalling = false;
    private static final String LOADING_TEXT = "loading";
    private String[] mWord; // 存储初始化顺序后的文本
    private Text[] mTexts; // 存储字母对象
    private int mTextPos;
    private int mTextSize = 88;
    private int mTextScaleSize = 8;

    public LoopBeadLoadingView(Context context) {
        super(context);
        init();
    }

    public LoopBeadLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoopBeadLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initFixedCircles();

        setBackgroundColor(0xffaaccff);

        mRefLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRefLinePaint.setColor(Color.BLUE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.WHITE);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor((Color.MAGENTA));
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initFixedCircles();
        updateBead();
        if (mTexts == null) {
            initTexts();
        }

        drawRefLine(canvas);
        drawFixedCircles(canvas);
        drawBead(canvas);
        if (isFalling) {
            //TODO draw bezier
        }

        drawTexts(canvas);

        if (mAnimSet == null) {
            startAnim();
        }
    }

    private void initFixedCircles() {
        mFixedCircles = new ArrayList<>();

        double angle = 2 * Math.PI / FIXED_CIRCLE_NUM;

        mRingCenterX = mWidth / 2;
        int offsetY = 0;
//        int offsetY = -48;
        mRingCenterY = mHeight / 2 + offsetY;
        mFixedCircles.add(new Circle(mRingCenterX,
                mRingCenterY - mRingRadius, mFixedCircleRadius));
        mFixedCircles.add(new Circle(mRingCenterX + mRingRadius * Math.sin(angle),
                mRingCenterY - mRingRadius * Math.cos(angle), mFixedCircleRadius));
        mFixedCircles.add(new Circle(mRingCenterX + mRingRadius * Math.sin(angle),
                mRingCenterY + mRingRadius * Math.cos(angle), mFixedCircleRadius));
        mFixedCircles.add(new Circle(mRingCenterX,
                mRingCenterY + mRingRadius, mFixedCircleRadius));
        mFixedCircles.add(new Circle(mRingCenterX - mRingRadius * Math.sin(angle),
                mRingCenterY + mRingRadius * Math.cos(angle), mFixedCircleRadius));
        mFixedCircles.add(new Circle(mRingCenterX - mRingRadius * Math.sin(angle),
                mRingCenterY - mRingRadius * Math.cos(angle), mFixedCircleRadius));
    }

    private void updateBead() {
        if (mBead == null) {
            Circle firstFixedCircle = mFixedCircles.get(0);
            mBead = new Circle(firstFixedCircle.centerX, firstFixedCircle.centerY, mBeadRadius);
        }
    }

    private void initTexts() {
        initWord();
        mTexts = new Text[mWord.length];
        boolean toRight = false;
        float x = mRingCenterX;
        for (int i = 0; i < mWord.length; i++) {
            // 向左运动
            if (!toRight) {
                mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_LEFT);
                toRight = true;
            } else {
                // 居中不动
                if (i + 1 == mWord.length) {
                    mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_CENTER);
                } else { // 向右运动
                    mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_RIGHT);
                }
                toRight = false;
            }
        }
    }

    /**
     * 初始化word的顺序,例如loading->lgonaid,方便进行动画
     */
    protected void initWord() {
        mWord = new String[LOADING_TEXT.length()];
        int a = 0;
        int b = mWord.length - 1;
        int i = 0;
        while (a <= b) {
            if (a == b) {
                mWord[i] = String.valueOf(LOADING_TEXT.charAt(a));
            } else {
                mWord[i] = String.valueOf(LOADING_TEXT.charAt(a));
                mWord[i + 1] = String.valueOf(LOADING_TEXT.charAt(b));
            }
            a++;
            b--;
            i += 2;
        }
    }

    private void drawRefLine(Canvas canvas) {
        canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mRefLinePaint);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mRefLinePaint);
    }

    private void drawFixedCircles(Canvas canvas) {
        for (Circle fixedCircle : mFixedCircles) {
            canvas.drawCircle(fixedCircle.centerX, fixedCircle.centerY, fixedCircle.radius, mCirclePaint);
        }
    }

    private void drawBead(Canvas canvas) {
        canvas.drawCircle(mBead.centerX, mBead.centerY, mBead.radius, mCirclePaint);
    }

    private void drawTexts(Canvas canvas) {
        float textY = mRingCenterY + 2 * mRingRadius;

//        mTextPaint.setTextSize(mTextSize);
        for (int i = 0; i <= mTextPos; i++) {
            Text text = mTexts[i];
//            mTextPaint.setTextSize(text.size);
            Log.d(TAG, "drawTexts: " + i + " --> content: " + text.content + " --> offset: " + text.offsetX);
            if (i == mTextPos) {
                Log.d(TAG, "drawTexts: i == mTextPos == " + mTextPos);
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(text.content, text.x, textY, mTextPaint);
            } else {
                Log.d(TAG, "drawTexts: i != mTextPos, i == " + i + ", mTextPos == " + mTextPos);
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                if (text.direction == Text.DIRECTION_RIGHT) {
                    Log.d(TAG, "drawTexts: direction is right");
                    canvas.drawText(text.content,
                            text.x + mTextPaint.measureText(mWord[mTextPos - 1]) / 2 + text.offsetX,
                            textY, mTextPaint);
                } else if (text.direction == Text.DIRECTION_LEFT) {
                    Log.d(TAG, "drawTexts: direction is left");
                    canvas.drawText(text.content,
                            text.x - mTextPaint.measureText(mWord[i]) -
                                    mTextPaint.measureText(mWord[mTextPos - 1]) / 2 + text.offsetX,
                            textY, mTextPaint);
                }
            }
        }
    }

    private void resetTextOffset() {
        if (mTexts == null) return;
        for (Text text : mTexts) {
            text.offsetX = 0;
        }
    }

    private void startAnim() {
        ValueAnimator animLoop = ObjectAnimator.ofFloat(0, (float) (2 * Math.PI));
        animLoop.setDuration(2000);
        animLoop.setInterpolator(new AccelerateDecelerateInterpolator());
        animLoop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float angle = (float) valueAnimator.getAnimatedValue();
                /*
                圆点坐标：(x0,y0)，半径：r，角度：a，则圆上任一点为：（x1,y1）
                x1 = x0 + r * sin(a * PI / 180)
                y1 = y0 - r * cos(a * PI / 180)
                 */
                mBead.centerX = (float) (mRingCenterX + mRingRadius * Math.sin(angle));
                mBead.centerY = (float) (mRingCenterY - mRingRadius * Math.cos(angle));
                invalidate();
            }
        });

        ValueAnimator animFall = ObjectAnimator.ofFloat(mRingCenterY - mRingRadius, mRingCenterY + mRingRadius + mTextHeight);
        animFall.setDuration(500);
        animFall.setInterpolator(new AccelerateInterpolator());
        animFall.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBead.centerY = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animFall.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isFalling = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isFalling = false;
            }
        });

        ValueAnimator animText = ObjectAnimator.ofInt(0, mTextSize, mTextSize + mTextScaleSize, 0,
                mTextSize + mTextScaleSize / 2, mTextSize);
        animText.setDuration(200);
        animText.setInterpolator(new AccelerateInterpolator());
        animText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curTextSize = (int) valueAnimator.getAnimatedValue();
//                Log.d(TAG, "onAnimationUpdate: curTextSize: " + curTextSize);
                if (mTextPos >= 0) {
                    mTexts[mTextPos].setSize(curTextSize);
                }
                invalidate();
            }
        });
        animText.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTexts[mTextPos].setSize(mTextSize);

                int tempPos = mTextPos;
                Log.d(TAG, "onAnimationStart: tempPos:: " + tempPos);
                int halfLen = mWord.length / 2;
                while (tempPos - halfLen >= 0) {
                    String content = mTexts[mTextPos].content;
                    Log.d(TAG, "onAnimationStart: in while: content--> " + content);
                    float offset = mTextPaint.measureText(content);
                    Log.d(TAG, "onAnimationStart: in while: tempPos--> " + tempPos + ", offsetX--> " + offset);
                    mTexts[tempPos - halfLen].setOffsetX(offset);
                    tempPos -= 2;
                }
                if (mTextPos == 0) {
                    resetTextOffset();
                }
            }
        });

        mAnimSet = new AnimatorSet();
        mAnimSet.playSequentially(animLoop, animFall, animText);
        mAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                Log.d(TAG, "onAnimationEnd: to start");
                mTextPos = (++mTextPos) % mWord.length;
                mAnimSet.start();
            }
        });
        mAnimSet.start();
    }

    private class Circle {
        private float centerX;
        private float centerY;
        private float radius;

        public Circle(Number centerX, Number centerY, Number radius) {
            this.centerX = centerX.floatValue();
            this.centerY = centerY.floatValue();
            this.radius = radius.floatValue();
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "centerX=" + centerX +
                    ", centerY=" + centerY +
                    ", radius=" + radius +
                    '}';
        }

        public float getCenterX() {
            return centerX;
        }

        public void setCenterX(float centerX) {
            this.centerX = centerX;
        }

        public float getCenterY() {
            return centerY;
        }

        public void setCenterY(float centerY) {
            this.centerY = centerY;
        }

        public float getRadius() {
            return radius;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }
    }

    private class Text {
        private static final int DIRECTION_LEFT = 1;
        private static final int DIRECTION_CENTER = 2;
        private static final int DIRECTION_RIGHT = 3;

        private String content;
        private int size;
        private float x;
        private int direction;
        private float offsetX;

        public Text(String content, int size, float x, int direction) {
            this.content = content;
            this.size = size;
            this.x = x;
            this.direction = direction;
            this.offsetX = 0;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public void setOffsetX(float offsetX) {
            if (direction == DIRECTION_LEFT) {
                this.offsetX -= offsetX;
                Log.d(TAG, "setOffsetX: left -- " + this.offsetX);
            } else if (direction == DIRECTION_RIGHT) {
                this.offsetX += offsetX;
                Log.d(TAG, "setOffsetX: right -- " + this.offsetX);
            }
        }

        @Override
        public String toString() {
            return "Text{" +
                    "content='" + content + '\'' +
                    ", size=" + size +
                    ", x=" + x +
                    ", direction=" + direction +
                    '}';
        }
    }
}
