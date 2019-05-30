package com.hll_sc_app.base.utils.router;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 外部跳转统一处理
 *
 * @author zhuyingsong
 * @date 2018/12/17.
 */
public class SchemeFilterActivity extends AppCompatActivity {
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
