package com.vita.animation.value;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vita.animation.R;
import com.vita.animation.view.PointSinView;

public class ValueAnimActivity extends AppCompatActivity {

    private PointSinView mPsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_anim);

        setupPointSinView();
    }

    private void setupPointSinView() {
        mPsv = (PointSinView) findViewById(R.id.psv);
        mPsv.initAnim();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPsv.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPsv.pauseAnim();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPsv.stopAnim();
    }
}
