package com.hll_sc_app.base.utils.glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.R;
import com.hll_sc_app.base.widget.PinchImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览页面
 *
 * @author zc
 */
public class ImageViewActivity extends BaseLoadActivity {
    private TextView mInfo;
    private ViewPager mViewPager;
    private ArrayList<String> urls;
    private String url;
    private List<PinchImageView> pinchImageViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_image_view);
        StatusBarCompat.setStatusBarColor(this, 0xFF000000);
        mInfo = findViewById(R.id.img_info);
        mViewPager = findViewById(R.id.view_pager);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        urls = intent.getStringArrayListExtra("urls");
        if (urls == null) {
            urls = new ArrayList<>();
            urls.add(url);
        }
        showTitle();
        showImgs();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showTitle() {
        if (urls != null) {
            int index = urls.indexOf(url);
            if (index > -1) {
                mInfo.setText(String.format("%s/%s", index + 1, urls.size()));
            }
        } else {
            mInfo.setText("图片预览");
        }
    }

    private void showImgs() {
        pinchImageViews = new ArrayList<>();
        for (String url : urls) {
            PinchImageView pinchImageView = new PinchImageView(this);
            pinchImageView.setCenterInside(true);
            pinchImageView.setImageURL(url, false);
            pinchImageView.setOnClickListener(v -> {
                finish();
                overridePendingTransition(R.anim.base_slide_in_alpha, R.anim.base_slide_out_alpha);
            });
            pinchImageViews.add(pinchImageView);
        }
        mViewPager.setAdapter(new PagerAdapter());
        mViewPager.setCurrentItem(urls.indexOf(url));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mInfo.setText(String.format("%s/%s", position + 1, urls.size()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter {
        @Override
        public int getCount() {
            return urls == null ? 0 : urls.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(pinchImageViews.get(position));
            return pinchImageViews.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(pinchImageViews.get(position));
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    }
}
