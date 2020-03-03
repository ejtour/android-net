package com.hll_sc_app.app.mall;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.mall.PrivateMallBean;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ShareDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.mall.PrivateMallFooter;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/27
 */
@Route(path = RouterConfig.PRIVATE_MALL)
public class PrivateMallActivity extends BaseLoadActivity implements IPrivateMallContract.IPrivateMallView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    private PrivateMallAdapter mAdapter;
    private ShareDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mDialog != null) mDialog.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null) mDialog.release();
    }

    private void initData() {
        PrivateMallPresenter presenter = new PrivateMallPresenter();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        int _10dp = UIUtils.dip2px(10);
        mListView.setPadding(_10dp, _10dp, _10dp, 0);
        mTitleBar.setHeaderTitle("私有商城");
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, _10dp));
        mAdapter = new PrivateMallAdapter();
        mAdapter.setFooterView(new PrivateMallFooter(this));
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getTag() == null) return;
            switch (view.getId()) {
                case R.id.ipm_copy:
                    ClipboardManager cm = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cm == null) {
                        return;
                    }
                    ClipData clipData = ClipData.newPlainText("链接", view.getTag().toString());
                    cm.setPrimaryClip(clipData);
                    showToast("复制成功");
                    break;
                case R.id.ipm_share:
                    share((View) view.getTag());
                    break;
            }
        });
    }

    @Override
    public void setData(List<PrivateMallBean> list) {
        mAdapter.setNewData(list);
    }

    private void share(View view) {
        File cacheDir = getExternalCacheDir();
        if (cacheDir == null) return;
        String imagePath = cacheDir.getAbsolutePath() + "/qr_code_22city.png";
        Utils.saveViewToFile(view, imagePath);
        if (mDialog == null) {
            mDialog = new ShareDialog(this);
            mDialog.setData(ShareDialog.ShareParam.createImageShareParam("分享二维码", imagePath));
        }
        mDialog.show();
    }
}
