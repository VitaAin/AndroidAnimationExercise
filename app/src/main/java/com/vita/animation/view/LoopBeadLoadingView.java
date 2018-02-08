package com.vita.animation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;

import java.util.ArrayList;
import java.util.Collection;
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
    private static final String LOADING_TEXT = "LOADING";
    private String[] mWord; // 存储初始化顺序后的文本
    private Text[] mTexts; // 存储字母对象
    private int mTextPos;
    private int mTextSize = 56;
    private int mLetterWidth = mTextSize;
    private int mTextScaleSize = 8;
    private boolean isCycle = false;
    private float mFallStickyMaxDistance;
    private boolean isGoingCenter = false;

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
        mTextPaint.setTextAlign(Paint.Align.CENTER);
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

        mRingCenterX = mWidth / 2;
        int offsetY = 0;
//        int offsetY = -48;
        mRingCenterY = mHeight / 2 + offsetY;
        mFallStickyMaxDistance = mRingRadius * 2 / 3;

        initFixedCircles();
        updateBead();
        initTexts();

        drawRefLine(canvas);
        drawFixedCircles(canvas);
        drawBead(canvas);
        drawFallStickyPath(canvas);

        drawTexts(canvas);

        if (mAnimSet == null) {
            startAnim();
        }
    }

    private void initFixedCircles() {
        if (mFixedCircles != null || mWidth == 0) return;
        Log.d(TAG, "initFixedCircles: " + mWidth);
        mFixedCircles = new ArrayList<>();

        double angle = 2 * Math.PI / FIXED_CIRCLE_NUM;
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
        if (isGoingCenter) return;
        if (mBead == null) {
            Circle firstFixedCircle = mFixedCircles.get(0);
            mBead = new Circle(firstFixedCircle.centerX, firstFixedCircle.centerY, mBeadRadius);
        }
    }

    private void initTexts() {
        if (mTexts != null) return;
        initWord();
        mTexts = new Text[mWord.length];
        boolean toRight = false;
        float x = mRingCenterX;
        for (int i = 0; i < mWord.length; i++) {
            // 向左运动
            if (!toRight) {// i=0,2,4,6
                mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_LEFT);
                toRight = true;
            } else {// i=1,3,5
                // 居中不动
                if (i + 1 == mWord.length) {// i=
                    mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_CENTER);
                } else { // 向右运动 // i=1,3,5
                    mTexts[i] = new Text(mWord[i], 0, x, Text.DIRECTION_RIGHT);
                }
                toRight = false;
            }
        }
    }

    /**
     * 初始化word的顺序,如loading->lgonaid,方便进行动画
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

    private void drawFallStickyPath(Canvas canvas) {
        if (!isFalling) return;
        Circle from = mFixedCircles.get(0);
        if ((mBead.centerY - from.centerY) > mFallStickyMaxDistance) {
            return;
        }

        Path path = new Path();
        path.moveTo(from.centerX - from.radius, from.centerY);
        path.lineTo(from.centerX + from.radius, from.centerY);
        path.quadTo(from.centerX, (from.centerY + mBead.centerY) / 2,
                mBead.centerX + mBead.radius, mBead.centerY);
        path.lineTo(mBead.centerX - mBead.radius, mBead.centerY);
        path.quadTo(from.centerX, (from.centerY + mBead.centerY) / 2,
                from.centerX - from.radius, from.centerY);
        canvas.drawPath(path, mCirclePaint);
    }

    private void drawTexts(Canvas canvas) {
        if (isCycle && mTextPos == 0) {
            return;
        }
        Log.d(TAG, "drawTexts: " + mTextPos);
        float textY = mRingCenterY + 2 * mRingRadius;
        for (int i = 0; i <= mTextPos; i++) {
            Text text = mTexts[i];
            mTextPaint.setTextSize(text.size);
            if (i == mTextPos) {
                canvas.drawText(text.content, text.x, textY, mTextPaint);
            } else {
                canvas.drawText(text.content, text.x + text.offsetX, textY, mTextPaint);
            }
        }
    }

    private void resetTextOffset() {
        if (mTexts == null) return;
        for (Text text : mTexts) {
            text.offsetX = 0;
        }
    }

    private void resetAll() {
        mAnimSet.end();
        mAnimSet.cancel();
        mAnimSet = null;
        resetTextOffset();
        mTexts = null;
        mFixedCircles = null;
        mBead = null;
        mTextPos = 0;
        isGoingCenter = false;
        isCycle = false;
        isFalling = false;
    }

    private void startAnim() {
        Animator animLoop = getLoopAnim();
        Animator animMix = getMixAnim();
        Animator animFall = getFallAnim();
        Animator animText = getTextAnim();

        mAnimSet = new AnimatorSet();
        mAnimSet.playSequentially(animLoop, animMix, animFall, animText);
        mAnimSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "onAnimationEnd: " + mWord.length);
                Log.d(TAG, "onAnimationEnd: to start, mTextPos:: " + mTextPos);
//                if (mTextPos != mWord.length - 1) {
//                    mTextPos++;
//                }
                mTextPos = (++mTextPos) % mWord.length;
                Log.d(TAG, "onAnimationEnd: after update:: " + mTextPos);
                if (mTextPos == 0) {
                    isCycle = true;
                    getGoCenterAnim().start();
                } else {
                    mAnimSet.start();
                }
            }
        });
        mAnimSet.start();
    }

    @NonNull
    private Animator getGoCenterAnim() {
        isGoingCenter = true;
        Collection<Animator> animators = new ArrayList<>();
        for (int i = 0; i < mFixedCircles.size(); i++) {
            final Circle circle = mFixedCircles.get(i);
            ValueAnimator xAnim = ObjectAnimator.ofFloat(circle.centerX, mRingCenterX);
            xAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    circle.centerX = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            ValueAnimator yAnim = ObjectAnimator.ofFloat(circle.centerY, mRingCenterY);
            yAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    circle.centerY = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });

            animators.add(xAnim);
            animators.add(yAnim);
        }
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1000);
        animSet.setInterpolator(new AnticipateOvershootInterpolator());
        animSet.playTogether(animators);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                resetAll();
                invalidate();
            }
        });

        return animSet;
    }

    @NonNull
    private Animator getTextAnim() {
        ValueAnimator animText = ObjectAnimator.ofInt(0, mTextSize,
                mTextSize + mTextScaleSize, 0,
                mTextSize + mTextScaleSize / 2, mTextSize);
        animText.setDuration(200);
        animText.setInterpolator(new AccelerateInterpolator());
        animText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curTextSize = (int) valueAnimator.getAnimatedValue();
                mTexts[mTextPos].setSize(curTextSize);
                invalidate();
            }
        });
        animText.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTexts[mTextPos].setSize(mTextSize);

                for (int i = mTextPos; i >= 0; i--) {
                    if (i >= mTextPos - 2) {
                        mTexts[i].setOffsetX(mLetterWidth);
                    } else {
                        mTexts[i].setOffsetX(mLetterWidth + Math.abs(mTexts[i + 2].offsetX));
                    }
                }
                for (int i = mTextPos; i < mTexts.length; i++) {
                    mTexts[i].setOffsetX(0);
                }
//                if (mTextPos == 0) {
//                    resetTextOffset();
//                }
            }
        });
        return animText;
    }

    @NonNull
    private Animator getFallAnim() {
        ValueAnimator animFall = ObjectAnimator.ofFloat(mRingCenterY - mRingRadius,
                mRingCenterY + mRingRadius + mTextHeight);
        animFall.setDuration(1000);
        animFall.setInterpolator(new AccelerateInterpolator());
        animFall.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mBead.centerY = (float) valueAnimator.getAnimatedValue();
                mFixedCircles.get(0).radius = calcFixedCircleRadius();
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
        return animFall;
    }

    @NonNull
    private Animator getMixAnim() {
        ValueAnimator animMix = ObjectAnimator.ofFloat(mFixedCircleRadius,
                mFixedCircleRadius + mBeadRadius, mFixedCircleRadius,
                mFixedCircleRadius + mBeadRadius / 2, mFixedCircleRadius + mBeadRadius);
        animMix.setDuration(600);
        animMix.setInterpolator(new AccelerateDecelerateInterpolator());
        animMix.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mFixedCircles.get(0).radius = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        return animMix;
    }

    @NonNull
    private Animator getLoopAnim() {
        ValueAnimator animLoop = ObjectAnimator.ofInt(0, 360);
        animLoop.setDuration(2000);
        animLoop.setInterpolator(new AccelerateDecelerateInterpolator());
        animLoop.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float angle = (int) valueAnimator.getAnimatedValue();

                /*
                圆点坐标：(x0,y0)，半径：r，角度：a，则圆上任一点为：（x1,y1）
                x1 = x0 + r * sin(a * PI / 180)
                y1 = y0 - r * cos(a * PI / 180)
                 */
                mBead.centerX = (float) (mRingCenterX + mRingRadius * Math.sin(angle * Math.PI / 180));
                mBead.centerY = (float) (mRingCenterY - mRingRadius * Math.cos(angle * Math.PI / 180));
                invalidate();
            }
        });
        return animLoop;
    }

    /**
     * 移动圆下落时，计算顶部固定圆的半径
     */
    private float calcFixedCircleRadius() {
        float distance = calcDistance();
        distance = Math.min(distance, mFallStickyMaxDistance);

        // 比例
        float percent = distance / mFallStickyMaxDistance;

        float start = mFixedCircleRadius + mBeadRadius;
        float end = mFixedCircleRadius;
        return start + percent * (end - start);
    }

    /**
     * 计算顶部固定圆与移动圆之间圆心的直线距离
     */
    private float calcDistance() {
        Circle firstCircle = mFixedCircles.get(0);
        float xDistance = mBead.centerX - firstCircle.centerX;
        float yDistance = mBead.centerY - firstCircle.centerY;
        // d = (x^2 + y^2)^(1/2)
        return (float) Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
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
                this.offsetX = -offsetX;
            } else if (direction == DIRECTION_RIGHT) {
                this.offsetX = offsetX;
            }
        }

        @Override
        public String toString() {
            return "Text{" +
                    "content='" + content + "\'" +
                    ", size=" + size +
                    ", x=" + x +
                    ", direction=" + direction +
                    "}";
        }
    }
}

class MyTypeEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startPoint, PointF endPoint) {
        return null;
    }
}
