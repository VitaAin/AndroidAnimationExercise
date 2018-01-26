package com.vita.animation.tween;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.vita.animation.R;

public class AlphaActivity extends AppCompatActivity {

    private static final String TAG = "AlphaActivity";

    private AlphaAnimation mAnim;
    private float mFromDefault = 1;
    private float mToDefault = 0;
    private Interpolator mInterpolatorDefault = new AccelerateDecelerateInterpolator();
    private float mFromAlpha = mFromDefault;
    private float mToAlpha = mToDefault;
    private Interpolator mInterpolator = mInterpolatorDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        setupFromAlphaView();
        setupToAlphaView();

        setupInterpolatorView();

        setupAnim(mFromDefault, mToDefault);
    }

    private void setupAnim(float from, float to) {
        mAnim = new AlphaAnimation(from, to);
        mAnim.setRepeatMode(Animation.RESTART);
        mAnim.setRepeatCount(Animation.INFINITE);
        mAnim.setDuration(3000);
        mAnim.setInterpolator(mInterpolator);

//        fillAfter ---- true表示保持动画结束时的状态，false表示不保持
//        mAnim.setFillAfter();

        ImageView ivShow = (ImageView) findViewById(R.id.iv_alpha);
        ivShow.setAnimation(mAnim);
    }

    private void setupAnimInterpolator() {
        mAnim.setInterpolator(mInterpolator);

        ImageView ivShow = (ImageView) findViewById(R.id.iv_alpha);
        ivShow.setAnimation(mAnim);
    }

    private void setupInterpolatorView() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg_interpolator);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_acc_dec:
                        mInterpolator = new AccelerateDecelerateInterpolator();
                        break;
                    case R.id.rb_acc:
                        mInterpolator = new AccelerateInterpolator();
                        break;
                    case R.id.rb_anti:
                        mInterpolator = new AnticipateInterpolator();
                        break;
                    case R.id.rb_anti_os:
                        mInterpolator = new AnticipateOvershootInterpolator();
                        break;
                    case R.id.rb_bounce:
                        mInterpolator = new BounceInterpolator();
                        break;
                    case R.id.rb_dec:
                        mInterpolator = new DecelerateInterpolator();
                        break;
                    case R.id.rb_linear:
                        mInterpolator = new LinearInterpolator();
                        break;
                    case R.id.rb_overshoot:
                        mInterpolator = new OvershootInterpolator();
                        break;
                }
                setupAnimInterpolator();
            }
        });
    }

    private void setupFromAlphaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_from_alpha);
        sb.setProgress((int) (mFromDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_from_alpha);
        tv.setText(mFromDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float fromAlpha = (float) i / 10;
                tv.setText(fromAlpha + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mFromAlpha = (float) seekBar.getProgress() / 10;
                setupAnim(mFromAlpha, mToAlpha);
            }
        });
    }

    private void setupToAlphaView() {
        SeekBar sb = (SeekBar) findViewById(R.id.sb_to_alpha);
        sb.setProgress((int) (mToDefault * 10));
        final TextView tv = (TextView) findViewById(R.id.tv_to_alpha);
        tv.setText(mToDefault + "");
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float toAlpha = (float) i / 10;
                tv.setText(toAlpha + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mToAlpha = (float) seekBar.getProgress() / 10;
                setupAnim(mFromAlpha, mToAlpha);
            }
        });
    }
}
