package com.hll_sc_app.app.order.reject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.details.OrderDetailAdapter;
import com.hll_sc_app.app.submit.BackType;
import com.hll_sc_app.app.submit.SubmitSuccessActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */
@Route(path = RouterConfig.ORDER_REJECT)
public class OrderRejectActivity extends BaseLoadActivity implements IOrderRejectContract.IOrderRejectView {

    public static void start(OrderResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_REJECT, resp);
    }

    @BindView(R.id.aor_submit)
    TextView mSubmit;
    @BindView(R.id.aor_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable", required = true)
    OrderResp mResp;
    SingleSelectionDialog mRejectOptionsDialog;
    private int mRejectReason;
    private IOrderRejectContract.IOrderRejectPresenter mPresenter;
    /**
     * 编辑拒收说明
     */
    private EditText mRejectExplainEdit;
    private ImageUploadGroup mUploadImgGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_reject);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = OrderRejectPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        preProcessOrder();
        OrderDetailAdapter adapter = new OrderDetailAdapter(mResp.getBillDetailList(), OrderDetailAdapter.REJECT_TEXT);
        mListView.setAdapter(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.view_order_reject_header, mListView, false);
        header.findViewById(R.id.orh_reject_option).setOnClickListener(this::showRejectDialog);
        View footer = LayoutInflater.from(this).inflate(R.layout.view_order_reject_footer, mListView, false);
        adapter.addHeaderView(header);
        adapter.addFooterView(footer);
        mRejectExplainEdit = header.findViewById(R.id.orh_edit_reject_desc);
        mUploadImgGroup = header.findViewById(R.id.orh_upload_group);
        mUploadImgGroup.register(this);
        // 拒收时金额数量统一显示为0
        ((TextView) footer.findViewById(R.id.orf_money)).setText(String.format("¥%s", CommonUtils.formatMoney(0)));
    }

    /**
     * 拒收数量传0
     */
    private void preProcessOrder() {
        for (OrderDetailBean bean : mResp.getBillDetailList()) {
            bean.setInspectionNum(0);
            if (CommonUtils.isEmpty(bean.getDepositList())) continue;
            for (OrderDepositBean depositBean : bean.getDepositList()) {
                depositBean.setProductNum(0);
            }
        }
    }

    @OnClick(R.id.aor_submit)
    public void onViewClicked() {
        mPresenter.rejectOrder(mRejectReason,
                mRejectExplainEdit.getText().toString(),
                TextUtils.join(",", mUploadImgGroup.getUploadImgUrls()), mResp);
    }

    @Override
    public void rejectSuccess() {
        SubmitSuccessActivity.start("订单拒收提交成功", BackType.ORDER_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUploadImgGroup.onActivityResult(requestCode, resultCode, data);
    }

    private void showRejectDialog(View v) {
        if (mRejectOptionsDialog == null) { // 初始化拒收选项弹窗
            List<NameValue> values = new ArrayList<>();
            String[] items = {"与供应商协商拒收", "商品质量问题", "商品与描述不符", "不想要了", "其他"};
            for (int i = 0; i < items.length; i++) {
                NameValue e = new NameValue(items[i], String.valueOf(i + 1));
                values.add(e);
            }
            mRejectOptionsDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .setTitleText("拒收原因")
                    .refreshList(values)
                    .setOnSelectListener(nameValue -> {
                        mRejectReason = Integer.parseInt(nameValue.getValue());
                        ((TextView) v).setText(nameValue.getName());
                        mSubmit.setEnabled(true);
                    })
                    .create();
        }
        mRejectOptionsDialog.show();
    }
}
