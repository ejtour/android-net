package com.hll_sc_app.app.complainmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.ordernumberlist.SelectOrderListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 投诉详情-新增
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_ADD)
public class ComplainMangeAddActivity extends BaseLoadActivity implements IComplainMangeAddContract.IView {
    private final int MAX_INPUT_LENGTH = 200;
    private final int REQUEST_CODE_ORDER_NUMBER_LIST = 100;
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.txt_group)
    TextView mTxtGroup;
    @BindView(R.id.txt_shop)
    TextView mTxtShop;
    @BindView(R.id.txt_order)
    TextView mTxtOrder;
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.txt_type)
    TextView mTxtType;
    @BindView(R.id.txt_reason)
    TextView mTxtReason;
    @BindView(R.id.edt_explain)
    EditText mEdtExplain;
    @BindView(R.id.ll_scroll_photo)
    LinearLayout mLlScrollPhoto;
    @BindView(R.id.recyclerView)
    RecyclerView mProductListView;
    @BindView(R.id.group_product_area)
    Group mGroupProductArea;
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @Autowired(name = "parcelable")
    ComplainDetailResp mDetail;
    private Unbinder unbinder;
    private IComplainMangeAddContract.IPresent mPresent;
    private SingleSelectionDialog mSelectTypeDialog;
    private SingleSelectionDialog mSelectReasonDialog;

    public static void start(ComplainDetailResp complainDetailResp) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_ADD, complainDetailResp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_add);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = ComplainMangeAddPresent.newInstance();
        mPresent.register(this);
        mPresent.queryDropMenus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitle.setHeaderTitle(getTitleName());
        initExplainView();
        initDetail();
    }

    private String getTitleName() {
        return mDetail == null ? "新增投诉" : "编辑投诉";
    }

    private void initExplainView() {
        mTxtLeftNumber.setText(String.valueOf(MAX_INPUT_LENGTH));
        mEdtExplain.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(MAX_INPUT_LENGTH)
        });
        mEdtExplain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTxtLeftNumber.setText(String.valueOf(MAX_INPUT_LENGTH - s.toString().length()));
            }
        });
        mEdtExplain.setSelection(mEdtExplain.getText().toString().length());
    }

    private void initDetail() {
        if (mDetail == null) {
            return;
        }
        mTxtGroup.setTag(mDetail.getPurchaserID());
        mTxtGroup.setText(mDetail.getPurchaserName());

        mTxtShop.setTag(mDetail.getPurchaserShopID());
        mTxtShop.setText(mDetail.getPurchaserShopName());

        mTxtOrder.setTag(mDetail.getBillID());
        mTxtOrder.setText(mDetail.getBillID());

        mEdtPhone.setText(mDetail.getPurchaserContact());


        mTxtType.setTag(new DropMenuBean(mDetail.getTypeName(), mDetail.getType()));
        mTxtType.setText(mDetail.getTypeName());

        mTxtReason.setTag(new DropMenuBean(mDetail.getReasonName(), mDetail.getReason()));
        mTxtReason.setText(mDetail.getReasonName());

        mEdtExplain.setText(mDetail.getComplaintExplain());

        initImagesView();
        initProductListView();
    }

    /**
     * 初始化上传凭证
     */
    private void initImagesView() {
        //todo 凭证图片

    }

    /**
     * 初始化商品列表区域
     */
    private void initProductListView() {
        //todo 商品列表

    }

    @OnClick({R.id.ll_add_product, R.id.txt_reason, R.id.txt_type, R.id.txt_order, R.id.txt_group, R.id.txt_shop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_group:

                break;
            case R.id.txt_shop:
                if (mTxtGroup.getTag() == null) {
                    showToast("请选择投诉集团");
                    return;
                }
                break;
            case R.id.txt_type:
                if (mSelectTypeDialog != null) {
                    mSelectTypeDialog.show();
                }
                break;
            case R.id.txt_reason:
                if (mSelectReasonDialog != null) {
                    if (mTxtType.getTag() == null) {
                        showToast("请选择投诉类型");
                        return;
                    }
                    mSelectReasonDialog.show();
                }
                break;
            case R.id.txt_order:
                String subBillNo = mTxtOrder.getTag() == null ? "" : mTxtOrder.getTag().toString();
                /* if (mTxtGroup.getTag() == null) {
                    showToast("请选择投诉集团");
                    return;
                }
                if (mTxtShop.getTag() == null) {
                    showToast("请选择门店");
                    return;
                }
               SelectOrderListActivity.start(this,
                        REQUEST_CODE_ORDER_NUMBER_LIST,
                        subBillNo,
                        mTxtGroup.getTag().toString(),
                        mTxtShop.getTag().toString());*/

                SelectOrderListActivity.start(this,
                        REQUEST_CODE_ORDER_NUMBER_LIST,
                        subBillNo,
                        "4105",
                        "91215");

                break;
            default:
                break;
        }
    }


    @Override
    public void queryMenuSuccess(List<DropMenuBean> dropMenuBeans) {
        mSelectReasonDialog = SingleSelectionDialog.newBuilder(this, DropMenuBean::getValue)
                .setOnSelectListener(dropMenuBean -> {
                    mTxtReason.setTag(dropMenuBean);
                    mTxtReason.setText(dropMenuBean.getValue());
                })
                .setTitleText("选择投诉原因")
                .selectEqualListener((dropMenuBean, m) -> {
                    return TextUtils.equals(dropMenuBean.getKey(), m.getKey());
                })
                .create();

        mSelectTypeDialog = SingleSelectionDialog.newBuilder(this, DropMenuBean::getValue)
                .setOnSelectListener(dropMenuBean -> {
                    mTxtType.setTag(dropMenuBean);
                    mTxtType.setText(dropMenuBean.getValue());
                    mTxtReason.setTag(null);
                    mTxtReason.setText("");
                    mSelectReasonDialog.refreshList(dropMenuBean.getChildren());
                })
                .refreshList(dropMenuBeans)
                .setTitleText("选择投诉类型")
                .selectEqualListener((dropMenuBean, m) -> {
                    return TextUtils.equals(dropMenuBean.getKey(), m.getKey());
                })
                .create();

        mSelectReasonDialog.selectItem(mTxtReason.getTag());
        mSelectTypeDialog.selectItem(mTxtType.getTag());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_ORDER_NUMBER_LIST:
                if (resultCode == RESULT_OK) {
                    OrderResp resp = data.getParcelableExtra("order");
                    mTxtOrder.setTag(resp.getSubBillNo());
                    mTxtOrder.setText(resp.getSubBillNo());
                    mEdtPhone.setText(resp.getReceiverMobile());
                }
                break;
            default:
                break;
        }
    }
}
