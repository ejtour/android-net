package com.hll_sc_app.app.staffmanage.linkshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.staffmanage.salerole.StaffSaleListActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;
import com.hll_sc_app.bean.event.StaffEvent;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.hll_sc_app.citymall.util.CalendarUtils.FORMAT_HH_MM_SS;

/**
 * 员工列表_关联门店
 */
@Route(path = RouterConfig.STAFF_LIST_LINK_SHOP, extras = Constant.LOGIN_EXTRA)
public class StaffLinkShopListActivity extends BaseLoadActivity implements StaffLinkShopListContract.IView {
    private final int REQUEST_TO_SALE_ROLE = 100;
    @BindView(R.id.list_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_link_number)
    TextView mTxtLinkNumber;
    @BindView(R.id.title_bar)
    TitleBar mTitlebar;
    @BindView(R.id.rl_button_bottom)
    RelativeLayout mRlButton;
    @BindView(R.id.txt_person)
    TextView mTxtPerson;
    @BindView(R.id.check_all)
    CheckBox mCheckAll;
    @Autowired(name = "object0")
    String mSalesmanID;

    private StaffLinkShopPresenter mPresenter;
    private StaffShopAdapter mAdapter;

    private ContextOptionsWindow mMenuWindow;

    private Set<String> selectShopIds;

    private Unbinder unbinder;

    private int shopTotalNumber = 0;

    private boolean isCrm = UserConfig.crm();

    public static void start(String salesmanID) {
        RouterUtil.goToActivity(RouterConfig.STAFF_LIST_LINK_SHOP, salesmanID);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_link_shop);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresenter = StaffLinkShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryCooperationShop(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mAdapter = new StaffShopAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            selectSale(mAdapter.getItem(position));
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            selectSale(mAdapter.getItem(position));
        });

        if (isCrm) {
            mTitlebar.setRightBtnVisible(true);
            mTitlebar.setRightBtnClick(v -> {
                if (mRlButton.getVisibility() == View.VISIBLE) {//取消
                    clearSelect();
                } else {
                    if (mMenuWindow == null) {//菜单
                        mMenuWindow = new ContextOptionsWindow(this);
                        List<OptionsBean> list = new ArrayList<>();
                        list.add(new OptionsBean("批量转交其他人", R.drawable.ic_xuelunyan_white));
                        list.add(new OptionsBean("批量转到公海", R.drawable.ic_xuelunyan_white));
                        mMenuWindow.refreshList(list);
                        mMenuWindow.setListener((adapter, view, position) -> {
                            mMenuWindow.dismiss();
                            if (position == 0) {
                                mTxtPerson.setVisibility(View.VISIBLE);
                                mTitlebar.setHeaderTitle("批量转交其他人");
                            } else if (position == 1) {
                                mTxtPerson.setVisibility(View.GONE);
                                mTitlebar.setHeaderTitle("批量转到公海");
                            }
                            mTitlebar.setRightText("取消");
                            mAdapter.toggleModal(true);
                            mRlButton.setVisibility(View.VISIBLE);

                        });
                    }
                    mMenuWindow.showAsDropDownFix(mTitlebar, Gravity.END);
                }
            });
        } else {
            mTitlebar.setRightBtnVisible(true);
            mTitlebar.setRightText("批量转交");
            mTitlebar.setRightBtnClick(v -> {
                if (mRlButton.getVisibility() == View.VISIBLE) {//取消
                    clearSelect();
                } else {//批量转交
                    mAdapter.toggleModal(true);
                    mRlButton.setVisibility(View.VISIBLE);
                }
            });
        }

        mCheckAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                List<PurchaserShopBean> shopBeans = mAdapter.getData();
                for (PurchaserShopBean shopBean : shopBeans) {
                    selectShopIds.add(shopBean.getShopID());
                }
            } else {
                selectShopIds.clear();
            }
            mAdapter.notifyDataSetChanged();
        });
    }

    private void selectSale(PurchaserShopBean shopBean) {
        if(!mAdapter.isShowCheck){
            return;
        }
        if (selectShopIds == null) {
            selectShopIds = new HashSet<>();
        }
        if (selectShopIds.contains(shopBean.getShopID())) {
            selectShopIds.remove(shopBean.getShopID());
        } else {
            selectShopIds.add(shopBean.getShopID());
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 取消操作或操作成功
     */
    private void clearSelect() {
        if (selectShopIds != null) {
            selectShopIds.clear();
        }
        mRlButton.setVisibility(View.GONE);
        mTitlebar.setHeaderTitle("关联门店");
        mAdapter.toggleModal(false);
        mTxtPerson.setText("选择接收人");
        mTxtPerson.setTag(null);
        if (isCrm) {
            mTitlebar.setRightText("");
        } else {
            mTitlebar.setRightText("批量转交");
        }
        mTitlebar.setRightBtnVisible(true);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TO_SALE_ROLE && data != null) {
            EmployeeBean bean = data.getParcelableExtra("employ");
            if (bean != null) {
                mTxtPerson.setText("接收人：" + bean.getEmployeeName());
                mTxtPerson.setTag(bean);
            }
        }
    }

    @OnClick({R.id.txt_confirm, R.id.txt_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_confirm:
                if (isCrm) {
                    if (mTxtPerson.getVisibility() == View.VISIBLE) {
                        EmployeeBean employeeBean = (EmployeeBean) mTxtPerson.getTag();
                        if (employeeBean == null) {
                            showToast("请选择接收人");
                        } else {
                            ShopSettlementReq req = new ShopSettlementReq();
                            req.setEmployeeID(employeeBean.getEmployeeID());
                            req.setEmployeeName(employeeBean.getEmployeeName());
                            req.setEmployeePhone(employeeBean.getLoginPhone());
                            req.setShopIDs(TextUtils.join(",", selectShopIds));
                            mPresenter.editShopEmployee(req);
                        }
                    } else {
                        mPresenter.dropStaffEmployee();
                    }
                } else {

                }
                break;
            case R.id.txt_person:
                EmployeeBean employeeBean = (EmployeeBean) mTxtPerson.getTag();
                String employeeId = "";
                if (employeeBean != null) {
                    employeeId = employeeBean.getEmployeeID();
                }
                StaffSaleListActivity.start(this, REQUEST_TO_SALE_ROLE, employeeId);
                break;
            default:
                break;
        }
    }

    public class StaffShopAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
        private boolean isShowCheck = false;

        StaffShopAdapter() {
            super(R.layout.list_item_staff_link_shop);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.check_item);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean shopBean) {
            helper.setText(R.id.txt_shop_name, shopBean.getShopName())
                    .setText(R.id.txt_group_name, shopBean.getPurchaserName())
                    .setText(R.id.txt_link, "联系人: " + shopBean.getShopAdmin() + "/" + shopBean.getShopPhone())
                    .setText(R.id.txt_addr, "地址: " + shopBean.getShopProvince() + shopBean.getShopCity() + shopBean.getShopAddress())
                    .setText(R.id.txt_time, CalendarUtils.getDateFormatString(shopBean.getManageTime(), FORMAT_HH_MM_SS, "yyyy/MM/dd") + " 关联")
                    .setVisible(R.id.check_item, isShowCheck);
            ((GlideImageView) helper.getView(R.id.shop_img)).setImageURL(shopBean.getImagePath());
            ((CheckBox) helper.getView(R.id.check_item)).setChecked(selectShopIds != null && selectShopIds.contains(shopBean.getShopID()));
        }

        public void toggleModal(boolean isShowCheck) {
            this.isShowCheck = isShowCheck;
            notifyDataSetChanged();
        }
    }

    @Override
    public void dropSuccess() {
        EventBus.getDefault().post(new StaffEvent(shopTotalNumber - selectShopIds.size(), true));
        clearSelect();
        mPresenter.refresh();
    }

    @Override
    public void showShops(CooperationShopListResp resp, boolean isMore) {
        if (isMore) {
            mAdapter.addData(resp.getShopList());
        } else {
            shopTotalNumber = resp.getShopTotal();
            mTxtLinkNumber.setText("当前已关联门店数: " + shopTotalNumber);
            mAdapter.setNewData(resp.getShopList());
            if (CommonUtils.isEmpty(resp.getShopList())) {
                mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("没有关联门店").create());
            }
        }
        if (!CommonUtils.isEmpty(resp.getShopList())) {
            mRefreshLayout.setEnableLoadMore(resp.getShopList().size() == mPresenter.getPageSize());
        }
    }

    @Override
    public String getSalesmanID() {
        return mSalesmanID;
    }

    @Override
    public List<String> getShopIds() {
        return new ArrayList<>(selectShopIds);
    }

    @Override
    public void editSuccess() {
        dropSuccess();
    }


}
