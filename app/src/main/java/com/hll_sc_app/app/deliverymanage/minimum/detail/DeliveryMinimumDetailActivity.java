package com.hll_sc_app.app.deliverymanage.minimum.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.GirdSimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 起送金额详情
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
@Route(path = RouterConfig.DELIVERY_MINIMUM_DETAIL, extras = Constant.LOGIN_EXTRA)
public class DeliveryMinimumDetailActivity extends BaseLoadActivity implements DeliveryMinimumDetailContract.IDeliveryMinimumDetailView {
    public static final String TYPE_AREA = "0";
    public static final Pattern PRICE = Pattern.compile("^[0-9]{1,6}([.]{1}[0-9]{0,2})?$");
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable")
    DeliveryMinimumBean mBean;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.edt_divideName)
    EditText mEdtDivideName;
    @BindView(R.id.edt_sendPrice)
    EditText mEdtSendPrice;
    @BindView(R.id.txt_settings)
    TextView mTxtSettings;

    private MinimumListAdapter mAdapter;
    private SingleSelectionDialog mDialog;
    private DeliveryMinimumDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_minimum_detail);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        mPresenter = DeliveryMinimumDetailPresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new GirdSimpleDecoration(4));
        mAdapter = new MinimumListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProvinceListBean bean = (ProvinceListBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mTxtTitle.setText(isAdd() ? "新增起订金额" : "编辑起订金额");
        if (mBean != null) {
            mEdtDivideName.setText(mBean.getDivideName());
            mEdtSendPrice.setText(CommonUtils.formatNumber(mBean.getSendPrice()));
            mTxtSettings.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            mTxtSettings.setTag(mBean.getSettings());
            mTxtSettings.setText(isAreaType() ? "根据地区设置" : "根据采购商设置");
            mPresenter.queryDeliveryMinimumDetail();
        } else {
            mTxtSettings.setTag(TYPE_AREA);
            mTxtSettings.setText("根据地区设置");
            showType();
        }
        mEdtSendPrice.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
            if (s.toString().startsWith(".")) {
                s.insert(0, TYPE_AREA);
            }
            if (!PRICE.matcher(s.toString()).find() && s.length() > 1) {
                s.delete(s.length() - 1, s.length());
                showToast("起订金额7位数以内");
            }
        });
    }

    private boolean isAdd() {
        return mBean == null;
    }

    private boolean isAreaType() {
        boolean flag = false;
        if (mTxtSettings.getTag() != null) {
            flag = TextUtils.equals(TYPE_AREA, (CharSequence) mTxtSettings.getTag());
        }
        return flag;
    }

    private void showType() {
        if (isAreaType()) {
            // 地区选择
            mPresenter.processAreaData(null);
        } else {
            // 采购商选择
        }
    }

    @OnClick({R.id.img_close, R.id.txt_save, R.id.ll_settings, R.id.txt_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            case R.id.ll_settings:
            case R.id.txt_settings:
                if (isAdd()) {
                    showSettingDialog();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 保存
     */
    private void toSave() {
        if (TextUtils.isEmpty(mEdtDivideName.getText().toString().trim())) {
            showToast("分组名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(mEdtSendPrice.getText().toString().trim())) {
            showToast("起订金额不能为空");
            return;
        }
        if (mTxtSettings.getTag() == null) {
            showToast("选择类型不能为空");
            return;
        }
        DeliveryMinimumReq req = new DeliveryMinimumReq();
        req.setAmount(mEdtSendPrice.getText().toString().trim());
        req.setName(mEdtDivideName.getText().toString().trim());
        if (mTxtSettings.getTag() != null) {
            req.setSettings((String) mTxtSettings.getTag());
        }
        req.setCodeList(getCodeList());
        if (isAdd()) {
            req.setSupplyID(UserConfig.getGroupID());
            req.setType("1");
        } else {
            req.setSendAmountID(mBean.getSendAmountID());
            req.setSupplyID(mBean.getSupplyID());
            req.setSupplyShopID(mBean.getSupplyShopID());
            req.setType("2");
        }
        mPresenter.editDeliveryMinimum(req);
    }

    private void showSettingDialog() {
        if (mDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("根据地区设置", TYPE_AREA));
            list.add(new NameValue("根据采购商设置", "1"));
            mDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .setTitleText("选择类型")
                .refreshList(list)
                .setOnSelectListener(nameValue -> {
                    if (!TextUtils.equals(mTxtSettings.getText(), nameValue.getName())) {
                        mTxtSettings.setText(nameValue.getName());
                        mTxtSettings.setTag(nameValue.getValue());
                        showType();
                    }
                })
                .create();
        }
        mDialog.show();
    }

    private List<String> getCodeList() {
        List<String> codeList = new ArrayList<>();
        List<ProvinceListBean> provinceListBeans = mAdapter.getData();
        if (!CommonUtils.isEmpty(provinceListBeans) && isAreaType()) {
            for (ProvinceListBean bean : provinceListBeans) {
                if (bean.isSelect()) {
                    codeList.addAll(bean.getCodeList());
                }
            }
        }
        return codeList;
    }

    @Override
    public void showAreaList(List<ProvinceListBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public DeliveryMinimumBean getDeliveryMinimumBean() {
        return mBean;
    }

    @Override
    public Activity getContext() {
        return this;
    }

    @Override
    public void editSuccess() {
        if (isAdd()) {
            showToast("新增起订金额成功");
        } else {
            showToast("编辑起订金额成功");
        }
        ARouter.getInstance().build(RouterConfig.DELIVERY_MINIMUM)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    class MinimumListAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        MinimumListAdapter() {
            super(R.layout.item_delivery_minimum_detail_area);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            int selectedNum = getSelectedNum(item);
            helper.setText(R.id.txt_provinceName, item.getProvinceName())
                .setText(R.id.txt_selectedNum, "已选" + selectedNum)
                .setText(R.id.txt_optionalNum, "可选" + (item.getAllNum() - selectedNum))
                .getView(R.id.content).setSelected(item.isSelect());
        }

        private int getSelectedNum(ProvinceListBean item) {
            int num = 0;
            if (!CommonUtils.isEmpty(item.getCodeList())) {
                num = item.getCodeList().size();
            }
            return num;
        }
    }
}
