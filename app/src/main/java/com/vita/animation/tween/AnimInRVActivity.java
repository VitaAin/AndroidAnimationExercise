package com.vita.animation.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vita.animation.R;
import com.vita.animation.itemanimator.FadeItemAnimator;
import com.vita.animation.itemanimator.RotateItemAnimator;
import com.vita.animation.itemanimator.ScaleItemAnimator;
import com.vita.animation.itemanimator.TranslateItemAnimator;

import java.util.ArrayList;
import java.util.List;

public class AnimInRVActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> mDataList = new ArrayList<>();
    private RvAdapter mAdapter;
    private RecyclerView mRv1;
    private TextView mTvItemAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_in_rv);

        initData();

        mTvItemAnim = (TextView) findViewById(R.id.tv_item_anim);
        mTvItemAnim.setText("DefaultItemAnimator");
        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);

        mRv1 = (RecyclerView) findViewById(R.id.rv_1);
        mRv1.setLayoutManager(new LinearLayoutManager(this));

        mRv1.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RvAdapter();
        mRv1.setAdapter(mAdapter);
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mDataList.add("I am item " + i);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                mDataList.add(2, "I am new!!! ");
                mAdapter.notifyItemInserted(2);
                break;
            case R.id.btn_remove:
                mDataList.remove(1);
                mAdapter.notifyItemRemoved(1);
                break;
            case R.id.btn_update:
                mDataList.set(5, "I am update!!! ");
                mAdapter.notifyItemChanged(5);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anim_in_rv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_fade:
                mRv1.setItemAnimator(new FadeItemAnimator());
                mTvItemAnim.setText("FadeItemAnimator");
                break;
            case R.id.menu_item_scale:
                mRv1.setItemAnimator(new ScaleItemAnimator());
                mTvItemAnim.setText("ScaleItemAnimator");
                break;
            case R.id.menu_item_translate:
                mRv1.setItemAnimator(new TranslateItemAnimator());
                mTvItemAnim.setText("TranslateItemAnimator");
                break;
            case R.id.menu_item_rotate:
                mRv1.setItemAnimator(new RotateItemAnimator());
                mTvItemAnim.setText("RotateItemAnimator");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    class RvAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_lv_1, null);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ItemHolder) holder).tv.setText(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class ItemHolder extends RecyclerView.ViewHolder {
            private TextView tv;

            public ItemHolder(View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv_item_1);
            }
        }
    }
}
