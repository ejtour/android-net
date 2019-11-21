package com.hll_sc_app.app.goodsdemand.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.GoodsDemandActivity;
import com.hll_sc_app.app.goodsdemand.select.GoodsDemandSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.widget.RemarkDialog;
import com.hll_sc_app.widget.SimpleDecoration;
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
    @BindView(R.id.gdd_reply_custome)
    TextView mReplyCustome;
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
    private IGoodsDemandDetailContract.IGoodsDemandDetailPresenter mPresenter;

    public static void start(GoodsDemandBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_goods_demand_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
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
                mReplyCustome.setVisibility(View.VISIBLE);
            }else if(mBean.getSource() == 1){//crm端提的
                mBottomGroupBk.setVisibility(View.VISIBLE);
                mReplySale.setVisibility(View.VISIBLE);
                mReplyCustome.setVisibility(View.VISIBLE);
            }

            if (mBean.getStatus() == 1) {
                mReplySale.setText("回复销售");
                mReplyCustome.setText("回复客户");
            } else if (mBean.getStatus() == 2) {
                mReplySale.setText("再次回复销售");
                mReplyCustome.setText("再次回复客户");
                mNotice.setVisibility(View.VISIBLE);
                mNotice.setText("通知客户已上架");
            }else {
                mBottomGroupBk.setVisibility(View.GONE);
                mReplySale.setVisibility(View.GONE);
                mReplyCustome.setVisibility(View.GONE);
            }
        }
    }



    private void initData() {
        mPresenter = GoodsDemandDetailPresenter.newInstance(mBean);
        mPresenter.register(this);
    }


    @OnClick(R.id.gdd_notice)
    public void notice() {
        GoodsDemandSelectActivity.start(mBean.getId(), mBean.getPurchaserID());
    }

    @OnClick({R.id.gdd_reply_custome, R.id.gdd_reply_sale})
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
            case R.id.gdd_reply_custome:
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
    public void replySuccess() {
        GoodsDemandActivity.start();
    }
}
