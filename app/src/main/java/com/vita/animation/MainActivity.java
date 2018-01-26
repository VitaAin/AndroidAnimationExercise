package com.vita.animation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.value.ObjectAnimActivity;
import com.vita.animation.value.ValueAnimActivity;
import com.vita.animation.other.OtherActivity;
import com.vita.animation.tween.TweenAnimActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_tween_anim).setOnClickListener(this);
        findViewById(R.id.btn_object_anim).setOnClickListener(this);
        findViewById(R.id.btn_value_anim).setOnClickListener(this);
        findViewById(R.id.btn_other).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_tween_anim:
                clz = TweenAnimActivity.class;
                break;
            case R.id.btn_object_anim:
                clz = ObjectAnimActivity.class;
                break;
            case R.id.btn_value_anim:
                clz = ValueAnimActivity.class;
                break;
            case R.id.btn_other:
                clz = OtherActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
