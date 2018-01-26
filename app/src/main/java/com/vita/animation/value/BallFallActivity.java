package com.vita.animation.value;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vita.animation.R;
import com.vita.animation.view.BallView;

public class BallFallActivity extends AppCompatActivity {

    private BallView mBallView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_fall);

        mBallView = (BallView) findViewById(R.id.ball_view);
        mBallView.triggerAnim();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBallView.startAnim();
    }
}
