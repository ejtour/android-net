package com.hll_sc_app.app.deliverymanage.deliverytype.company.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
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
 * 新增合作物流公司
 *
 * @author zhuyingsong
 * @date 2019/7/27
 */
@Route(path = RouterConfig.DELIVERY_TYPE_COMPANY_ADD, extras = Constant.LOGIN_EXTRA)
public class DeliveryCompanyAddActivity extends BaseLoadActivity implements DeliveryCompanyAddContract.IDeliveryCompanyAddView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<DeliveryCompanyBean> mList;

    private EmptyView mEmptyView;
    private CompanyListAdapter mAdapter;
    private DeliveryCompanyAddPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_company_add);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = DeliveryCompanyAddPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(10)));
        mAdapter = new CompanyListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            DeliveryCompanyBean bean = (DeliveryCompanyBean) adapter.getItem(position);
            if (bean != null && TextUtils.isEmpty(bean.getId())) {
                showInputDialog(bean, position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("暂无第三方物流公司数据").create();
        mRecyclerView.setAdapter(mAdapter);
        showCompanyList(mList);
    }

    private void showInputDialog(DeliveryCompanyBean bean, int position) {
        InputDialog.newBuilder(this)
            .setCancelable(false)
            .setTextTitle("输入合作物流公司名称")
            .setHint("物流公司名称")
            .setText(bean != null ? bean.getDeliveryCompanyName() : "")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    // 输入的名称
                    if (TextUtils.isEmpty(dialog.getInputString())) {
                        showToast("合作物流公司名称不能为空");
                        return;
                    }
                    if (bean != null) {
                        bean.setDeliveryCompanyName(dialog.getInputString());
                        mAdapter.notifyItemChanged(position);
                    }
                }
                dialog.dismiss();
            }, "取消", "确定")
            .create().show();
    }

    @Override
    public void showCompanyList(List<DeliveryCompanyBean> list) {
        if (CommonUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        list.add(new DeliveryCompanyBean());
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        if (mAdapter.getItemCount() != 0) {
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }

    @OnClick({R.id.img_back, R.id.txt_save, R.id.txt_add_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_add_again:
                toAddAgain();
                break;
            case R.id.txt_save:
                mPresenter.addDeliveryCompany(getAddCompanyName());
                break;
            default:
                break;
        }
    }

    private void toAddAgain() {
        mAdapter.addData(new DeliveryCompanyBean());
        mAdapter.notifyDataSetChanged();
        if (mAdapter.getItemCount() != 0) {
            mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }
    }

    private List<String> getAddCompanyName() {
        List<String> listAdd = new ArrayList<>();
        List<DeliveryCompanyBean> list = mAdapter.getData();
        if (!CommonUtils.isEmpty(list)) {
            for (DeliveryCompanyBean bean : list) {
                if (!TextUtils.isEmpty(bean.getDeliveryCompanyName()) && TextUtils.isEmpty(bean.getId())) {
                    listAdd.add(bean.getDeliveryCompanyName());
                }
            }
        }
        return listAdd;
    }

    class CompanyListAdapter extends BaseQuickAdapter<DeliveryCompanyBean, BaseViewHolder> {

        CompanyListAdapter() {
            super(R.layout.item_delivery_company);
        }

        @Override
        protected void convert(BaseViewHolder helper, DeliveryCompanyBean item) {
            helper.setText(R.id.txt_deliveryCompanyName, item.getDeliveryCompanyName())
                .setImageResource(R.id.img_status, R.drawable.bg_blue_dot);
        }
    }
}
