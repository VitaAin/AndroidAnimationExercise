package com.vita.animation.instance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.vita.animation.R;
import com.vita.animation.view.StickyCircleView;

public class StickyCircleActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup mContainerStickyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_circle);

        mContainerStickyView = (ViewGroup) findViewById(R.id.container_sticky_view);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        StickyCircleView stickyCircleView = new StickyCircleView(this);
        stickyCircleView.setLayoutParams(lp);
        mContainerStickyView.addView(stickyCircleView);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_sticky_circle_in_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_sticky_circle_in_list:
                clz = StickyCircleListActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
