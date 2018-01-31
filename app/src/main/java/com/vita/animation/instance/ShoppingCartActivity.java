package com.vita.animation.instance;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vita.animation.R;
import com.vita.animation.evaluator.SecondOrderBezierEvaluator;
import com.vita.animation.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity {

    private static final String TAG = "ShoppingCartActivity";
    private List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        initData();

        RecyclerView rvCart = (RecyclerView) findViewById(R.id.rv_cart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setItemAnimator(new DefaultItemAnimator());
        rvCart.setAdapter(new CartRvAdapter());
    }

    private void initData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDataList.add("Item " + i);
        }
    }

    private void addItemToCart(CartRvAdapter.Holder holder, int position) {
        int[] plusPos = new int[2];
        holder.ivPlus.getLocationOnScreen(plusPos);
        Log.d(TAG, "addItemToCart: plusPos = [" + plusPos[0] + ", " + plusPos[1] + "]");

        int[] cartPos = new int[2];
        ImageView ivCart = (ImageView) findViewById(R.id.iv_cart);
        ivCart.getLocationOnScreen(cartPos);
        Log.d(TAG, "addItemToCart: cartPos = [" + cartPos[0] + ", " + cartPos[1] + "]");
        int[] cartSize = new int[2];
        getCartSize(ivCart, cartSize);
        Log.d(TAG, "addItemToCart: cartSize:: " + cartSize[0] + ", " + cartSize[1]);

        final ImageView good = new ImageView(this);
        good.setImageResource(R.drawable.ic_favorite_red_24dp);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        good.setLayoutParams(lp);
        good.setX(plusPos[0]);
        good.setY(plusPos[1]);
        final ViewGroup containerCart = (ViewGroup) findViewById(R.id.container_cart);
        containerCart.addView(good);

        // TODO calculate the ctrlPoint, here is inaccurately
        PointF startPoint = new PointF(plusPos[0], plusPos[1]);
        PointF endPoint = new PointF(cartPos[0] + cartSize[0] / 2, cartPos[1] - cartSize[1] * 2 / 3);
        PointF ctrlPoint = new PointF(0, ScreenUtils.getScreenHeight(getApplicationContext()) / 3);
        Log.d(TAG, "addItemToCart: startPoint = [" + startPoint.x + ", " + startPoint.y + "]");
        Log.d(TAG, "addItemToCart: endPoint = [" + endPoint.x + ", " + endPoint.y + "]");
        Log.d(TAG, "addItemToCart: ctrlPoint = [" + ctrlPoint.x + ", " + ctrlPoint.y + "]");

        SecondOrderBezierEvaluator evaluator = new SecondOrderBezierEvaluator(ctrlPoint);
        ValueAnimator anim = ValueAnimator.ofObject(evaluator, startPoint, endPoint);
        anim.setDuration(1500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setTarget(good);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF pointF = (PointF) valueAnimator.getAnimatedValue();
                good.setX(pointF.x);
                good.setY(pointF.y);
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                good.setAlpha(0f);
                containerCart.removeView(good);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();
    }

    private void getCartSize(ImageView ivCart, @Size(2) int[] cartSize) {
        // 0-width, 1-height
        cartSize[0] = ivCart.getLayoutParams().width;
        cartSize[1] = ivCart.getLayoutParams().height;
        if (cartSize[0] < 0) {
            cartSize[0] = ivCart.getDrawable().getIntrinsicWidth();
        }
        if (cartSize[1] < 0) {
            cartSize[1] = ivCart.getDrawable().getIntrinsicHeight();
        }
    }

    class CartRvAdapter extends RecyclerView.Adapter<CartRvAdapter.Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_cart_rv, null);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(final Holder holder, final int position) {
            holder.tvItemName.setText(mDataList.get(position));
            holder.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addItemToCart(holder, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class Holder extends RecyclerView.ViewHolder {
            private TextView tvItemName;
            private ImageView ivMinus, ivPlus;

            public Holder(View itemView) {
                super(itemView);
                tvItemName = itemView.findViewById(R.id.tv_item_name);
                ivMinus = itemView.findViewById(R.id.iv_minus);
                ivPlus = itemView.findViewById(R.id.iv_plus);
            }
        }
    }
}