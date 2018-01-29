package com.vita.animation.tween;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.R;
import com.vita.animation.tween.AlphaActivity;
import com.vita.animation.tween.RotateActivity;
import com.vita.animation.tween.ScaleActivity;
import com.vita.animation.tween.TranslateActivity;

public class TweenAnimActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween_anim);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_translate).setOnClickListener(this);
        findViewById(R.id.btn_rotate).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_alpha:
                clz = AlphaActivity.class;
                break;
            case R.id.btn_scale:
                clz = ScaleActivity.class;
                break;
            case R.id.btn_translate:
                clz = TranslateActivity.class;
                break;
            case R.id.btn_rotate:
                clz = RotateActivity.class;
                break;
            case R.id.btn_anim_in_list:
                clz = AnimInListActivity.class;
                break;
            case R.id.btn_anim_in_rv:
                clz = AnimInRVActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
