package com.hll_sc_app.app.warehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 代仓管理-我是货主默认介绍页面
 *
 * @author zhuyingsong
 * @date 2019/8/2
 */
@Route(path = RouterConfig.WAREHOUSE_INTRODUCE, extras = Constant.LOGIN_EXTRA)
public class WarehouseIntroduceActivity extends BaseLoadActivity implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.img_add)
    ImageView mImgAdd;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_introduce);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_back, R.id.img_add, R.id.txt_contact, R.id.txt_recommend})
    public void onViewClicked(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_add:
                showAddWindow();
                break;
            case R.id.txt_contact:
                UIUtils.callPhone(getString(R.string.warehouse_contact));
                break;
            case R.id.txt_recommend:
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_RECOMMEND);
                break;
            default:
                break;
        }
    }

    private void showAddWindow() {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_warehouse_add, OptionType.OPTION_WAREHOUSE_ADD));
            list.add(new OptionsBean(R.drawable.ic_cooperation_receive, OptionType.OPTION_COOPERATION_RECEIVE));
            list.add(new OptionsBean(R.drawable.ic_cooperation_send, OptionType.OPTION_COOPERATION_SEND));
            mOptionsWindow = new ContextOptionsWindow(this).setListener(this).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(mImgAdd, Gravity.END);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OptionsBean optionsBean = (OptionsBean) adapter.getItem(position);
        if (optionsBean == null) {
            return;
        }
        if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_WAREHOUSE_ADD)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_ADD);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_RECEIVE)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_APPLICATION);
        } else if (TextUtils.equals(optionsBean.getLabel(), OptionType.OPTION_COOPERATION_SEND)) {
            RouterUtil.goToActivity(RouterConfig.WAREHOUSE_INVITE);
        }
        mOptionsWindow.dismiss();
    }
}
