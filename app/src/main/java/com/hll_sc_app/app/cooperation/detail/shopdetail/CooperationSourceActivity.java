package com.hll_sc_app.app.cooperation.detail.shopdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.detail.CooperationDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.CooperationSourceBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商详情- 门店详情-合作方式
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL_SOURCE, extras = Constant.LOGIN_EXTRA)
public class CooperationSourceActivity extends BaseLoadActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable")
    ArrayList<CooperationSourceBean> mList;
    private CooperationDetailActivity.PurchaserShopListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_shop_detail_source);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mRecyclerView.setAdapter(new CooperationSourceListAdapter(mList));
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    private static class CooperationSourceListAdapter extends BaseQuickAdapter<CooperationSourceBean, BaseViewHolder> {

        CooperationSourceListAdapter(List<CooperationSourceBean> list) {
            super(R.layout.item_cooperation_shop_detail_source, list);
        }

        @Override
        protected void convert(BaseViewHolder helper, CooperationSourceBean item) {
            helper.setText(R.id.txt_source, getSource(item.getSource()))
                .setText(R.id.txt_ownerName, TextUtils.isEmpty(item.getOwnerName()) ? "自助添加合作关系" : item.getOwnerName())
                .setText(R.id.txt_agreeTime, getAgreeTime(item));
        }

        private String getSource(String source) {
            String agreeTime = "无";
            if (TextUtils.equals(source, "normal")) {
                agreeTime = "直接合作";
            } else if (TextUtils.equals(source, "appointWarehouse")) {
                agreeTime = "代仓合作";
            }
            return agreeTime;
        }

        private String getAgreeTime(CooperationSourceBean item) {
            String agreeTime = "无";
            Date date = CalendarUtils.parse(item.getAgreeTime(), CalendarUtils.FORMAT_HH_MM_SS);
            if (date != null) {
                agreeTime = "合作日期：" + CalendarUtils.format(date, "yyyy/MM/dd");
            }
            return agreeTime;
        }
    }
}
