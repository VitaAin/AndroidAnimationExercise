package com.vita.animation.instance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vita.animation.R;
import com.vita.animation.view.Bezier1View;
import com.vita.animation.view.Bezier2View;
import com.vita.animation.view.WaveView;

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
        findViewById(R.id.btn_bezier_2).setOnClickListener(this);
        findViewById(R.id.btn_wave_view).setOnClickListener(this);
        findViewById(R.id.btn_shopping_cart).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_bezier_1:
                Bezier1View bezier1 = (Bezier1View) findViewById(R.id.view_bezier_1);
                bezier1.setVisibility(View.VISIBLE);
                findViewById(R.id.view_bezier_2).setVisibility(View.GONE);
                findViewById(R.id.view_wave).setVisibility(View.GONE);
                bezier1.addCircle();
                break;
            case R.id.btn_bezier_2:
                Bezier2View bezier2 = (Bezier2View) findViewById(R.id.view_bezier_2);
                bezier2.setVisibility(View.VISIBLE);
                findViewById(R.id.view_bezier_1).setVisibility(View.GONE);
                findViewById(R.id.view_wave).setVisibility(View.GONE);
                break;
            case R.id.btn_wave_view:
                WaveView waveView = (WaveView) findViewById(R.id.view_wave);
                waveView.setVisibility(View.VISIBLE);
                findViewById(R.id.view_bezier_1).setVisibility(View.GONE);
                findViewById(R.id.view_bezier_2).setVisibility(View.GONE);
                break;
            case R.id.btn_shopping_cart:
                clz = ShoppingCartActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
