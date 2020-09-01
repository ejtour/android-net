package com.hll_sc_app.app.goods.assign.detail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.select.product.goodsassign.SelectProductActivity;
import com.hll_sc_app.app.select.shop.goodsassign.SelectShopActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.bean.goods.GoodsAssignDetailBean;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
@Route(path = RouterConfig.GOODS_ASSIGN_DETAIL)
public class GoodsAssignDetailActivity extends BaseLoadActivity implements IGoodsAssignDetailContract.IGoodsAssignDetailView {
    @BindView(R.id.gad_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.gad_copy)
    TextView mCopy;
    @BindView(R.id.gad_list_view)
    RecyclerView mListView;
    @BindView(R.id.gad_image)
    GlideImageView mImageView;
    @BindView(R.id.gad_name)
    TextView mName;
    @BindView(R.id.gad_label)
    TextView mLabel;
    @BindView(R.id.gad_bottom_bar)
    FrameLayout mBottomBar;
    @BindView(R.id.gad_add)
    TextView mAdd;
    @BindView(R.id.gad_tip)
    TextView mShopNum;
    @Autowired(name = "parcelable", required = true)
    GoodsAssignBean mBean;
    private IGoodsAssignDetailContract.IGoodsAssignDetailPresenter mPresenter;
    private GoodsAssignDetailAdapter mAdapter;

    public static void start(GoodsAssignBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_ASSIGN_DETAIL, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_goods_assign_detail);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
        mPresenter = GoodsAssignDetailPresenter.newInstance(mBean.getId(), mBean.getPurchaserID());
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initData() {
        mTitleBar.setHeaderTitle(mBean.getAssignType().getTitle());
        mAdd.setText(mBean.getAssignType().getDetailAddLabel());
        mCopy.setVisibility(!TextUtils.isEmpty(mBean.getId()) && mBean.getAssignType().isCanCopy() ? View.VISIBLE : View.GONE);
        mLabel.setText(mBean.getAssignType().getDetailLabel());
        mName.setText(mBean.getPurchaserName());
        mShopNum.setText(String.format("已选择%s个门店", mBean.getPurchaserShopIDs().split(",").length));
        mAdapter.setNewData(mBean.getProductList());
        updateBottomBar();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::save);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(64), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new GoodsAssignDetailAdapter() {
            @Override
            public void remove(int position) {
                super.remove(position);
                updateBottomBar();
            }
        };
        mAdapter.setEmptyView(getEmptyView());
        mListView.setAdapter(mAdapter);
    }

    private EmptyView getEmptyView() {
        return EmptyView.newBuilder(this)
                .setImage(R.drawable.ic_goods_assign_detail_empty)
                .setTips(mBean.getAssignType().getDetailAddTip())
                .setTipsButton(mBean.getAssignType().getDetailAddLabel())
                .setOnClickListener(new EmptyView.OnActionClickListener() {
                    @Override
                    public void retry() {
                        // no-op
                    }

                    @Override
                    public void action() {
                        toSelect();
                    }
                }).create();
    }

    @OnClick(R.id.gad_add)
    void toSelect() {
        SelectProductActivity.start(mBean);
    }

    @OnClick(R.id.gad_copy)
    void toCopy() {
        mBean.setId("");
        GoodsAssignDetailActivity.start(mBean);
        finish();
    }

    @OnClick(R.id.gad_info)
    void clickInfo() {
        if (TextUtils.isEmpty(mBean.getId()) && App.equalToLastActivity(SelectShopActivity.class.getSimpleName(), 1)) {
            Intent intent = new Intent();
            intent.putExtra("parcelable", mBean);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            RouterUtil.goToActivity(RouterConfig.SELECT_GROUP_GOODS_ASSIGN, mBean);
        }
    }

    void save(View view) {
        if (CommonUtils.isEmpty(mBean.getProductList())) {
            showToast("商品不能为空");
        } else {
            mPresenter.save(mBean);
        }
    }

    @Override
    public void setList(List<GoodsAssignDetailBean> list) {
        mBean.setProductList(list);
        mAdapter.setNewData(list);
        updateBottomBar();
    }

    private void updateBottomBar() {
        mBottomBar.setVisibility(CommonUtils.isEmpty(mAdapter.getData()) ? View.GONE : View.VISIBLE);
    }

    @Override
    public void cacheGroupUrl(String url) {
        mImageView.setImageURL(url);
    }

    @Override
    public void saveSuccess() {
        showToast("保存成功");
        mBean.setProductNum(mBean.getProductList().size());
        mBean.setProductList(null);
        RouterUtil.goToActivity(RouterConfig.GOODS_ASSIGN, mBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleListEvent(SingleListEvent<GoodsAssignDetailBean> event) {
        if (event.getClazz() == GoodsAssignDetailBean.class) {
            setList(event.getList());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mBean = intent.getParcelableExtra("parcelable");
        initData();
    }
}
