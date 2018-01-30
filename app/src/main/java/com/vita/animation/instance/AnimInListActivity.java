package com.vita.animation.instance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.vita.animation.R;

import java.util.ArrayList;
import java.util.List;

public class AnimInListActivity extends AppCompatActivity {

    private List<String> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_in_list);

        initData();

        ListView lv1 = (ListView) findViewById(R.id.lv_1);
        // can also used in .xml
        LayoutAnimationController layoutAnimCtrl = new LayoutAnimationController(
                AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        layoutAnimCtrl.setDelay(0.5f);
        layoutAnimCtrl.setOrder(LayoutAnimationController.ORDER_REVERSE);
        lv1.setLayoutAnimation(layoutAnimCtrl);
        lv1.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public Object getItem(int i) {
                return mDataList.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Holder holder = null;
                if (view == null) {
                    view = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_lv_1, null);
                    holder = new Holder(view);
                    view.setTag(holder);
                } else {
                    holder = (Holder) view.getTag();
                }
                holder.tv.setText(mDataList.get(i));
                return view;
            }

            class Holder {
                private TextView tv;

                public Holder(View itemView) {
                    tv = itemView.findViewById(R.id.tv_item_1);
                }
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            mDataList.add("I am item " + i);
        }
    }
}
