package com.vita.animation.instance;

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

public class StickyCircleListActivity extends AppCompatActivity {

    private List<String> mDataList = new ArrayList<>();
    private RecyclerView mRvStickyCircleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_circle_list);

        initData();

        mRvStickyCircleList = (RecyclerView) findViewById(R.id.rv_sticky_circle_list);
        mRvStickyCircleList.setItemAnimator(new DefaultItemAnimator());
        mRvStickyCircleList.setLayoutManager(new LinearLayoutManager(this));
        mRvStickyCircleList.setAdapter(new StickyCircleRvAdapter());
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mDataList.add("I am item " + i);
        }
    }

    private class StickyCircleRvAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_sticky_circle, null);
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
                tv = itemView.findViewById(R.id.tv_item);
            }
        }
    }
}
