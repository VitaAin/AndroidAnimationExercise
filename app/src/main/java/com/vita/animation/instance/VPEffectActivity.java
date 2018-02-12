package com.vita.animation.instance;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vita.animation.R;
import com.vita.animation.pagetransformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class VPEffectActivity extends AppCompatActivity {

    private List<Integer> mImages;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp_effect);

        initData();

        mVp = (ViewPager) findViewById(R.id.vp);
        mVp.setAdapter(new VpAdapter());
    }

    private void initData() {
        mImages = new ArrayList<>();
        mImages.add(R.drawable.ic_shopping_01);
        mImages.add(R.drawable.ic_shopping_02);
        mImages.add(R.drawable.ic_shopping_03);
        mImages.add(R.drawable.ic_shopping_04);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vp_effect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewPager.PageTransformer transformer = null;
        switch (item.getItemId()) {
            case R.id.menu_item_normal:
                break;
            case R.id.menu_item_zoom:
                transformer = new DepthPageTransformer();
                break;
            case R.id.menu_item_cube:
                break;
            case R.id.menu_item_rotate:
                break;
        }
        if (transformer != null) {
            mVp.setPageTransformer(false, transformer);
        }
        return super.onOptionsItemSelected(item);
    }

    private class VpAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImages == null ? 0 : mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mImages.size();
            View view = LayoutInflater.from(VPEffectActivity.this).inflate(R.layout.item_vp, null);
            ((ImageView) view.findViewById(R.id.iv_item_vp)).setImageResource(mImages.get(position));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
