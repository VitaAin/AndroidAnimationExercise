package com.vita.animation.tween;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vita.animation.R;

public class TranslateActivity extends AppCompatActivity {

    private static final String TAG = "TranslateActivity";

    private TranslateAnimation mAnim;
    private float mFromXDefault = 0;
    private float mFromYDefault = 0;
    private float mToXDefault = 1;
    private float mToYDefault = 1;
    private Interpolator mInterpolatorDefault = new AccelerateDecelerateInterpolator();
    private float mFromX = mFromXDefault;
    private float mFromY = mFromYDefault;
    private float mToX = mToXDefault;
    private float mToY = mToYDefault;
    private Interpolator mInterpolator = mInterpolatorDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        setupFromXDeltaView();
        setupToXDeltaView();
        setupFromYDeltaView();
        setupToYDeltaView();

        setupAnim();
    }

    private void setupAnim() {
        mAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, mFromX,
                Animation.RELATIVE_TO_PARENT, mToX,
                Animation.RELATIVE_TO_PARENT, mFromY,
                Animation.RELATIVE_TO_PARENT, mToY);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_translate);
        ivShow.setAnimation(mAnim);
    }

    private void setupFromXDeltaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_x_delta);
        sb.setProgress((int) (mFromXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_from_x_delta);
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

    private void setupToXDeltaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_x_delta);
        sb.setProgress((int) (mToXDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_to_x_delta);
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

    private void setupFromYDeltaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_y_delta);
        sb.setProgress((int) (mFromYDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_from_y_delta);
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

    private void setupToYDeltaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_y_delta);
        sb.setProgress((int) (mToYDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_to_y_delta);
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
}
