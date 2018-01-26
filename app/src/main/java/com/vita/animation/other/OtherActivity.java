package com.vita.animation.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.R;
import com.vita.animation.animation.TVCloseAnim;
import com.vita.animation.animation.TremblingAnim;

public class OtherActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        mContainer = findViewById(R.id.container_other);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_trembling).setOnClickListener(this);
        findViewById(R.id.btn_tv_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_trembling:
                TremblingAnim tremblingAnim = new TremblingAnim();
                tremblingAnim.setDuration(800);
                tremblingAnim.setRepeatCount(2);
                mContainer.startAnimation(tremblingAnim);
                break;
            case R.id.btn_tv_close:
                TVCloseAnim tvCloseAnim = new TVCloseAnim();
                mContainer.startAnimation(tvCloseAnim);
                break;
        }
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
