package com.vita.animation.tween;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vita.animation.R;

import java.util.ArrayList;
import java.util.List;

public class AnimInRVActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> mDataList = new ArrayList<>();
    private RvAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_in_rv);

        initData();

        findViewById(R.id.btn_insert).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);

        RecyclerView rv1 = (RecyclerView) findViewById(R.id.rv_1);
        rv1.setLayoutManager(new LinearLayoutManager(this));

        rv1.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new RvAdapter();
        rv1.setAdapter(mAdapter);
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
