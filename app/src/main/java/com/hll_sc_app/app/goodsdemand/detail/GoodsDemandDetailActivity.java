package com.hll_sc_app.app.goodsdemand.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
    @BindView(R.id.gdd_positive)
    TextView mPositive;
    @BindView(R.id.gdd_negative)
    TextView mNegative;
    @BindView(R.id.gdd_bottom_group)
    Group mBottomGroup;
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
            if (mBean.getStatus() == 1) {
                mBottomGroup.setVisibility(View.VISIBLE);
                mPositive.setText("回复采购商");
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mPositive.getLayoutParams();
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                layoutParams.width = UIUtils.dip2px(230);
            } else if (mBean.getStatus() == 2) {
                mBottomGroup.setVisibility(View.VISIBLE);
                mPositive.setText("通知采购商已上架商品");
                mNegative.setVisibility(View.VISIBLE);
                mNegative.setText("再次回复采购商");
            }
        }
    }

    private void initData() {
        mPresenter = GoodsDemandDetailPresenter.newInstance(mBean);
        mPresenter.register(this);
    }

    @OnClick(R.id.gdd_positive)
    public void positive() {
        if (mBean.getStatus() == 1) {
            negative();
        } else {
            showToast("选择商品待添加");
        }
    }

    @OnClick(R.id.gdd_negative)
    public void negative() {
        RemarkDialog.newBuilder(this)
                .setButtons("容我再想想", "确认回复", (dialog, positive, content) -> {
                    dialog.dismiss();
                    if (positive) mPresenter.reply(content);
                })
                .setHint("请填写回复说明")
                .setMaxLength(50)
                .create()
                .show();
    }

    @Override
    public void replySuccess() {
        GoodsDemandActivity.start();
    }
}
