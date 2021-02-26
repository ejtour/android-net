package com.hll_sc_app.app.staffmanage.linkshop;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.hll_sc_app.bean.event.RefreshStaffShopEvent;
import com.hll_sc_app.bean.event.StaffEvent;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresenter = StaffLinkShopPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.queryCooperationShop(true);
    }

    @Subscribe
    public void onEvent(RefreshStaffShopEvent event) {
        if (event.getShopNum() == -1 && mPresenter != null) {
            mPresenter.refresh();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
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
                        list.add(new OptionsBean(R.drawable.ic_xuelunyan_white, OptionType.OPTION_HAND_OVER_OTHERS));
                        list.add(new OptionsBean(R.drawable.ic_xuelunyan_white, OptionType.OPTION_HAND_OVER_SEA));
                        mMenuWindow.refreshList(list);
                        mMenuWindow.setListener((adapter, view, position) -> {
                            mMenuWindow.dismiss();
                            if (position == 0) {
                                mTxtPerson.setVisibility(View.VISIBLE);
                                mTitlebar.setHeaderTitle(OptionType.OPTION_HAND_OVER_OTHERS);
                            } else if (position == 1) {
                                mTxtPerson.setVisibility(View.GONE);
                                mTitlebar.setHeaderTitle(OptionType.OPTION_HAND_OVER_SEA);
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
                    mTitlebar.setRightText("取消");
                    mAdapter.toggleModal(true);
                    mRlButton.setVisibility(View.VISIBLE);
                    mAdapter.toggleModal(true);
                }
            });
        }

        mCheckAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (selectShopIds == null) {
                selectShopIds = new HashSet<>();
            }
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

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
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
            mCheckAll.setChecked(false);
        } else {
            selectShopIds.add(shopBean.getShopID());
            if (selectShopIds.size() == mAdapter.getData().size()) {
                mCheckAll.setChecked(true);
            }
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
        super.onActivityResult(requestCode, resultCode, data);
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
                if (selectShopIds == null || selectShopIds.size() == 0) {
                    showToast("请选择需要转移的门店");
                    return;
                }
                if (isCrm) {
                    if (mTxtPerson.getVisibility() == View.VISIBLE) {
                        transformToOther();
                    } else {
                        mPresenter.dropStaffEmployee();
                    }
                } else {
                    transformToOther();
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

    /**
     * 转移其他人
     */
    private void transformToOther() {
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
    }

    @Override
    public void showShops(CooperationShopListResp resp, boolean isMore) {
        if (isMore) {
            if (!CommonUtils.isEmpty(resp.getShopList())) {
                mAdapter.addData(resp.getShopList());
            }
        } else {
            shopTotalNumber = resp.getShopTotal();
            mTxtLinkNumber.setText("当前已关联门店数: " + shopTotalNumber);
            mAdapter.setNewData(resp.getShopList());
            if (CommonUtils.isEmpty(resp.getShopList())) {
                SpannableString ss = new SpannableString("该销售下未关联门店，请在合作客户下指派");
                if (!isCrm) {
                    ss.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                        }
                    }, 12, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle(ss).create());
            }
            mTitlebar.setRightBtnVisible(shopTotalNumber > 0);
            EventBus.getDefault().post(new RefreshStaffShopEvent(mSalesmanID, shopTotalNumber));
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
    public void dropSuccess() {
        syncStatus();
        EventBus.getDefault().post(new StaffEvent(
                shopTotalNumber - selectShopIds.size(), true));
    }

    @Override
    public void editSuccess() {
        EmployeeBean employeeBean = (EmployeeBean) mTxtPerson.getTag();
        if (employeeBean != null && !TextUtils.equals(employeeBean.getEmployeeID(), mSalesmanID)) {
            //选择转移的人和当前的用户一样，则不用改变数值
            EventBus.getDefault().post(new StaffEvent(shopTotalNumber - selectShopIds.size(), true));
        }
        syncStatus();
    }

    private void syncStatus() {
        mPresenter.refresh();
        showToast("转移成功");
        clearSelect();
    }
}
