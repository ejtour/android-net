package com.hll_sc_app.widget.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.mine.MenuItem;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 1/27/21.
 */
public class MenuGridLayout extends LinearLayout {
    @BindView(R.id.vmm_name)
    TextView mName;
    @BindView(R.id.vmm_line)
    View mLine;
    @BindView(R.id.vmm_list_view)
    RecyclerView mListView;
    private final MenuGridAdapter mAdapter;
    private List<MenuItem> mList;

    public MenuGridLayout(Context context) {
        this(context, null);
    }

    public MenuGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (getBackground() == null) {
            setBackgroundResource(R.drawable.bg_white_radius_8_solid);
        }
        setOrientation(VERTICAL);
        View view = View.inflate(context, R.layout.view_mine_menu, this);
        ButterKnife.bind(this, view);
        mListView.setLayoutManager(new GridLayoutManager(context, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter = new MenuGridAdapter();
        mListView.setAdapter(mAdapter);
        if (getTag() != null) {
            mName.setText(getTag().toString());
            mList = new ArrayList<>();
            switch (getTag().toString()) {
                case "常用工具":
                    mList.add(MenuItem.RETURN_AUDIT);
                    mList.add(MenuItem.AGREEMENT_PRICE);
                    if (!BuildConfig.isOdm) mList.add(MenuItem.WAREHOUSE);
                    mList.add(MenuItem.CO_PURCHASER);
                    break;
                case "基础工具":
                    mList.add(MenuItem.SHOP);
                    mList.add(MenuItem.STAFF);
                    mList.add(MenuItem.SELL_PRICE);
                    mList.add(MenuItem.DELIVERY);
                    mList.add(MenuItem.PAYMENT);
                    mList.add(MenuItem.RETURN_AGING);
                    mList.add(MenuItem.INVENTORY);
                    mList.add(MenuItem.PRIVATE_MALL);
                    mList.add(MenuItem.PRINT);
                    break;
                case "资金管理":
                    mList.add(MenuItem.WALLET);
                    mList.add(MenuItem.BILL_LIST);
                    mList.add(MenuItem.CARD);
                    break;
                case "客户管理":
                    mList.add(MenuItem.INVOICE);
                    mList.add(MenuItem.COMPLIANT);
                    mList.add(MenuItem.TARGET_SALE);
                    mList.add(MenuItem.ITEM_BLOCK_LIST);
                    mList.add(MenuItem.GOODS_DEMAND);
                    mList.add(MenuItem.GOODS_SPECIAL_DEMAND);
                    mList.add(MenuItem.INSPECTION);
                    mList.add(MenuItem.PURCHASE_TEMPLATE);
                    mList.add(MenuItem.INQUIRY);
                    mList.add(MenuItem.APTITUDE);
                    break;
                case "综合管理":
                    mList.add(MenuItem.MARKETING);
                    mList.add(MenuItem.REPORT_CENTER);
                    mList.add(MenuItem.MARKET_PRICE);
                    break;
            }
        } else {
            mName.setVisibility(GONE);
            mLine.setVisibility(GONE);
            if (UserConfig.crm()) {
                mList = new ArrayList<>();
                mList.add(MenuItem.GOODS_REPO);
                mList.add(MenuItem.STAFF);
                mList.add(MenuItem.GOODS_FEEDBACK);
                mList.add(MenuItem.COMPLIANT);
                mList.add(MenuItem.BILL_LIST);
                mList.add(MenuItem.RETURN_AUDIT);
                mList.add(MenuItem.INVOICE);
                mList.add(MenuItem.DELIVERY_ROUTE);
                mList.add(MenuItem.REPORT_CENTER);
                mList.add(MenuItem.SALESMAN_RANK);
                mList.add(MenuItem.GOODS_SPECIAL_DEMAND);
            }
        }
        updateMenu();
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    public void updateMenu() {
        if (mList == null) return;
        List<MenuItem> list = new ArrayList<>();
        for (MenuItem menuItem : mList) {
            if (UserConfig.isOnlyReceive() && MenuItem.ONLY_RECEIVE_BAN_LIST.contains(menuItem) ||
                    menuItem == MenuItem.PRINT && !UserConfig.isEnablePrint()) {
                continue;
            }
            list.add(menuItem);
        }
        mAdapter.setNewData(list);
    }

    public void setOnList(List<MenuItem> list) {
        mAdapter.setOnList(list);
    }

    private static class MenuGridAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {

        private List<MenuItem> mList;

        public MenuGridAdapter() {
            super(R.layout.item_mine_menu);
        }

        @Override
        protected void convert(BaseViewHolder helper, MenuItem item) {
            helper.setText(R.id.imm_item, item.getLabel())
                    .setImageResource(R.id.imm_icon, item.getIcon())
                    .setGone(R.id.imm_tip, !CommonUtils.isEmpty(mList) && mList.contains(item));
        }

        public void setOnList(List<MenuItem> list) {
            mList = list;
            notifyDataSetChanged();
        }
    }
}
