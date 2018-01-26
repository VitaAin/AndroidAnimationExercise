package com.vita.animation.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vita.animation.R;

public class RotateActivity extends AppCompatActivity {

    private static final String TAG = "TranslateActivity";

    private RotateAnimation mAnim;
    private float mFromDegreeDefault = 0;
    private float mToDegreeDefault = 180;
    private float mPivotXDefault = 1;
    private float mPivotYDefault = 1;
    private Interpolator mInterpolatorDefault = new AccelerateDecelerateInterpolator();
    private float mFromDegree = mFromDegreeDefault;
    private float mToDegree = mToDegreeDefault;
    private float mPivotX = mPivotXDefault;
    private float mPivotY = mPivotYDefault;
    private Interpolator mInterpolator = mInterpolatorDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);

        setupFromDegreeView();
        setupToDegreeView();
        setupPivotXView();
        setupPivotYView();

        setupAnim();
    }

    private void setupAnim() {
        mAnim = new RotateAnimation(mFromDegree, mToDegree,
                Animation.RELATIVE_TO_SELF, mPivotX,
                Animation.RELATIVE_TO_SELF, mPivotY);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_rotate);
        ivShow.setAnimation(mAnim);
    }

    private void setupAnimInterpolator() {
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_rotate);
        ivShow.setAnimation(mAnim);
    }

    private void setupFromDegreeView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_degree);
        sb.setProgress((int) mFromDegreeDefault);
        final TextView tv = (TextView) findViewById(R.id.tv_from_degree);
        tv.setText(mFromDegreeDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFromDegree = (float) seekBar.getProgress() / 10;
                setupAnim();
            }
        });
    }

    private void setupToDegreeView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_degree);
        sb.setProgress((int) mToDegreeDefault);
        final TextView tv = (TextView) findViewById(R.id.tv_to_degree);
        tv.setText(mToDegreeDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mToDegree = (float) seekBar.getProgress() / 10;
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
                setupAnim();
            }
        });
    }

    private void setupPivotYView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_pivot_y);
        sb.setProgress((int) (mPivotYDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_pivot_y);
        tv.setText(mPivotYDefault + "");
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
                setupAnim();
            }
        });
    }
}
