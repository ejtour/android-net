package com.hll_sc_app.app.complainmanage.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hll_sc_app.app.complainmanage.add.productlist.SelectProductListActivity;
import com.hll_sc_app.app.complainmanage.ordernumberlist.SelectOrderListActivity;
import com.hll_sc_app.app.complainmanage.purchaserlist.SelectPurchaserListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.complain.ComplainDetailResp;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;
import com.hll_sc_app.bean.event.ComplainManageEvent;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
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
    private final int REQUEST_SELECT_PURCHASER_LIST = 101;
    private final int REQUEST_SELECT_SHOP_LIST = 102;
    private final int REQUEST_SELECT_PRODUCT_LIST = 103;
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
    @BindView(R.id.txt_left_number)
    TextView mTxtLeftNumber;
    @BindView(R.id.txt_product_edit)
    TextView mTxtProductEdt;
    @Autowired(name = "parcelable")
    ComplainDetailResp mDetail;
    @BindView(R.id.upload_img)
    ImgUploadBlock mUpload;
    @BindView(R.id.ll_add_product)
    LinearLayout mLlAddProduct;
    @BindView(R.id.txt_title_product)
    TextView mTxtTitleAddProduct;


    private Unbinder unbinder;
    private IComplainMangeAddContract.IPresent mPresent;
    private SingleSelectionDialog mSelectTypeDialog;
    private SingleSelectionDialog mSelectReasonDialog;
    private ProductAdapter mProductListAdapter;

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
        mTitle.setRightBtnClick(v -> {
            if (checkInput()) {
                mPresent.saveComplain();
            }
        });
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

    @OnClick({R.id.ll_add_product, R.id.txt_reason, R.id.txt_type, R.id.txt_order, R.id.txt_group,
            R.id.txt_shop, R.id.txt_product_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_group:
                String purchaserId = mTxtGroup.getTag() == null ? "" : mTxtGroup.getTag().toString();
                SelectPurchaserListActivity.start(SelectPurchaserListActivity.TYPE.GROUP, this, REQUEST_SELECT_PURCHASER_LIST, purchaserId, purchaserId);
                break;
            case R.id.txt_shop:
                if (mTxtGroup.getTag() == null) {
                    showToast("请选择投诉集团");
                    return;
                }
                purchaserId = mTxtGroup.getTag().toString();
                String shopId = mTxtShop.getTag() == null ? "" : mTxtShop.getTag().toString();
                SelectPurchaserListActivity.start(SelectPurchaserListActivity.TYPE.SHOP, this, REQUEST_SELECT_SHOP_LIST, purchaserId, shopId);
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
                if (mTxtGroup.getTag() == null) {
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
                       mTxtShop.getTag().toString());
                break;
            case R.id.ll_add_product:
                if (mTxtOrder.getTag() == null) {
                    showToast("请先选择订单");
                    return;
                }
                SelectProductListActivity.start(this, REQUEST_SELECT_PRODUCT_LIST, mTxtOrder.getTag().toString(), null);
                break;
            case R.id.txt_product_edit:
                SelectProductListActivity.start(this, REQUEST_SELECT_PRODUCT_LIST, mTxtOrder.getTag().toString(), (ArrayList<OrderDetailBean>) mProductListAdapter.getData());
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
                    /*选择商品问题*/
                    toggleSelectProductView(TextUtils.equals("1", dropMenuBean.getKey()));
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
    public void showImage(String url) {
        addImgUrlDetail(url);
    }

    private void addImgUrlDetail(String url) {
        if (mLlScrollPhoto.getTag() == null) {
            mLlScrollPhoto.setTag(new ArrayList<String>());
        }
        ArrayList<String> urls = (ArrayList<String>) mLlScrollPhoto.getTag();
        urls.add(url);
        ImgShowDelBlock block = new ImgShowDelBlock(this);
        block.setImgUrl(url);
        block.setDeleteListener(v -> {
            urls.remove(block.getImageUrl());
            mLlScrollPhoto.removeView(block);
            mUpload.setSubTitle(String.format("%s/%s", mLlScrollPhoto.getChildCount() - 1, 4));
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(60),
                UIUtils.dip2px(60));
        params.rightMargin = UIUtils.dip2px(10);

        int curChildCount = mLlScrollPhoto.getChildCount();
        mLlScrollPhoto.addView(block, curChildCount - 1, params);
        mUpload.setSubTitle(String.format("%s/%s", mLlScrollPhoto.getChildCount() - 1, 4));
        mUpload.setVisibility(curChildCount == 4 ? View.GONE : View.VISIBLE);

    }

    /**
     * 展示和隐藏选择投诉商品区域
     *
     * @param isShow
     */
    private void toggleSelectProductView(boolean isShow) {
        boolean isHasProduct = mProductListAdapter != null && mProductListAdapter.getData().size() > 0;
        mTxtProductEdt.setVisibility(isShow && isHasProduct ? View.VISIBLE : View.GONE);
        mTxtTitleAddProduct.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mProductListView.setVisibility(isShow && isHasProduct ? View.VISIBLE : View.GONE);
        mLlAddProduct.setVisibility(isShow && !isHasProduct ? View.VISIBLE : View.GONE);
    }

    @Override
    public String getExplain() {
        return mEdtExplain.getText().toString();
    }

    @Override
    public String getImgs() {
        Object o = mLlScrollPhoto.getTag();
        if (o == null) {
            return "";
        }
        ArrayList urls = (ArrayList) o;
        return TextUtils.join(",", urls);
    }

    @Override
    public String getType() {
        Object object = mTxtType.getTag();
        if (object == null) {
            return "";
        } else {
            DropMenuBean bean = (DropMenuBean) object;
            return bean.getKey();
        }
    }

    @Override
    public String getReason() {
        Object object = mTxtReason.getTag();
        if (object == null) {
            return "";
        } else {
            DropMenuBean bean = (DropMenuBean) object;
            return bean.getKey();
        }
    }

    @Override
    public String getBillID() {
        return mTxtOrder.getText().toString();
    }

    @Override
    public String getPhone() {
        return mEdtPhone.getText().toString();
    }

    @Override
    public String getPurchaserID() {
        Object object = mTxtGroup.getTag();
        return object == null ? "" : object.toString();
    }

    @Override
    public String getPurchaserName() {
        return mTxtGroup.getText().toString();
    }

    @Override
    public String getShopID() {
        Object object = mTxtShop.getTag();
        return object == null ? "" : object.toString();
    }

    @Override
    public String getShopName() {
        return mTxtShop.getText().toString();
    }

    @Override
    public List<OrderDetailBean> getProducts() {
        if (mProductListAdapter == null) {
            return null;
        }
        return mProductListAdapter.getData();
    }

    @Override
    public void saveSuccess() {
        EventBus.getDefault().post(new ComplainManageEvent(ComplainManageEvent.TARGET.LIST, ComplainManageEvent.EVENT.REFRESH));
        finish();
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
            case REQUEST_SELECT_PURCHASER_LIST:
                if (resultCode == RESULT_OK) {
                    ReportFormSearchResp.ShopMallBean shopMallBean = data.getParcelableExtra("bean");
                    mTxtGroup.setTag(shopMallBean.getShopmallID());
                    mTxtGroup.setText(shopMallBean.getName());
                    mTxtShop.setTag(null);
                    mTxtShop.setText("");
                }
                break;

            case REQUEST_SELECT_SHOP_LIST:
                if (resultCode == RESULT_OK) {
                    ReportFormSearchResp.ShopMallBean shopMallBean = data.getParcelableExtra("bean");
                    mTxtShop.setTag(shopMallBean.getShopmallID());
                    mTxtShop.setText(shopMallBean.getName());
                }
                break;
            case ImgUploadBlock.REQUEST_CODE_CHOOSE:
                if (resultCode == RESULT_OK) {
                    List<String> list = Matisse.obtainPathResult(data);
                    if (!CommonUtils.isEmpty(list)) {
                        mPresent.imageUpload(new File(list.get(0)));
                    }
                }
                break;
            case REQUEST_SELECT_PRODUCT_LIST:
                if (resultCode == RESULT_OK) {
                    List<OrderDetailBean> orderDetailBeans = data.getParcelableArrayListExtra("product");
                    if (mProductListAdapter == null) {
                        mProductListAdapter = new ProductAdapter(orderDetailBeans, ProductAdapter.TYPE.EDIT, null);
                        mProductListAdapter.setOnItemClickListener((adapter, view, position) -> {
                            mProductListAdapter.remove(position);
                            toggleSelectProductView(true);
                        });
                        mProductListView.setAdapter(mProductListAdapter);
                    } else {
                        mProductListAdapter.setNewData(orderDetailBeans);
                    }
                    toggleSelectProductView(true);
                }
                break;
            default:
                break;
        }
    }

    private boolean checkInput() {
        //商品类型投诉
        if (TextUtils.equals(getType(), "1")) {
            if (getProducts().size() == 0) {
                showToast("请选择投诉商品商品");
                return false;
            }
        }
        if (TextUtils.isEmpty(getPurchaserName())) {
            showToast("请选择投诉集团");
            return false;
        } else if (TextUtils.isEmpty(getShopName())) {
            showToast("请选择投诉集团");
            return false;
        } else if (TextUtils.isEmpty(getBillID())) {
            showToast("请选择订单");
            return false;
        } else if (TextUtils.isEmpty(getPhone())) {
            showToast("请填写联系方式");
            return false;
        } else if (TextUtils.isEmpty(getType())) {
            showToast("请选择投诉类型");
            return false;
        } else if (TextUtils.isEmpty(getReason())) {
            showToast("请选择投诉原因");
            return false;
        } else if (TextUtils.isEmpty(getExplain())) {
            showToast("请填写投诉说明");
            return false;
        } /*else if (TextUtils.isEmpty(getImgs())) {
            showToast("请选择上传凭证");
            return false;
        }*/
        return true;
    }
}
