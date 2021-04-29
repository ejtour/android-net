package com.hll_sc_app.app.print.list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.print.add.PrinterAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.print.PrinterBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/22
 */
@Route(path = RouterConfig.PRINTER_LIST)
public class PrinterListActivity extends BaseLoadActivity implements IPrinterListContract.IPrinterListView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    boolean mIsSelect;
    private PrinterListAdapter mAdapter;
    private IPrinterListContract.IPrinterListPresenter mPresenter;
    private PrinterBean mCurBean;
    private boolean mInitial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        mPresenter = new PrinterListPresenter();
        mPresenter.register(this);
        mPresenter.start();
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mIsSelect ? "选择打印机" : "打印机列表");
        mTitleBar.setRightText("添加");
        mTitleBar.setRightBtnClick(v -> PrinterAddActivity.start(this, null));
        mAdapter = new PrinterListAdapter(mIsSelect);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            if (view.getId() == R.id.ipl_remove) {
                removeConfirm();
                return;
            }
            if (mIsSelect) {
                Intent intent = new Intent();
                intent.putExtra("printer", mCurBean);
                setResult(RESULT_OK, intent);
                onBackPressed();
            } else {
                PrinterAddActivity.start(this, mCurBean);
            }
        });
        SimpleDecoration decor = new SimpleDecoration(
                ContextCompat.getColor(this, R.color.color_eeeeee),
                ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(UIUtils.dip2px(10), 0, 0, 0);
        mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mListView.addItemDecoration(decor);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPresenter.start();
        } else if (CommonUtils.isEmpty(mAdapter.getData())) {
            onBackPressed();
        }
    }

    private void removeConfirm() {
        TipsDialog.newBuilder(this)
                .setTitle("解绑打印机")
                .setMessage(String.format("你确定解绑打印机【%s】吗？", mCurBean.getDeviceName()))
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1 && mCurBean != null) {
                        mPresenter.delete(mCurBean.getDeviceID());
                    }
                }, "取消", "确定")
                .create().show();
    }

    @Override
    public void setData(List<PrinterBean> list) {
        mAdapter.setNewData(list);
        if (!mInitial) {
            mInitial = true;
            if (CommonUtils.isEmpty(list)) {
                PrinterAddActivity.start(this, null);
            }
        }
    }
}
