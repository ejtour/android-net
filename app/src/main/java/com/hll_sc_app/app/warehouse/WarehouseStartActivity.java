package com.hll_sc_app.app.warehouse;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.api.WarehouseService;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.warehouse.GroupDetail;
import com.hll_sc_app.bean.warehouse.WarehouseListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.ButterKnife;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 代仓管理启动页面
 *
 * @author zhuyingsong
 * @date 2019/2/27
 */
@Route(path = RouterConfig.WAREHOUSE_START, extras = Constant.LOGIN_EXTRA)
public class WarehouseStartActivity extends BaseLoadActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setLightStatusBar(getWindow(), true);
        StatusBarCompat.setTranslucent(getWindow(), true);
        ButterKnife.bind(this);
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            finish();
            return;
        }
        if (TextUtils.equals("1", userBean.getSelfOperated())) {
            getWarehouseOpen();
        } else {
            queryWarehouseList();
        }
    }

    public void getWarehouseOpen() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("flag", "1")
            .put("groupID", UserConfig.getGroupID())
            .put("groupType", "1")
            .create();
        WarehouseService.INSTANCE
            .queryGroupDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<GroupDetail>() {
                @Override
                public void onSuccess(GroupDetail result) {
                    dealResult(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    finish();
                    showError(e);
                }
            });
    }

    public void queryWarehouseList() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "formalSigned")
            .put("purchaserID", UserConfig.getGroupID())
            .put("originator", "0")
            .put("pageNum", "1")
            .put("pageSize", "20")
            .put("source", "app")
            .create();
        WarehouseService.INSTANCE
            .queryWarehouseList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(getOwner())))
            .subscribe(new BaseCallback<WarehouseListResp>() {
                @Override
                public void onSuccess(WarehouseListResp resp) {
                    RouterUtil.goToActivity(resp.getTotalNum() > 0 ? RouterConfig.WAREHOUSE_SHIPPER :
                        RouterConfig.WAREHOUSE_INTRODUCE, this);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    finish();
                    showError(e);
                }
            });
    }

    /**
     * 跳转判断
     */
    private void dealResult(GroupDetail detail) {
        if (detail != null) {
            if (TextUtils.equals(detail.getIsSelfOperated(), "1")) {
                // 自营
                if (TextUtils.equals("0", detail.getWareHourseStatus())) {
                    // 未开通代仓
                    showTipsDialog();
                } else {
                    RouterUtil.goToActivity(RouterConfig.WAREHOUSE_LIST, this);
                }
            } else {
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_INTRODUCE, this);
            }
        }
    }

    private void showTipsDialog() {
        SuccessDialog successDialog = SuccessDialog.newBuilder(this)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("提示")
            .setMessage("没有开通代仓功能")
            .setCancelable(false)
            .setButton((dialog, item) -> {
                dialog.dismissWithOutDim();
                WarehouseStartActivity.this.finish();
            }, "我知道了").create();
        successDialog.showWithOutDim();
        if (successDialog.getWindow() != null) {
            successDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4d000000")));
        }
    }
}
