package com.hll_sc_app.app.info.modify;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public class InfoModifyPresenter implements IInfoModifyContract.IInfoModifyPresenter {
    private IInfoModifyContract.IInfoModifyView mView;
    @ModifyType
    private int mModifyType;

    private InfoModifyPresenter(int modifyType) {
        mModifyType = modifyType;
    }

    public static InfoModifyPresenter newInstance(@ModifyType int modifyType) {
        return new InfoModifyPresenter(modifyType);
    }

    @Override
    public void save(String content) {
        switch (mModifyType) {
            case ModifyType.EMAIL:
                bindEmail(content, getObserver());
                break;
            case ModifyType.CONTACT:
                updateInfo("linkman", content);
                break;
            case ModifyType.PHONE:
                updateInfo("groupPhone", content);
                break;
            case ModifyType.FAX:
                updateInfo("fax", content);
                break;
            case ModifyType.GROUP_EMAIL:
                updateInfo("groupMail", content);
                break;
            case ModifyType.NAME:
            case ModifyType.ID_CARD:
            default:
                break;
        }
    }

    private void bindEmail(String email, SimpleObserver<Object> observer) {
        UserBean user = GreenDaoUtils.getUser();
        UserService.INSTANCE.bindEmail(BaseMapReq.newBuilder()
                .put("email", email)
                .put("employeeID", user.getEmployeeID())
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }

    private void updateInfo(String key, String value) {
        User.updateGroupInfo(key, value, getObserver());
    }

    private SimpleObserver<Object> getObserver() {
        return new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("保存成功");
                mView.success();
            }
        };
    }

    @Override
    public void register(IInfoModifyContract.IInfoModifyView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
