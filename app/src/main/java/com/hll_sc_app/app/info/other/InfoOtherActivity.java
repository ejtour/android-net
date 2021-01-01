package com.hll_sc_app.app.info.other;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.app.upload.ImageUploadPresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.groupInfo.GroupInfoResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

@Route(path = RouterConfig.INFO_OTHER)
public class InfoOtherActivity extends BaseLoadActivity implements IImageUploadContract.IImageUploadView {
    private static final int REQ_CODE = 0x893;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "parcelable")
    InfoOtherParam mParam;
    private InfoOtherAdapter mAdapter;
    private IImageUploadContract.IImageUploadPresenter mPresenter;
    private int mCurPos;

    public static void start(Activity context, List<GroupInfoResp.OtherLicensesBean> list, boolean editable) {
        RouterUtil.goToActivity(RouterConfig.INFO_OTHER, context, REQ_CODE, new InfoOtherParam(editable, list));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("其他证照");
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10), 0);
        mAdapter = new InfoOtherAdapter(mParam.isEditable(), (v, event) -> {
            mCurPos = (int) v.getTag();
            return false;
        }, mParam.getList());
        mListView.setAdapter(mAdapter);
        if (mParam.isEditable()) {
            mTitleBar.setRightText("保存");
            mTitleBar.setRightBtnClick(this::save);
            TextView footer = new TextView(this);
            footer.setGravity(Gravity.RIGHT);
            footer.setPadding(0, UIUtils.dip2px(10), 0, UIUtils.dip2px(10));
            footer.setTextSize(12);
            footer.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            footer.setOnClickListener(v -> mAdapter.addData(new GroupInfoResp.OtherLicensesBean()));
            footer.setText("+ 添加新的证照");
            mAdapter.setFooterView(footer);
        }
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurPos = position;
            switch (view.getId()) {
                case R.id.iio_del:
                    mAdapter.remove(position);
                    break;
                case R.id.iio_type:
                    GroupInfoResp.OtherLicensesBean item = mAdapter.getItem(mCurPos);
                    if (item != null) selectType(item.getOtherLicenseType());
                    break;
            }
        });
    }

    private void selectType(int type) {
        List<NameValue> list = new ArrayList<>();
        list.add(new NameValue("税务登记证", "1"));
        list.add(new NameValue("餐饮服务许可证", "2"));
        list.add(new NameValue("食品经营许可证", "3"));
        list.add(new NameValue("组织机构代码证", "4"));
        list.add(new NameValue("民办非企业单位证件照", "5"));
        list.add(new NameValue("其他证件照", "6"));
        NameValue cur = null;
        if (type != 0) {
            cur = list.get(type - 1);
        }
        SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .refreshList(list)
                .select(cur)
                .setOnSelectListener(value -> {
                    GroupInfoResp.OtherLicensesBean item = mAdapter.getItem(mCurPos);
                    if (item != null) {
                        item.setOtherLicenseType(Integer.parseInt(value.getValue()));
                        mAdapter.notifyItemChanged(mCurPos);
                    }
                })
                .setTitleText("证照类型")
                .create().show();
    }

    private void save(View view) {
        Intent intent = new Intent();
        intent.putExtra("array", mAdapter.getUsableData());
        intent.putExtra("type", ModifyType.OTHER);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initData() {
        mPresenter = ImageUploadPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    public void setImageUrl(String url) {
        GroupInfoResp.OtherLicensesBean item = mAdapter.getItem(mCurPos);
        if (item == null) return;
        item.setOtherLicenseName(url);
        mAdapter.notifyItemChanged(mCurPos);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }
}
