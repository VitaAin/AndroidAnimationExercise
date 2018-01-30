package com.vita.animation.instance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.R;
import com.vita.animation.view.FlutterView;

public class FlutterActivity extends AppCompatActivity implements View.OnClickListener {

    private FlutterView mFlutterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegant_leaves);

        findViewById(R.id.btn_add_item).setOnClickListener(this);

        mFlutterView = (FlutterView) findViewById(R.id.flutter_view);
        mFlutterView.setItemWidth(100);
        mFlutterView.setItemHeight(100);
        mFlutterView.setAuto(true, 0.1f);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_item:
                mFlutterView.addItem();
                break;
        }
    }
}
