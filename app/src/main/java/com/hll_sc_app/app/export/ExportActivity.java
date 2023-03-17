package com.hll_sc_app.app.export;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.dialog.MakeSureDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.export.ExportType;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

@Route(path = RouterConfig.ACTIVITY_EXPORT)
public class ExportActivity extends BaseLoadActivity implements IExportView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    private ExportType mCurType;
    private IExportContract.IExportPresenter mPresenter;
    private ExportAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ExportPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        int space = UIUtils.dip2px(12);
        mListView.setPadding(space, space, space, space);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mTitleBar.setHeaderTitle("快捷导出");
        mAdapter = new ExportAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCurType = mAdapter.getItem(position);
            if (mCurType == null) return;
            showExportDialog();
        });
        mListView.setAdapter(mAdapter);
    }

    private void showExportDialog() {
        new ExportDialog(this, new ExportDialog.InputDialogConfig() {

            @Override
            public String getTitle() {
                return "请选择导出方式";
            }

            @Override
            public void click(BaseDialog dialog, String content, int index) {

                String source = index == 0 ? "shopmall-supplier-pc" : "shopmall-supplier";
                //邮件
                mPresenter.export(mCurType, null, source);
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, email -> mPresenter.export(mCurType, email, "shopmall-supplier"));
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    @Override
    public void exportReportID(String url, IExportView export) {

        if (ExportType.GOODS_TOTAL == mCurType) {
            //非图片格式弹窗打开浏览器
            new MakeSureDialog((Activity) this, () -> {
                //进入附件列表
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
            }).show();
        } else {
            Utils.exportReportID(this,url,export);
        }
    }
}
