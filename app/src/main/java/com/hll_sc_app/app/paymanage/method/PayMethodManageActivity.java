package com.hll_sc_app.app.paymanage.method;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.paymanage.PayBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付管理-货到付款设置or在线支付设置
 *
 * @author zhuyingsong
 * @date 2019/8/9
 */
@Route(path = RouterConfig.PAY_MANAGE_METHOD, extras = Constant.LOGIN_EXTRA)
public class PayMethodManageActivity extends BaseLoadActivity implements PayMethodManageContract.IMethodView {
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    @Autowired(name = "parcelable")
    ArrayList<PayBean> mPayList;
    private PayMethodManagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_manage_method);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PayMethodManagePresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    private void initView() {
        if (isOnline()) {
            mRlBottom.setVisibility(View.GONE);
            mTxtTitle.setText("在线支付设置");
        } else {
            mRlBottom.setVisibility(View.VISIBLE);
            mTxtTitle.setText("货到付款设置");
        }
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        PayListAdapter payAdapter = new PayListAdapter(mPayList);
        payAdapter.setOnItemClickListener((adapter, view, position) -> clickItem(adapter, position));
        mRecyclerView.setAdapter(payAdapter);
    }

    /**
     * 在线支付设置 or 货到付款设置
     *
     * @return true-在线支付设置
     */
    private boolean isOnline() {
        boolean isOnline = false;
        if (!CommonUtils.isEmpty(mPayList)) {
            isOnline = TextUtils.equals("1", mPayList.get(0).getPayType());
        }
        return isOnline;
    }

    private void clickItem(BaseQuickAdapter adapter, int position) {
        PayBean payBean = (PayBean) adapter.getItem(position);
        if (payBean != null && payBean.isEnable()) {
            payBean.setSelect(!payBean.isSelect());
            if (!checkSelect()) {
                payBean.setSelect(true);
                showToast("至少有一种结算方式噢");
            } else {
                adapter.notifyItemChanged(position);
            }
        }
    }

    /**
     * 至少有一种结算方式
     *
     * @return false-无结算方式
     */
    private boolean checkSelect() {
        boolean select = false;
        if (!CommonUtils.isEmpty(mPayList)) {
            for (PayBean payBean : mPayList) {
                if (payBean.isSelect() && payBean.isEnable()) {
                    select = true;
                }
            }
        }
        return select;
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toSave();
                break;
            default:
                break;
        }
    }

    /**
     * 保存
     */
    private void toSave() {
        String payType = isOnline() ? "1" : "2";
        List<String> selectList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mPayList)) {
            for (PayBean bean : mPayList) {
                if (bean.isEnable() && bean.isSelect()) {
                    selectList.add(String.valueOf(bean.getId()));
                }
            }
        }
        mPresenter.editPayMethod(payType, TextUtils.join(",", selectList));
    }

    @Override
    public void editSuccess() {
        showToast("修改支付方式列表成功");
        ARouter.getInstance().build(RouterConfig.PAY_MANAGE)
            .setProvider(new LoginInterceptor())
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
            .navigation(this);
    }

    private class PayListAdapter extends BaseQuickAdapter<PayBean, BaseViewHolder> {

        PayListAdapter(@Nullable List<PayBean> data) {
            super(R.layout.item_pay_manage, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PayBean item) {
            helper
                .setText(R.id.txt_payMethodName, item.getPayMethodName())
                .setGone(R.id.img_select, TextUtils.equals(item.getPayType(), "2"));
            GlideImageView imageView = helper.getView(R.id.img_imgPath);
            imageView.setImageURL(item.getImgPath());
            ImageView imgSelect = helper.getView(R.id.img_select);
            if (!item.isEnable()) {
                imgSelect.setEnabled(false);
            } else {
                imgSelect.setEnabled(true);
                imgSelect.setSelected(item.isSelect());
            }
        }
    }
}
