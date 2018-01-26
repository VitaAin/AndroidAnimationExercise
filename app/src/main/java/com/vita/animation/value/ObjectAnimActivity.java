package com.vita.animation.value;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.vita.animation.R;

public class ObjectAnimActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_anim);

        setupObj1();
        setupObj2();
        setupObj3();
        setupObj4();

        setClickListener();
    }

    private void setupObj1() {
        ImageView iv = (ImageView) findViewById(R.id.iv_obj_1);
        ObjectAnimator anim = ObjectAnimator.ofFloat(iv, "rotation", 0, 360);
        anim.setDuration(2000);
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    private void setupObj2() {
        ImageView iv = (ImageView) findViewById(R.id.iv_obj_2);
        ObjectAnimator animX = ObjectAnimator.ofFloat(iv, "translationX", 0, 400);
        animX.setDuration(2000);
        animX.setRepeatMode(ValueAnimator.RESTART);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        animX.setInterpolator(new AnticipateOvershootInterpolator());
        animX.start();
    }

    private void setupObj3() {
        ImageView iv = (ImageView) findViewById(R.id.iv_obj_3);
        ObjectAnimator animX = ObjectAnimator.ofFloat(iv, "translationX", 0, 400);
        animX.setDuration(2000);
        animX.setRepeatMode(ValueAnimator.RESTART);
        animX.setRepeatCount(ValueAnimator.INFINITE);
        animX.setInterpolator(new BounceInterpolator());
        animX.start();
        ObjectAnimator animY = ObjectAnimator.ofFloat(iv, "translationY", 0, 30);
        animY.setDuration(2000);
        animY.setRepeatMode(ValueAnimator.RESTART);
        animY.setRepeatCount(ValueAnimator.INFINITE);
        animY.setInterpolator(new BounceInterpolator());
        animY.start();
    }

    private void setupObj4() {
        ImageView iv = (ImageView) findViewById(R.id.iv_obj_4);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 1, 0.2f, 0.8f, 1);
        alpha.setRepeatMode(ValueAnimator.RESTART);
        alpha.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.2f, 1);
        scaleX.setRepeatMode(ValueAnimator.RESTART);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.2f, 1);
        scaleY.setRepeatMode(ValueAnimator.RESTART);
        scaleY.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator transX = ObjectAnimator.ofFloat(iv, "translationX", 0, 500);
        transX.setRepeatMode(ValueAnimator.RESTART);
        transX.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(alpha, scaleX, scaleY, transX); // 同时播放
//        animSet.playSequentially(alpha, scaleX, scaleY, transX); // 顺序播放
        animSet.setDuration(3000);
        animSet.setInterpolator(new BounceInterpolator());
        animSet.start();
    }

    private void setClickListener() {
        findViewById(R.id.btn_scatter).setOnClickListener(this);
        findViewById(R.id.btn_circle_around).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_scatter:
                clz = ScatterActivity.class;
                break;
            case R.id.btn_circle_around:
                clz = CircleAroundActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
