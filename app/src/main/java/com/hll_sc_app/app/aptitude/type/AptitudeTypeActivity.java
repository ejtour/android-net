package com.hll_sc_app.app.aptitude.type;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/26
 */
@Route(path = RouterConfig.APTITUDE_TYPE)
public class AptitudeTypeActivity extends BaseLoadActivity implements IAptitudeTypeContract.IAptitudeTypeView {
    public static final int REQ_CODE = 0x799;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    int mDataType;
    @Autowired(name = "object1")
    String mTypes;
    @Autowired(name = "object2")
    String mType;
    List<String> mSelectedTypeList;
    private AptitudeTypeAdapter mAdapter;
    private IAptitudeTypeContract.IAptitudeTypePresenter mPresenter;
    private AptitudeBean mCurBean;
    private int mResultCode = RESULT_CANCELED;

    /**
     * @param dataType 1-企业，2-商品
     * @param typeList 已选列表
     * @param type     当前选中类型
     */
    public static void start(Activity activity, int dataType, List<String> typeList, String type) {
        String types = TextUtils.join(",", typeList);
        Object[] args = {dataType, types, type};
        RouterUtil.goToActivity(RouterConfig.APTITUDE_TYPE, activity, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = AptitudeTypePresenter.newInstance(mDataType);
        mPresenter.register(this);
        mPresenter.start();
        initView();
        initData();
    }

    private void initData() {
        if (TextUtils.isEmpty(mTypes)) {
            mSelectedTypeList = new ArrayList<>();
        } else {
            mSelectedTypeList = Arrays.asList(mTypes.split(","));
        }
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            mCurBean = null;
            showInputDialog();
        });
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTitleBar.setHeaderTitle("选择证件类型");
        mTitleBar.setRightText("自定义");
        int space = UIUtils.dip2px(10);
        mListView.setPadding(0, space, 0, space);
        mAdapter = new AptitudeTypeAdapter();
        mListView.setAdapter(mAdapter);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), ViewUtils.dip2px(this, 0.5f));
        decor.setLineMargin(space, 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            switch (view.getId()) {
                case R.id.iat_name:
                    mResultCode = RESULT_OK;
                    onBackPressed();
                    break;
                case R.id.iat_del:
                    mPresenter.edit(mCurBean.getId(), null);
                    break;
            }
        });
    }

    private String[] getTypes() {
        List<String> types = new ArrayList<>();
        for (AptitudeBean bean : mAdapter.getData()) {
            types.add(bean.getAptitudeType());
        }
        return types.toArray(new String[]{});
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if (mResultCode == RESULT_OK) {
            intent.putExtra("parcelable", mCurBean);
        }
        intent.putExtra("types", getTypes());
        setResult(mResultCode, intent);
        super.onBackPressed();
    }

    private void showInputDialog() {
        InputDialog.newBuilder(this)
                .setTextTitle("自定义证件类型")
                .setHint("请输入证件名称")
                .setMaxLength(30)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        mPresenter.edit(null, dialog.getInputString());
                    }
                }, "取消", "保存")
                .create().show();
    }

    @Override
    public void setData(List<AptitudeBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            for (AptitudeBean bean : list) {
                if (bean.getAptitudeType().equals(mType)) {
                    bean.setSelected(true);
                    continue;
                }
                if (mSelectedTypeList.contains(bean.getAptitudeType())) {
                    bean.setSelectable(false);
                }
            }
        }
        mAdapter.setNewData(list);
    }

    @Override
    public void delFailure() {
        String name = mCurBean == null ? "“该资质类型”" : "“" + mCurBean.getAptitudeName() + "”";
        String typeName = mDataType == 1 ? "企业" : "商品";
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("资质类型删除失败")
                .setMessage(name + "已被使用不能删除，\n如想删除该资质类型，需先从" + typeName + "资质列表\n删除该资质相关记录")
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                }, "我知道了")
                .create().show();
    }
}
