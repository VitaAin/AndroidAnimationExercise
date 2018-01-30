package com.vita.animation.instance;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.vita.animation.R;

import java.util.ArrayList;
import java.util.List;

public class ScatterActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] mScatterIvIds = new int[]{R.id.iv_scatter_center,
            R.id.iv_scatter_1, R.id.iv_scatter_2,
            R.id.iv_scatter_3, R.id.iv_scatter_4};
    private List<ImageView> mScatterIvs = new ArrayList<>();
    private boolean flag = false;// 打开、关闭的标记位，true为开，false为关

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter);

        for (int i = 0; i < mScatterIvIds.length; i++) {
            ImageView iv = (ImageView) findViewById(mScatterIvIds[i]);
            iv.setOnClickListener(this);
            mScatterIvs.add(iv);
        }
    }

    private void startOpenAnim() {
        ObjectAnimator alpha0 = ObjectAnimator.ofFloat(mScatterIvs.get(0),
                "alpha", 1, 0.5f);
        ObjectAnimator rotation0 = ObjectAnimator.ofFloat(mScatterIvs.get(0),
                "rotation", 0, 90);
        ObjectAnimator transY1 = ObjectAnimator.ofFloat(mScatterIvs.get(1),
                "translationY", 200);
        ObjectAnimator transX2 = ObjectAnimator.ofFloat(mScatterIvs.get(2),
                "translationX", 200);
        ObjectAnimator transY3 = ObjectAnimator.ofFloat(mScatterIvs.get(3),
                "translationY", -200);
        ObjectAnimator transX4 = ObjectAnimator.ofFloat(mScatterIvs.get(4),
                "translationX", -200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.playTogether(alpha0, rotation0,
                transY1, transX2, transY3, transX4);
        animatorSet.start();
        flag = true;
    }

    private void startCloseAnim() {
        ObjectAnimator alpha0 = ObjectAnimator.ofFloat(mScatterIvs.get(0),
                "alpha", 0.5f, 1);
        ObjectAnimator rotation0 = ObjectAnimator.ofFloat(mScatterIvs.get(0),
                "rotation", 90, 0);
        ObjectAnimator transY1 = ObjectAnimator.ofFloat(mScatterIvs.get(1),
                "translationY", 200, 0);
        ObjectAnimator transX2 = ObjectAnimator.ofFloat(mScatterIvs.get(2),
                "translationX", 200, 0);
        ObjectAnimator transY3 = ObjectAnimator.ofFloat(mScatterIvs.get(3),
                "translationY", -200, 0);
        ObjectAnimator transX4 = ObjectAnimator.ofFloat(mScatterIvs.get(4),
                "translationX", -200, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.playTogether(alpha0, rotation0,
                transY1, transX2, transY3, transX4);
        animatorSet.start();
        flag = false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_scatter_center:
                if (flag) {
                    startCloseAnim();
                } else {
                    startOpenAnim();
                }
                break;
        }
    }
}
