package com.hll_sc_app.widget.stockmanage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class DepotRangeView extends ConstraintLayout {
    @BindView(R.id.vdr_desc)
    TextView mDesc;
    @BindView(R.id.vdr_province_list)
    RecyclerView mProvinceList;
    @BindView(R.id.vdr_city_list)
    RecyclerView mCityList;
    @BindView(R.id.vdr_area_list)
    RecyclerView mAreaList;
    @BindView(R.id.vdr_empty_tip)
    TextView mEmptyTip;
    private ProvinceAdapter mProvinceAdapter;
    private CityAdapter mCityAdapter;
    private AreaAdapter mAreaAdapter;
    private OnClickListener mListener;

    public DepotRangeView(Context context) {
        this(context, null);
    }

    public DepotRangeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepotRangeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_depot_range, this);
        ButterKnife.bind(this, view);
        mProvinceAdapter = new ProvinceAdapter();
        mProvinceAdapter.bindToRecyclerView(mProvinceList);
        mProvinceAdapter.setOnItemClickListener((adapter, view1, position) -> selectProvince(position));
        mCityAdapter = new CityAdapter();
        mCityAdapter.bindToRecyclerView(mCityList);
        mCityAdapter.setOnItemClickListener((adapter, view12, position) ->
                selectCity(position));
        mAreaAdapter = new AreaAdapter();
        mAreaAdapter.bindToRecyclerView(mAreaList);
    }

    private void selectProvince(int pos) {
        mCityAdapter.setNewData(mProvinceAdapter.select(pos).getCityList());
        if (mCityAdapter.getSelectBean() != null) {
            mAreaAdapter.setNewData(mCityAdapter.getSelectBean().getAreaList());
        }
    }

    private void selectCity(int pos) {
        mAreaAdapter.setNewData(mCityAdapter.select(pos).getAreaList());
    }

    public void setData(DepotResp resp) {
        mDesc.setVisibility(resp.getIsWholeCountry() == 1 ? VISIBLE : GONE);
        if (!CommonUtils.isEmpty(resp.getWarehouseDeliveryRangeList())) {
            mEmptyTip.setVisibility(GONE);
            mProvinceAdapter.setNewData(resp.getWarehouseDeliveryRangeList());
            selectProvince(0);
            mProvinceList.setVisibility(VISIBLE);
            mCityList.setVisibility(VISIBLE);
            mAreaList.setVisibility(VISIBLE);
        } else {
            mProvinceAdapter.setNewData(null);
            mEmptyTip.setVisibility(VISIBLE);
            mProvinceList.setVisibility(GONE);
            mCityList.setVisibility(GONE);
            mAreaList.setVisibility(GONE);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    @OnClick(R.id.vdr_setup)
    public void onViewClicked(View view) {
        if (mListener != null) {
            mListener.onClick(view);
        }
    }

    private static class ProvinceAdapter extends BaseQuickAdapter<ProvinceListBean, BaseViewHolder> {
        private ProvinceListBean mSelectBean;

        public ProvinceAdapter() {
            super(null);
        }

        public ProvinceListBean select(int position) {
            mSelectBean = getItem(position);
            if (mSelectBean != null) {
                for (ProvinceListBean bean : getData()) {
                    bean.setSelect(bean.getProvinceCode().equals(mSelectBean.getProvinceCode()));
                }
                notifyDataSetChanged();
            }
            return mSelectBean;
        }

        @SuppressLint("ResourceType")
        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = new TextView(parent.getContext());
            itemView.setTextSize(12);
            itemView.setTextColor(ContextCompat.getColorStateList(parent.getContext(), R.drawable.color_state_on_pri_off_222));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setBackgroundResource(R.drawable.bg_depot_province_item);
            itemView.setSingleLine(true);
            itemView.setEllipsize(TextUtils.TruncateAt.END);
            return new BaseViewHolder(itemView);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProvinceListBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setSelected(item.isSelect());
            itemView.setText(item.getProvinceName());
        }

        @Override
        public void setNewData(@Nullable List<ProvinceListBean> data) {
            if (!CommonUtils.isEmpty(data)) {
                if (mSelectBean != null) {
                    ProvinceListBean select = null;
                    for (ProvinceListBean bean : data) {
                        bean.setSelect(mSelectBean.getProvinceCode().equals(bean.getProvinceCode()));
                        if (mSelectBean.getProvinceCode().equals(bean.getProvinceCode())) {
                            select = bean;
                            bean.setSelect(true);
                        } else {
                            bean.setSelect(false);
                        }
                    }
                    if (select == null) {
                        select = data.get(0);
                        select.setSelect(true);
                    }
                    mSelectBean = select;
                } else {
                    mSelectBean = data.get(0);
                    mSelectBean.setSelect(true);
                }
            } else {
                mSelectBean = null;
            }
            super.setNewData(data);
            if (!CommonUtils.isEmpty(mData) && mSelectBean != null) {
                getRecyclerView().scrollToPosition(mData.indexOf(mSelectBean));
            }
        }
    }

    private static class CityAdapter extends BaseQuickAdapter<CityListBean, BaseViewHolder> {
        private CityListBean mSelectBean;

        public CityAdapter() {
            super(null);
        }

        public CityListBean getSelectBean() {
            return mSelectBean;
        }

        public CityListBean select(int position) {
            mSelectBean = getItem(position);
            if (mSelectBean != null) {
                for (CityListBean bean : getData()) {
                    bean.setSelect(bean.getCityCode().equals(mSelectBean.getCityCode()));
                }
                notifyDataSetChanged();
            }
            return mSelectBean;
        }

        @SuppressLint("ResourceType")
        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = new TextView(parent.getContext());
            itemView.setTextSize(12);
            itemView.setTextColor(ContextCompat.getColorStateList(parent.getContext(), R.drawable.color_state_on_pri_off_222));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setBackgroundResource(R.drawable.bg_depot_city_item);
            itemView.setSingleLine(true);
            itemView.setEllipsize(TextUtils.TruncateAt.END);
            return new BaseViewHolder(itemView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CityListBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setSelected(item.isSelect());
            itemView.setText(item.getCityName());
        }

        @Override
        public void setNewData(@Nullable List<CityListBean> data) {
            if (!CommonUtils.isEmpty(data)) {
                if (mSelectBean != null) {
                    CityListBean select = null;
                    for (CityListBean bean : data) {
                        bean.setSelect(mSelectBean.getCityCode().equals(bean.getCityCode()));
                        if (mSelectBean.getCityCode().equals(bean.getCityCode())) {
                            select = bean;
                            bean.setSelect(true);
                        } else {
                            bean.setSelect(false);
                        }
                    }
                    if (select == null) {
                        select = data.get(0);
                        select.setSelect(true);
                    }
                    mSelectBean = select;
                } else {
                    mSelectBean = data.get(0);
                    mSelectBean.setSelect(true);
                }
            } else {
                mSelectBean = null;
            }
            super.setNewData(data);
            if (!CommonUtils.isEmpty(mData) && mSelectBean != null) {
                getRecyclerView().scrollToPosition(mData.indexOf(mSelectBean));
            }
        }
    }

    private static class AreaAdapter extends BaseQuickAdapter<AreaListBean, BaseViewHolder> {
        private int space;

        public AreaAdapter() {
            super(null);
            space = UIUtils.dip2px(20);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = new TextView(parent.getContext());
            itemView.setTextSize(12);
            itemView.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.color_222222));
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setPadding(space, 0, 0, 0);
            itemView.setSingleLine(true);
            itemView.setEllipsize(TextUtils.TruncateAt.END);
            return new BaseViewHolder(itemView);
        }

        @Override
        protected void convert(BaseViewHolder helper, AreaListBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getAreaName());
        }
    }
}
