package com.hll_sc_app.app.menu.stratery;

import android.text.TextUtils;

import com.hll_sc_app.MyApplication;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.base.bean.UserEvent;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.PrivacyDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class AccountMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "账号管理";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        list.add(new MenuBean("更改集团绑定手机号", RouterConfig.ACTIVITY_CHANGE_GROUP_PHONE));
        if (TextUtils.equals("1", GreenDaoUtils.getUser().getAccountType())) {
            list.add(new MenuBean("解绑当前集团", RouterConfig.ACTIVITY_UNBIND_GROUP));
        } else {
            list.add(new MenuBean("修改登录密码", RouterConfig.USER_CHANGE));
        }
        list.add(new MenuBean("第三方账号管理",RouterConfig.ACCOUNT_THIRD));
        MenuBean account = new MenuBean("注销账号"){
            @Override
            public boolean doFinally() {
                EventBus.getDefault().post(new UserEvent(UserEvent.LOGOFF_ACCOUNT));
                return true;
            }
        };
        account.setExtra(String.format(Locale.getDefault(), "%s", "（删除所有数据,永久注销）"));
        list.add(account);
        return list;
    }

    @Override
    public String getViewName() {
        return null;
    }
}
