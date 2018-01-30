package com.vita.animation.instance;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.vita.animation.R;

import java.util.ArrayList;
import java.util.List;

public class CircleAroundActivity extends AppCompatActivity {

    private int[] mCircleIds = new int[]{R.id.circle_1,
            R.id.circle_2, R.id.circle_3,
            R.id.circle_4, R.id.circle_5};
    private List<View> mCircles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_around);

        for (int i = 0; i < mCircleIds.length; i++) {
            View v = findViewById(mCircleIds[i]);
            mCircles.add(v);
        }

        findViewById(R.id.btn_start_anim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim();
            }
        });
    }

    private void startAnim() {
//        ObjectAnimator rotation0 = ObjectAnimator.ofFloat(mCircles.get(0),
//                "rotation", 0, 360);
//        rotation0.setDuration(2000);
//        rotation0.setInterpolator(new AccelerateDecelerateInterpolator());
//        ObjectAnimator rotation1 = ObjectAnimator.ofFloat(mCircles.get(1),
//                "rotation", 0, 360);
//        rotation1.setStartDelay(150);
//        rotation1.setDuration(2000 + 150);
//        rotation1.setInterpolator(new AccelerateDecelerateInterpolator());
//        ObjectAnimator rotation2 = ObjectAnimator.ofFloat(mCircles.get(2),
//                "rotation", 0, 360);
//        rotation2.setStartDelay(2 * 150);
//        rotation2.setDuration(2000 + 2 * 150);
//        rotation2.setInterpolator(new AccelerateDecelerateInterpolator());
//        ObjectAnimator rotation3 = ObjectAnimator.ofFloat(mCircles.get(3),
//                "rotation", 0, 360);
//        rotation3.setStartDelay(3 * 150);
//        rotation3.setDuration(2000 + 3 * 150);
//        rotation3.setInterpolator(new AccelerateDecelerateInterpolator());
//        ObjectAnimator rotation4 = ObjectAnimator.ofFloat(mCircles.get(4),
//                "rotation", 0, 360);
//        rotation4.setStartDelay(4 * 150);
//        rotation4.setDuration(2000 + 4 * 150);
//        rotation4.setInterpolator(new AccelerateDecelerateInterpolator());
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.play(rotation0).with(rotation1).with(rotation2)
//                .with(rotation3).with(rotation4);
//        animatorSet.start();


        List<Animator> anims = new ArrayList<>();
        for (int i = 0; i < mCircles.size(); i++) {
            ObjectAnimator rotation = ObjectAnimator.ofFloat(mCircles.get(i),
                    "rotation", 0, 360);
            rotation.setStartDelay(i * 150);
            rotation.setDuration(2000 + i * 150);
            rotation.setInterpolator(new AccelerateDecelerateInterpolator());
            anims.add(rotation);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(anims);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}
