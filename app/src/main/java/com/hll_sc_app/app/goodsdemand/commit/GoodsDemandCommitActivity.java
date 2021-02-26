package com.hll_sc_app.app.goodsdemand.commit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.entry.GoodsDemandEntryActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandBean;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandItem;
import com.hll_sc_app.bean.goodsdemand.GoodsDemandReq;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.goodsdemand.GoodsDemandCommitHeader;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/22
 */

@Route(path = RouterConfig.GOODS_DEMAND_COMMIT)
public class GoodsDemandCommitActivity extends BaseLoadActivity implements IGoodsDemandCommitContract.IGoodsDemandCommitView {
    @Autowired(name = "parcelable")
    GoodsDemandReq mReq;
    @BindView(R.id.gdc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.gdc_submit)
    TextView mSubmit;
    @BindView(R.id.gdc_list_view)
    RecyclerView mListView;
    private GoodsDemandCommitAdapter mAdapter;
    private GoodsDemandCommitHeader mHeader;
    private IGoodsDemandCommitContract.IGoodsDemandCommitPresenter mPresenter;
    private int mCurPos;
    private ImageUploadGroup mUploadGroup;

    /**
     * @param purchaserID   采购商id
     * @param purchaserName 采购商名称
     * @param productName   产品名
     * @param productBrief  产品简介
     */
    public static void start(String purchaserID, String purchaserName, String productName, String productBrief) {
        UserBean user = GreenDaoUtils.getUser();
        GoodsDemandReq req = new GoodsDemandReq();
        req.setSupplyID(user.getGroupID());
        req.setSupplyName(user.getGroupName());
        req.setSupplyPhone(user.getLoginPhone());
        req.setPurchaserID(purchaserID);
        req.setPurchaserName(purchaserName);
        req.setProductName(productName);
        req.setProductBrief(productBrief);
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_COMMIT, req);
    }

    public static void start(GoodsDemandBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_DEMAND_COMMIT, bean.covertToReq());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_goods_demand_commit);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
        List<GoodsDemandItem> list = new ArrayList<>();
        list.add(new GoodsDemandItem());
        mAdapter = new GoodsDemandCommitAdapter(list, this::updateEnable);
        mListView.setAdapter(mAdapter);
        mHeader = new GoodsDemandCommitHeader(this);
        mHeader.withReq(mReq);
        mHeader.setOnClickListener(v -> {
            UIUtils.selectPhoto(this);
        });
        mAdapter.setHeaderView(mHeader);
        TextView footer = new TextView(this);
        TextViewCompat.setTextAppearance(footer, R.style.TextAppearance_City22_Middle_Blue);
        footer.setGravity(Gravity.RIGHT);
        int space = UIUtils.dip2px(10);
        footer.setPadding(space, space, space, space);
        footer.setText("+ 再添加一个需求");
        footer.setOnClickListener(v -> {
            mAdapter.addData(new GoodsDemandItem());
            if (mAdapter.getData().size() == 5) mAdapter.removeAllFooterView();
        });
        mAdapter.setFooterView(footer);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurPos = position;
            if (view.getId() == R.id.gdc_type) {
                List<NameValue> nameValues = new ArrayList<>();
                nameValues.add(new NameValue("商品外形", String.valueOf(1)));
                nameValues.add(new NameValue("储存要求", String.valueOf(2)));
                nameValues.add(new NameValue("分拣要求", String.valueOf(3)));
                nameValues.add(new NameValue("配送要求", String.valueOf(4)));
                nameValues.add(new NameValue("其他要求", String.valueOf(5)));
                if (!CommonUtils.isEmpty(mAdapter.getData())) {
                    for (GoodsDemandItem item : mAdapter.getData()) {
                        if (item.getDemandType() != 0) {
                            nameValues.get(item.getDemandType() - 1).setEnable(false);
                        }
                    }
                }
                SingleSelectionDialog.newBuilder(this, NameValue::getName, NameValue::isEnable)
                        .setOnSelectListener(value -> {
                            GoodsDemandItem item = mAdapter.getItem(mCurPos);
                            if (item != null)
                                item.setDemandType(Integer.parseInt(value.getValue()));
                            mAdapter.notifyItemChanged(mCurPos + mAdapter.getHeaderLayoutCount());
                            updateEnable();
                        })
                        .refreshList(nameValues)
                        .setTitleText("选择需求类型")
                        .create().show();
            } else mUploadGroup = ((ImageUploadGroup) view);
        });
        inflateData();
    }

    private void inflateData() {
        if (!TextUtils.isEmpty(mReq.getId())) {
            if (!CommonUtils.isEmpty(mReq.getDemandList())) {
                mAdapter.setNewData(mReq.getDemandList());
                if (mReq.getDemandList().size() == 5) {
                    mAdapter.removeAllFooterView();
                }
            }
            mTitleBar.setHeaderTitle("编辑商品需求");
        }
    }

    private void updateEnable() {
        boolean enable = true;
        for (GoodsDemandItem item : mAdapter.getData()) {
            if (item.getDemandType() > 0 && TextUtils.isEmpty(item.getDemandContent())) {
                enable = false;
                break;
            }
        }
        mSubmit.setEnabled(!CommonUtils.isEmpty(mAdapter.getData()) && enable);
    }

    private void initData() {
        mPresenter = GoodsDemandCommitPresenter.newInstance();
        mPresenter.register(this);
    }

    @OnClick(R.id.gdc_submit)
    public void submit() {
        List<GoodsDemandItem> list = new ArrayList<>();
        List<GoodsDemandItem> rawList = new ArrayList<>(mAdapter.getData());
        if (!CommonUtils.isEmpty(rawList)) {
            for (GoodsDemandItem item : rawList) {
                if (item.getDemandType() > 0) list.add(item);
            }
        }
        mReq.setDemandList(list);
        mPresenter.commit(mReq);
    }

    @Override
    public void success() {
        SuccessDialog.newBuilder(this)
                .setCancelable(false)
                .setMessageTitle("提交成功")
                .setMessage("您的商品需求已经提交给相应的供应商了 请等待供应商回复")
                .setImageTitle(R.drawable.ic_dialog_success)
                .setImageState(R.drawable.ic_dialog_state_success)
                .setButton((dialog, item) -> GoodsDemandEntryActivity.start(), "好，我知道了")
                .create().show();
    }

    @Override
    public void showImg(String url) {
        mHeader.addUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.IMG_SELECT_REQ_CODE && data != null) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
        if (mUploadGroup != null) mUploadGroup.onActivityResult(requestCode, resultCode, data);
    }
}
