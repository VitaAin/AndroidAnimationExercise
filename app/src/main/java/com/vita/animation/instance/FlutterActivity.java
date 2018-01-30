package com.vita.animation.instance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.R;
import com.vita.animation.view.FlutterView;

public class FlutterActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegant_leaves);

        FlutterView flutterView = (FlutterView) findViewById(R.id.flutter_view);
        flutterView.setItemWidth(100);
        flutterView.setItemHeight(100);
        flutterView.setAuto(true, 0.1f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
