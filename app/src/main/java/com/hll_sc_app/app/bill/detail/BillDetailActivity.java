package com.hll_sc_app.app.bill.detail;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.bill.log.BillLogActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.bill.BillBean;
import com.hll_sc_app.bean.bill.BillStatus;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.bill.BillConfirmDialog;
import com.hll_sc_app.widget.bill.BillDetailHeader;
import com.hll_sc_app.widget.bill.ModifyAmountDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.ViewCollections;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.BILL_DETAIL)
public class BillDetailActivity extends BaseLoadActivity implements IBillDetailContract.IBillDetailView {
    public static final int REQ_CODE = 0x766;
    @BindView(R.id.abd_list_view)
    RecyclerView mListView;
    @BindView(R.id.abd_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.abd_objection_tip)
    TextView mObjectionTip;
    @BindViews({R.id.abd_bottom_bg, R.id.abd_action_btn, R.id.abd_modify_btn})
    List<View> mActionGroup;
    @BindView(R.id.abd_modify_btn)
    TextView mModify;
    @Autowired(name = "parcelable", required = true)
    BillBean mCurBean;
    private BillDetailAdapter mAdapter;
    private BillDetailHeader mHeader;
    private IBillDetailContract.IBillDetailPresenter mPresenter;
    private boolean hasChanged;
    private ModifyAmountDialog mAmountDialog;
    private BillConfirmDialog mConfirmDialog;

    public static void start(Activity activity, BillBean bean) {
        RouterUtil.goToActivity(RouterConfig.BILL_DETAIL, activity, REQ_CODE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
        updateData();
    }

    @Override
    public void onBackPressed() {
        if (hasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    private void initData() {
        mPresenter = BillDetailPresenter.newInstance(mCurBean.getId());
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mAdapter = new BillDetailAdapter();
        mHeader = new BillDetailHeader(this);
        mHeader.setOnClickListener(v -> BillLogActivity.start(mCurBean));
        mAdapter.setHeaderView(mHeader);
        mListView.setAdapter(mAdapter);
    }

    @OnClick(R.id.abd_action_btn)
    public void doAction() {
        if (mConfirmDialog == null) {
            mConfirmDialog = new BillConfirmDialog(this, v -> {
                mConfirmDialog.dismiss();
                mPresenter.doAction();
            });
        }
        mConfirmDialog.withData(mCurBean).show();
    }

    @Override
    public void updateData(BillBean bean) {
        mCurBean = bean;
        updateData();
    }

    @OnClick(R.id.abd_objection_tip)
    public void seeLog() {
        BillLogActivity.start(mCurBean);
    }

    @OnClick(R.id.abd_modify_btn)
    public void showModifyDialog() {
        if (mAmountDialog == null) {
            mAmountDialog = (ModifyAmountDialog) new ModifyAmountDialog(this)
                    .setModifyCallback(mPresenter::modifyAmount);
        }
        mAmountDialog.setRawPrice(mCurBean.getTotalAmount());
        mAmountDialog.show();
    }

    @Override
    public void actionSuccess() {
        hasChanged = true;
        mPresenter.start();
    }

    private void updateData() {
        mObjectionTip.setVisibility(TextUtils.isEmpty(mCurBean.getObjection()) ? View.GONE : View.VISIBLE);
        mHeader.setData(mCurBean);
        mAdapter.setNewData(mCurBean.getRecords());
        if (isShowConfirmButton(mCurBean)) {
            ViewCollections.run(mActionGroup, (view, index) -> view.setVisibility(View.VISIBLE));
            mModify.setVisibility(mCurBean.getIsConfirm() == 2 ? View.GONE : View.VISIBLE);
        } else {
            ViewCollections.run(mActionGroup, (view, index) -> view.setVisibility(View.GONE));
        }
    }


    /**
     * @return 是否显示结算按钮
     */
    private boolean isShowConfirmButton(BillBean billBean) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null || "1".equals(userBean.getCurRole()) || billBean.getSettlementStatus() == BillStatus.SETTLED) {
            return false;
        }
        if (billBean.getBillStatementFlag() == 0) { // 自营对账单 显示
            return true;
        }
        // 如果是自营，开通代仓是代仓角色，未开通代仓既不是货主，也不是代仓， 如果不是自营，则是货主身份
        if (TextUtils.equals("true", userBean.getSelfOperated())) {
            if (userBean.getWareHourseStatus() == 1) { // 代仓角色
                return (billBean.getBillStatementFlag() == 1 || billBean.getBillStatementFlag() == 2) && billBean.getPayee() == 0; // 代仓对账单，代仓收款
            } else { // 其他角色
                return false;
            }
        } else { // 货主角色
            return billBean.getBillStatementFlag() == 2 && billBean.getPayee() == 1; // 供应商代仓对账单，货主收款
        }
    }
}
