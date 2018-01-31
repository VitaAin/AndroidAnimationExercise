package com.vita.animation.instance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vita.animation.R;
import com.vita.animation.view.StickyCircleView;

public class StickyActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup mContainerStickyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);

        mContainerStickyView = (ViewGroup) findViewById(R.id.container_sticky_view);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_sticky_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        switch (view.getId()) {
            case R.id.btn_sticky_1:
                StickyCircleView stickyCircleView = new StickyCircleView(this);
                stickyCircleView.setLayoutParams(lp);
                mContainerStickyView.addView(stickyCircleView);
                break;
        }
    }
}
