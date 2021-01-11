package com.hll_sc_app.app.goodsdemand.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandActivity;
import com.hll_sc_app.app.goodsdemand.add.GoodsDemandAddActivity;
import com.hll_sc_app.app.goodsdemand.entry.GoodsDemandEntryActivity;
import com.hll_sc_app.app.goodsdemand.select.GoodsDemandSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.goodsdemand.GoodsDemandDetailFooter;
import com.hll_sc_app.widget.goodsdemand.GoodsDemandDetailHeader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/23
 */

@Route(path = RouterConfig.GOODS_DEMAND_DETAIL)
public class GoodsDemandDetailActivity extends BaseLoadActivity implements IGoodsDemandDetailContract.IGoodsDemandDetailView {
    @BindView(R.id.gdd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.gdd_cancel)
    TextView mCancel;
    @BindView(R.id.gdd_edit)
    TextView mEdit;
    @BindView(R.id.gdd_reply_customer)
    TextView mReplyCustomer;
    @BindView(R.id.gdd_reply_sale)
    TextView mReplySale;
    @BindView(R.id.gdd_notice)
    TextView mNotice;
    @BindView(R.id.gdd_bottom_bg)
    View mBottomGroupBk;
    @BindView(R.id.gdd_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    GoodsDemandBean mBean;
    @Autowired(name = "object0")
    String mId;
    private boolean mHasChanged;
    private IGoodsDemandDetailContract.IGoodsDemandDetailPresenter mPresenter;

    public static void start(GoodsDemandBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_DETAIL, bean);
    }

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_DETAIL, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_demand_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            GoodsDemandEntryActivity.start();
            return;
        }
        super.onBackPressed();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        inflateData();
    }

    private void inflateData() {
        if (mBean == null) return;
        GoodsDemandDetailAdapter adapter = new GoodsDemandDetailAdapter(mBean.getDemandList());
        mListView.setAdapter(adapter);
        GoodsDemandDetailHeader header = new GoodsDemandDetailHeader(this);
        header.setData(mBean);
        GoodsDemandDetailFooter footer = new GoodsDemandDetailFooter(this);
        footer.setData(mBean);
        adapter.setHeaderView(header);
        adapter.setFooterView(footer);
        if (!UserConfig.crm()) {
            if (mBean.getSource() == 0){//采购商提的
                mBottomGroupBk.setVisibility(View.VISIBLE);
                mReplyCustomer.setVisibility(View.VISIBLE);
            }else if(mBean.getSource() == 1){//crm端提的
                mBottomGroupBk.setVisibility(View.VISIBLE);
                mReplySale.setVisibility(View.VISIBLE);
                mReplyCustomer.setVisibility(View.VISIBLE);
            }else if (mBean.getSource() == 2){ // 供应链提的
                mBottomGroupBk.setVisibility(View.VISIBLE);
                mReplySale.setVisibility(View.VISIBLE);
            }

            if (mBean.getStatus() == 1) {
                mReplySale.setText("回复销售");
                mReplyCustomer.setText("回复客户");
            } else if (mBean.getStatus() == 2) {
                mReplySale.setText("再次回复销售");
                mReplyCustomer.setText("再次回复客户");
                mNotice.setVisibility(View.VISIBLE);
                mNotice.setText("通知客户已上架");
            } else {
                mBottomGroupBk.setVisibility(View.GONE);
                mReplySale.setVisibility(View.GONE);
                mReplyCustomer.setVisibility(View.GONE);
            }
        } else if (mBean.getStatus() == 1) {
            mBottomGroupBk.setVisibility(View.VISIBLE);
            mCancel.setVisibility(View.VISIBLE);
            mEdit.setVisibility(View.VISIBLE);
        } else {
            mBottomGroupBk.setVisibility(View.GONE);
            mCancel.setVisibility(View.GONE);
            mEdit.setVisibility(View.GONE);
        }
    }

    private void initData() {
        mPresenter = GoodsDemandDetailPresenter.newInstance();
        mPresenter.register(this);
        if (mBean == null) mPresenter.start();
    }

    @OnClick(R.id.gdd_notice)
    public void notice() {
        GoodsDemandSelectActivity.start(mBean.getId(), mBean.getPurchaserID());
    }

    @OnClick({R.id.gdd_cancel, R.id.gdd_edit})
    public void action(View view) {
        switch (view.getId()) {
            case R.id.gdd_cancel:
                SuccessDialog.newBuilder(this)
                        .setMessageTitle("确定取消该需求吗？")
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setButton((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresenter.cancel();
                            }
                        }, "我再看看", "确认取消")
                        .create()
                        .show();
                break;
            case R.id.gdd_edit:
                GoodsDemandAddActivity.start(mBean);
                break;
        }
    }

    @OnClick({R.id.gdd_reply_customer, R.id.gdd_reply_sale})
    public void reply(View view) {
        switch (view.getId()) {
            case R.id.gdd_reply_sale:
                RemarkDialog.newBuilder(this)
                        .setButtons("容我再想想", "确认回复", (dialog, positive, content) -> {
                            dialog.dismiss();
                            if (positive) {
                                mPresenter.reply(content, IGoodsDemandDetailContract.TARGET.SALE);
                            }
                        })
                        .setHint("请填写回复说明")
                        .setMaxLength(50)
                        .create()
                        .show();
                break;
            case R.id.gdd_reply_customer:
                RemarkDialog.newBuilder(this)
                        .setButtons("容我再想想", "确认回复", (dialog, positive, content) -> {
                            dialog.dismiss();
                            if (positive) {
                                mPresenter.reply(content, IGoodsDemandDetailContract.TARGET.CUSTOMER);
                            }
                        })
                        .setHint("请填写回复说明")
                        .setMaxLength(50)
                        .create()
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(GoodsDemandBean bean) {
        mBean = bean;
        inflateData();
    }

    @Override
    public void replySuccess() {
        GoodsDemandActivity.start();
    }

    @Override
    public void statusChanged() {
        mHasChanged = true;
        mPresenter.start();
    }

    @Override
    public GoodsDemandBean getBean() {
        return mBean;
    }

    @Override
    public String getID() {
        return mId;
    }
}
