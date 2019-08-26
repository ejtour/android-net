package com.hll_sc_app.app.inspection.detail;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/26
 */

@Route(path = RouterConfig.INSPECTION_DETAIL)
public class InspectionDetailActivity extends BaseActivity {

    public static void start(String id) {
        RouterUtil.goToActivity(RouterConfig.INSPECTION_DETAIL, id);
    }

}
