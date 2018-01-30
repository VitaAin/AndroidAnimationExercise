package com.vita.animation.instance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vita.animation.R;
import com.vita.animation.view.Bezier1View;

public class TryBezierActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup mContainerBezier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try_bezier);

        mContainerBezier = (ViewGroup) findViewById(R.id.container_bezier);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_bezier_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_bezier_1:
                Bezier1View bezier1 = (Bezier1View) findViewById(R.id.view_bezier_1);
                bezier1.setVisibility(View.VISIBLE);
                bezier1.addCircle();
                break;
        }
//        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
