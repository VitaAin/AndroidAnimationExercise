package com.vita.animation.instance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vita.animation.R;
import com.vita.animation.animation.TVCloseAnimation;
import com.vita.animation.animation.TremblingAnimation;

public class InstanceActivity extends AppCompatActivity implements View.OnClickListener {

    private View mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance);

        mContainer = findViewById(R.id.container_instance);

        setClickListener();
    }

    private void setClickListener() {
        findViewById(R.id.btn_trembling).setOnClickListener(this);
        findViewById(R.id.btn_tv_close).setOnClickListener(this);

        findViewById(R.id.btn_scatter).setOnClickListener(this);
        findViewById(R.id.btn_circle_around).setOnClickListener(this);
        findViewById(R.id.btn_ball_fall).setOnClickListener(this);

        findViewById(R.id.btn_anim_in_list).setOnClickListener(this);
        findViewById(R.id.btn_anim_in_rv).setOnClickListener(this);

        findViewById(R.id.btn_elegant_leaves).setOnClickListener(this);
        findViewById(R.id.btn_try_bezier).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Class clz = null;
        switch (view.getId()) {
            case R.id.btn_trembling:
                TremblingAnimation tremblingAnim = new TremblingAnimation();
                tremblingAnim.setDuration(800);
                tremblingAnim.setRepeatCount(2);
                mContainer.startAnimation(tremblingAnim);
                break;
            case R.id.btn_tv_close:
                TVCloseAnimation tvCloseAnimation = new TVCloseAnimation();
                mContainer.startAnimation(tvCloseAnimation);
                break;
            case R.id.btn_scatter:
                clz = ScatterActivity.class;
                break;
            case R.id.btn_circle_around:
                clz = CircleAroundActivity.class;
                break;
            case R.id.btn_ball_fall:
                clz = BallFallActivity.class;
                break;
            case R.id.btn_anim_in_list:
                clz = AnimInListActivity.class;
                break;
            case R.id.btn_anim_in_rv:
                clz = AnimInRVActivity.class;
                break;
            case R.id.btn_elegant_leaves:
                clz = FlutterActivity.class;
                break;
            case R.id.btn_try_bezier:
                clz = TryBezierActivity.class;
                break;
            case R.id.btn_jelly_1:
                clz = Jelly1Activity.class;
                break;
            case R.id.btn_flow_point_loading:
                clz = FlowPointLoadingActivity.class;
                break;
            case R.id.btn_sticky_circle:
                clz = StickyCircleActivity.class;
                break;
            case R.id.btn_loop_bead_loading:
                clz = LoopBeadLoadingActivity.class;
                break;
            case R.id.btn_path_text:
                clz = PathTextActivity.class;
                break;
            case R.id.btn_spring_loading:
                clz = SpringLoadingActivity.class;
                break;
            case R.id.btn_vp_effect:
                clz = VPEffectActivity.class;
                break;
        }
        jumpTo(clz);
    }

    private void jumpTo(Class clz) {
        if (clz == null) return;
        startActivity(new Intent(this, clz));
    }
}
