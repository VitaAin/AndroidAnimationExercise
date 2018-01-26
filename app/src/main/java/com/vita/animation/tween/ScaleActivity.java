package com.vita.animation.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vita.animation.R;

public class ScaleActivity extends AppCompatActivity {

    private static final String TAG = "ScaleActivity";

    private ScaleAnimation mAnim;
    private float mFromXDefault = 0;
    private float mFromYDefault = 0;
    private float mToXDefault = 1;
    private float mToYDefault = 1;
    private float mPivotXDefault = 1;
    private float mPivotYDefault = 1;
    private Interpolator mInterpolatorDefault = new AccelerateDecelerateInterpolator();
    private float mFromX = mFromXDefault;
    private float mFromY = mFromYDefault;
    private float mToX = mToXDefault;
    private float mToY = mToYDefault;
    private float mPivotX = mPivotXDefault;
    private float mPivotY = mPivotYDefault;
    private Interpolator mInterpolator = mInterpolatorDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        setupFromXScaleView();
        setupToXScaleView();
        setupFromYScaleView();
        setupToYScaleView();
        setupPivotXView();
        setupPivotYView();

        setupAnim();
    }

    private void setupAnim() {
        mAnim = new ScaleAnimation(mFromX, mToX, mFromY, mToY);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_scale);
        ivShow.setAnimation(mAnim);
    }

    private void setupAnimInterpolator() {
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_alpha);
        ivShow.setAnimation(mAnim);
    }

    private void setupAnimPivot() {
        mAnim = new ScaleAnimation(mFromX, mToX, mFromY, mToY,
                Animation.RELATIVE_TO_PARENT, mPivotX,
                Animation.RELATIVE_TO_PARENT, mPivotY);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_scale);
        ivShow.setAnimation(mAnim);
    }

    private void setupFromXScaleView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_x_scale);
        sb.setProgress((int) (mFromXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_from_x_scale);
        tv.setText(mFromXDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFromX = (float) seekBar.getProgress() / 10;
                setupAnim();
            }
        });
    }

    private void setupToXScaleView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_x_scale);
        sb.setProgress((int) (mToXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_to_x_scale);
        tv.setText(mToXDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mToX = (float) seekBar.getProgress() / 10;
                setupAnim();
            }
        });
    }

    private void setupFromYScaleView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_y_scale);
        sb.setProgress((int) (mFromXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_from_y_scale);
        tv.setText(mFromYDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFromY = (float) seekBar.getProgress() / 10;
                setupAnim();
            }
        });
    }

    private void setupToYScaleView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_y_scale);
        sb.setProgress((int) (mToXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_to_y_scale);
        tv.setText(mToYDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mToY = (float) seekBar.getProgress() / 10;
                setupAnim();
            }
        });
    }

    private void setupPivotXView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_pivot_x);
        sb.setProgress((int) (mPivotXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_pivot_x);
        tv.setText(mPivotXDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPivotX = (float) seekBar.getProgress() / 10;
                setupAnimPivot();
            }
        });
    }

    private void setupPivotYView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_pivot_y);
        sb.setProgress((int) (mPivotYDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_pivot_y);
        tv.setText(mToYDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText((float) i / 10 + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPivotY = (float) seekBar.getProgress() / 10;
                setupAnimPivot();
            }
        });
    }
}
