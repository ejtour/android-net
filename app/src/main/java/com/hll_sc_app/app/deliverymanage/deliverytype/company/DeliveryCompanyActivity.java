package com.hll_sc_app.app.deliverymanage.deliverytype.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryCompanyBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择第三方物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
@Route(path = RouterConfig.DELIVERY_TYPE_COMPANY, extras = Constant.LOGIN_EXTRA)
public class DeliveryCompanyActivity extends BaseLoadActivity implements DeliveryCompanyContract.IDeliveryCompanyView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<DeliveryCompanyBean> mList;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    private DeliveryCompanyPresenter mPresenter;
    private CompanyListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_company);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryCompanyPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new CompanyListAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeliveryCompanyBean bean = (DeliveryCompanyBean) adapter.getItem(position);
            if (bean != null) {
                bean.setStatus(TextUtils.equals(bean.getStatus(), "1") ? "0" : "1");
                adapter.notifyItemChanged(position);
                checkAllSelect();
            }
        });
        EmptyView emptyView = EmptyView.newBuilder(this).setTips("暂无第三方物流公司数据").create();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(emptyView);
        checkAllSelect();
    }

    private void checkAllSelect() {
        List<DeliveryCompanyBean> list = mAdapter.getData();
        boolean select = true;
        if (!CommonUtils.isEmpty(list)) {
            for (DeliveryCompanyBean bean : list) {
                if (TextUtils.equals(bean.getStatus(), "0")) {
                    select = false;
                    break;
                }
            }
        }
        mImgAllCheck.setSelected(select);
    }

    @OnClick({R.id.img_back, R.id.txt_add, R.id.img_allCheck, R.id.txt_allCheck, R.id.txt_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_add:
                break;
            case R.id.txt_allCheck:
            case R.id.img_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                selectAll(mImgAllCheck.isSelected());
                break;
            case R.id.txt_commit:
                mPresenter.editDeliveryCompanyStatus(getDeliveryCompanyIds());
                break;
            default:
                break;
        }
    }

    private void selectAll(boolean select) {
        String status = select ? "1" : "0";
        List<DeliveryCompanyBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (DeliveryCompanyBean bean : list) {
                bean.setStatus(status);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<String> getDeliveryCompanyIds() {
        List<String> listSelect = new ArrayList<>();
        List<DeliveryCompanyBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (DeliveryCompanyBean bean : list) {
                if (TextUtils.equals(bean.getStatus(), "1")) {
                    listSelect.add(bean.getId());
                }
            }
        }
        return listSelect;
    }

    @Override
    public void showCompanyList(List<DeliveryCompanyBean> list) {

    }

    @Override
    public void editSuccess() {
        showToast("修改三方配送公司状态成功");
        ARouter.getInstance().build(RouterConfig.DELIVERY_TYPE_SET)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    class CompanyListAdapter extends BaseQuickAdapter<DeliveryCompanyBean, BaseViewHolder> {

        CompanyListAdapter(@Nullable List<DeliveryCompanyBean> data) {
            super(R.layout.item_delivery_company, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryCompanyBean item) {
            helper.setText(R.id.txt_deliveryCompanyName, item.getDeliveryCompanyName())
                .getView(R.id.img_status).setSelected(TextUtils.equals(item.getStatus(), "1"));
        }
    }
}
