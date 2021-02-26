package com.hll_sc_app.base.utils.router;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hll_sc_app.base.BaseActivity;

/**
 * 外部跳转统一处理
 *
 * @author zhuyingsong
 * @date 2018/12/17.
 */
public class SchemeFilterActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        if (uri != null) {
            RouterUtil.goToActivity(uri.getPath(), this);
        } else {
            finish();
        }
    }
}
