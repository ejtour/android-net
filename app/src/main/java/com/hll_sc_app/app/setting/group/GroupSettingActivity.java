package com.hll_sc_app.app.setting.group;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/12
 */
@Route(path = RouterConfig.GROUP_SETTING)
public class GroupSettingActivity extends BaseLoadActivity implements IGroupSettingContract.IGroupSettingView {
    // 标记禁止关闭的类型，确保按顺序排列
    private static final int[] DISABLE_CLOSE = {7};
    // 标记禁止开启的类型，确保按顺序排列
    private static final int[] DISABLE_OPEN = {};
    /**
     * 订单设置
     */
    public static final Object[] ORDER_SETTING = {15, 11, 12, 2, 3, 4, 7, 14, 9, 8, 21, 25};
    /**
     * 合作采购商设置
     */
    public static final Object[] CO_SETTING = {6, 18};

    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    String mTitle;
    @Autowired(name = "object1")
    String mUpdateRight;
    @Autowired(name = "object2")
    String mTypes;
    private GroupSettingAdapter mAdapter;
    private IGroupSettingContract.IGroupSettingPresenter mPresenter;
    private int mCurPos;

    public static void start(@NonNull String title, @Nullable String viewRight, Object... args) {
        start(title, viewRight, null, args);
    }

    /**
     * @param title       页面标题
     * @param viewRight   页面权限码
     * @param updateRight 参数更新权限
     * @param args        页面类型参数
     */
    public static void start(@NonNull String title, @Nullable String viewRight, @Nullable String updateRight, Object... args) {
        if (!RightConfig.checkRight(viewRight)) {
            ToastUtils.showShort(MyApplication.getInstance().getString(R.string.right_tips));
            return;
        }
        RouterUtil.goToActivity(RouterConfig.GROUP_SETTING, title, updateRight, TextUtils.join(",", args));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = GroupSettingPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mTitle);
        mAdapter = new GroupSettingAdapter(generateData(), (buttonView, isChecked) -> {
            mCurPos = (int) buttonView.getTag();
            GroupParam bean = mAdapter.getItem(mCurPos);
            if (bean == null) return;
            if (!RightConfig.checkRight(mUpdateRight)) { // 如果无更新权限
                fallbackStatus();
            } else if (Arrays.binarySearch(isChecked ? DISABLE_OPEN : DISABLE_CLOSE, bean.getType()) > -1) { //如果禁止操作
                fallbackStatus();
                showDisableDialog(isChecked ? "开启" : "关闭");
            } else if (bean.isNeedConfirm()) { // 如果需要确认
                showConfirmDialog(isChecked, bean);
            } else { // 如果无需确认
                mPresenter.toggle(bean.getType(), isChecked);
            }
        });
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(5)));
    }

    private List<GroupParam> generateData() {
        List<GroupParam> list = new ArrayList<>();
        if (!TextUtils.isEmpty(mTypes)) {
            for (String type : mTypes.split(",")) {
                for (GroupParam param : GroupParam.values()) {
                    if (CommonUtils.getInt(type) == param.getType()) {
                        list.add(param);
                        break;
                    }
                }
            }
        }
        return list;
    }

    private void showConfirmDialog(boolean isChecked, GroupParam param) {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle(isChecked ? param.getOnTitle() : param.getOffTitle())
                .setMessage(isChecked ? param.getOnDesc() : param.getOffDesc())
                .setCancelable(false)
                .setButton(((dialog, item) -> {
                    if (1 == item) {
                        mPresenter.toggle(param.getType(), isChecked);
                    } else {
                        fallbackStatus();
                    }
                    dialog.dismiss();
                }), "取消", "确定")
                .create()
                .show();
    }

    @Override
    public void fallbackStatus() {
        mAdapter.notifyItemChanged(mCurPos);
    }

    private void showDisableDialog(String label) {
        SpannableString msg = new SpannableString(String.format("如需%s请联系商城管理员进行操作，电话" + getString(R.string.contact_phone), label));
        msg.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                msg.toString().indexOf("电话") + 2, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msg.setSpan(new UnderlineSpan(), msg.toString().indexOf("电话") + 2, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SuccessDialog.newBuilder(this)
                .setCancelable(false)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle(String.format("您暂时不能%s噢", label))
                .setMessage(msg, v -> {
                    UIUtils.callPhone(getString(R.string.contact_phone));
                })
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                }, "好，我知道了")
                .setCancelable(true)
                .create()
                .show();
    }

    @Override
    public void setData(List<GroupParamBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            for (GroupParam param : mAdapter.getData()) {
                for (GroupParamBean bean : list) {
                    if (bean.getParameType() == param.getType()) {
                        param.setValue(bean.getParameValue());
                        break;
                    }
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void success(boolean isChecked) {
        showToast(isChecked ? "已打开" : "已关闭");
        GroupParam item = mAdapter.getItem(mCurPos);
        if (item == null) return;
        if (item.getType() == 7) {
            GlobalPreference.putParam(Constants.ONLY_RECEIVE, isChecked);
            EventBus.getDefault().post(new OrderEvent(OrderEvent.REFRESH_UI));
        }
        item.setValue(isChecked ? 2 : 1);
    }

    @Override
    public String getTypes() {
        return mTypes;
    }
}
