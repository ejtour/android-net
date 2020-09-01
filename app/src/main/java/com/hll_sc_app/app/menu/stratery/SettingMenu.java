package com.hll_sc_app.app.menu.stratery;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.IMenuStrategy;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.setting.group.GroupSettingActivity;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.menu.MenuBean;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

public class SettingMenu implements IMenuStrategy {
    @Override
    public String getTitle() {
        return "设置";
    }

    @Override
    public List<MenuBean> getList() {
        List<MenuBean> list = new ArrayList<>();
        boolean crm = UserConfig.crm();
        if (crm) {
            list.add(new MenuBean("第三方账号管理", RouterConfig.ACCOUNT_THIRD));
            list.add(new MenuBean("修改密码", RouterConfig.USER_CHANGE));
        } else {
            list.add(new MenuBean("账号管理") {
                @Override
                public boolean doFinally() {
                    if (RightConfig.checkRight(App.INSTANCE.getString(R.string.right_accountManagement))) {
                        MenuActivity.start(AccountMenu.class.getSimpleName());
                        return true;
                    } else {
                        ToastUtils.showShort(App.INSTANCE.getString(R.string.right_tips));
                    }
                    return true;
                }
            });
            list.add(new MenuBean("订单设置") {
                @Override
                public boolean doFinally() {
                    GroupSettingActivity.start("订单设置", App.INSTANCE.getString(R.string.right_billSetting),
                            App.INSTANCE.getString(R.string.right_billSetting_update), GroupSettingActivity.ORDER_SETTING);
                    return true;
                }
            });
            list.add(new MenuBean("合作采购商设置") {
                @Override
                public boolean doFinally() {
                    GroupSettingActivity.start("合作采购商设置", App.INSTANCE.getString(R.string.right_workingMealSetting),
                            App.INSTANCE.getString(R.string.right_workingMealSetting_update), GroupSettingActivity.CO_SETTING);
                    return true;
                }
            });
            list.add(new MenuBean("品项关联设置") {
                @Override
                public boolean doFinally() {
                    GroupSettingActivity.start("品项关联设置", null, 32);
                    return true;
                }
            });
            list.add(new MenuBean("价格相关设置") {
                @Override
                public boolean doFinally() {
                    MenuActivity.start(PriceMenu.class.getSimpleName());
                    return true;
                }
            });
            list.add(new MenuBean("税率设置", RouterConfig.SETTING_TAX));
            list.add(new MenuBean("提醒设置", RouterConfig.SETTING_REMIND, !BuildConfig.isDebug));
            if (BuildConfig.isDebug) {
                list.add(new MenuBean("仓库设置", true) {
                    @Override
                    public boolean doFinally() {
                        GroupSettingActivity.start("仓库设置", null, 28);
                        return true;
                    }
                });
            }
        }

        list.add(new MenuBean("清除缓存"));
        MenuBean version = new MenuBean("版本信息") {
            @Override
            public boolean doFinally() {
                Beta.checkUpgrade(true, false);
                return true;
            }
        };
        version.setExtra(String.format(Locale.getDefault(), "%s.%d", SystemUtils.getVersionName(App.INSTANCE),
                SystemUtils.getVersionCode(App.INSTANCE)));
        list.add(version);

        if (!crm) {
            MenuBean contact = new MenuBean("联系客服") {
                @Override
                public boolean doFinally() {
                    UIUtils.callPhone(App.INSTANCE.getString(R.string.contact_phone));
                    return true;
                }
            };
            contact.setExtra(App.INSTANCE.getString(R.string.contact_phone));
            list.add(contact);
            if (BuildConfig.isOdm) {
                list.add(new MenuBean("隐私政策和用户协议") {
                    @Override
                    public boolean doFinally() {
                        WebActivity.start("隐私政策和用户协议", "file:////android_asset/registerLegal.html");
                        return true;
                    }
                });
            } else {
                list.add(new MenuBean("用户服务协议") {
                    @Override
                    public boolean doFinally() {
                        WebActivity.start("用户服务协议", "file:////android_asset/userAgreement.html");
                        return true;
                    }
                });
                list.add(new MenuBean("隐私权政策") {
                    @Override
                    public boolean doFinally() {
                        WebActivity.start("隐私权政策", "file:////android_asset/privacyPolicy.html");
                        return true;
                    }
                });
            }
        }
        return list;
    }
}
