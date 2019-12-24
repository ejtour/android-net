package com.hll_sc_app.app.stockmanage.storehousemanage.edit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 仓库管理新增编辑页面
 */
@Route(path = RouterConfig.ACTIVITY_STORE_HOUSE_EDIT)
public class StoreHouseEditActivity extends BaseLoadActivity implements IStoreHouseEditContract.IView {
    @Autowired(name = "parcelable")
    StorehouseListResp.Storehouse mStore;
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.edt_name)
    EditText mEdtName;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.edt_link)
    EditText mEdtLink;
    @BindView(R.id.edt_tel)
    EditText mEdtTel;
    @BindView(R.id.edt_addr)
    EditText mEdtAddr;
    private Unbinder unbinder;

    private IStoreHouseEditContract.IPresent mPresent;

    public static void start(Activity activity, int requestCode, StorehouseListResp.Storehouse storehouse) {
        if (!RightConfig.checkRight(activity.getString(storehouse == null ? R.string.right_warehouse_creat : R.string.right_warehouseManage_query))) {
            ToastUtils.showShort(activity.getString(R.string.right_tips));
            return;
        }
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_STORE_HOUSE_EDIT, activity, requestCode, storehouse);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_store_house_add);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = StoreHouseEditPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    private void initView() {
        if (mStore != null) {
            mTitle.setHeaderTitle("仓库详情");
            mEdtName.setText(mStore.getHouseName());
            mEdtCode.setText(mStore.getHouseCode());
            mEdtLink.setText(mStore.getCharge());
            mEdtAddr.setText(mStore.getAddress());
            mEdtTel.setText(mStore.getLinkTel());
        }
        mTitle.setRightBtnClick(v -> {
            if (!RightConfig.checkRight(getString(R.string.right_warehouse_update))) {
                ToastUtils.showShort(getString(R.string.right_tips));
                return;
            }
            mPresent.saveStoreHouseInfo();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public StorehouseListResp.Storehouse getStoreHouse() {
        if (mStore == null) {
            mStore = new StorehouseListResp.Storehouse();
            mStore.setAction("0");
        } else {
            mStore.setAction("1");
        }
        mStore.setAddress(mEdtAddr.getText().toString());
        mStore.setHouseCode(mEdtCode.getText().toString());
        mStore.setCharge(mEdtLink.getText().toString());
        mStore.setHouseName(mEdtName.getText().toString());
        mStore.setLinkTel(mEdtTel.getText().toString());
        return mStore;
    }

    @Override
    public void saveSuccess() {
        showToast(TextUtils.equals(mStore.getAction(), "0") ? "创建仓库成功" : "修改仓库成功");
        setResult(RESULT_OK);
        finish();
    }
}
