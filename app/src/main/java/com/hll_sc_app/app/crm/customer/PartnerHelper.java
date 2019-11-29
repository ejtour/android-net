package com.hll_sc_app.app.crm.customer;

import android.app.Activity;
import android.view.View;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public class PartnerHelper {
    private ContextOptionsWindow mOptionsWindow;

    public void showOption(Activity activity, View view, int gravity) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_registered_option, OptionType.OPTION_CUSTOMER_REGISTERED));
            list.add(new OptionsBean(R.drawable.ic_unregistered_option, OptionType.OPTION_CUSTOMER_UNREGISTERED));
            mOptionsWindow = new ContextOptionsWindow(activity)
                    .refreshList(list)
                    .setListener((adapter, view1, position) -> {
                        mOptionsWindow.dismiss();
                        OptionsBean item = (OptionsBean) adapter.getItem(position);
                        if (item == null) return;
                        switch (item.getLabel()) {
                            case OptionType.OPTION_CUSTOMER_REGISTERED:
                                RouterUtil.goToActivity(RouterConfig.COOPERATION_PURCHASER_LIST);
                                break;
                            case OptionType.OPTION_CUSTOMER_UNREGISTERED:
                                RouterUtil.goToActivity(RouterConfig.USER_REGISTER);
                                break;
                        }
                    });
        }
        mOptionsWindow.showAsDropDownFix(view, gravity);
    }
}
