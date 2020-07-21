package com.hll_sc_app.widget.aptitude;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/9
 */

public class AptitudeListView extends ConstraintLayout implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.val_list_view)
    RecyclerView mListView;
    @BindView(R.id.val_bottom_group)
    Group mBottomGroup;
    private List<AptitudeBean> mList;
    private List<AptitudeTypeBean> mTypeList;
    private AptitudeBean mCurBean;
    private AptitudeAdapter mAdapter;
    private String mSearchWords;
    private int mDefaultPaddingTop;
    private OnClickListener mListener;

    public AptitudeListView(Context context) {
        this(context, null);
    }

    public AptitudeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AptitudeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_aptitude_list, this);
        ButterKnife.bind(this, view);
        mAdapter = new AptitudeAdapter((v, event) -> {
            mCurBean = (AptitudeBean) v.getTag();
            return false;
        });
        mAdapter.setOnItemChildClickListener(this);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        setEditable(false);
    }

    public void setListPaddingTop(int paddingTop) {
        mDefaultPaddingTop = paddingTop;
        int space = UIUtils.dip2px(10);
        mListView.setPadding(space, paddingTop, space, space);
    }

    public void setHeaderView(View header) {
        mAdapter.setHeaderView(header);
    }

    public void setEditable(boolean editable) {
        if (mDefaultPaddingTop == 0) {
            int space = UIUtils.dip2px(10);
            mListView.setPadding(space, editable ? space : 0, space, space);
        }
        if (!TextUtils.isEmpty(mSearchWords)) {
            mAdapter.setNewData(filter(editable ? "" : mSearchWords));
        }
        mAdapter.setEditable(editable);
        mBottomGroup.setVisibility(editable ? View.VISIBLE : View.GONE);
    }

    public void setImageUrl(String url) {
        if (mCurBean != null && mAdapter.getData().contains(mCurBean)) {
            mCurBean.setAptitudeUrl(url);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurBean) + mAdapter.getHeaderLayoutCount());
        }
    }

    public void setList(List<AptitudeBean> list) {
        mList = list;
        mAdapter.setNewData(filter(mSearchWords));
    }

    public void setSearchWords(String searchWords) {
        mSearchWords = searchWords;
        mAdapter.setNewData(filter(mSearchWords));
    }

    public void cacheTypeList(List<AptitudeTypeBean> list) {
        mTypeList = list;
    }

    public List<AptitudeTypeBean> getTypeList() {
        return mTypeList;
    }

    public List<AptitudeBean> getList() {
        List<AptitudeBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mList)) {
            for (AptitudeBean bean : mList) {
                if (!TextUtils.isEmpty(bean.getAptitudeType())) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    @OnClick(R.id.val_add)
    void add() {
        if (mList.size() >= mTypeList.size()) {
            return;
        }
        AptitudeBean data = new AptitudeBean();
        data.setAptitudeName("");
        mAdapter.addData(data);
        mList.add(data);
        post(() -> mListView.smoothScrollToPosition(mAdapter.getData().size() + mAdapter.getHeaderLayoutCount()));
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mCurBean = mAdapter.getItem(position);
        if (mCurBean == null) return;
        switch (view.getId()) {
            case R.id.ia_del:
                mAdapter.remove(mAdapter.getData().indexOf(mCurBean));
                mList.remove(mCurBean);
                break;
            case R.id.ia_date:
                selectDate();
                break;
            case R.id.ia_type:
                selectType();
                break;
        }
    }

    private void selectDate() {
        DateWindow window = new DateWindow((Activity) getContext());
        if (!TextUtils.isEmpty(mCurBean.getEndTime())) {
            window.setCalendar(DateUtil.parse(mCurBean.getEndTime()));
        }
        window.setSelectListener(date -> {
            if (mCurBean != null && mAdapter.getData().contains(mCurBean)) {
                mCurBean.setEndTime(CalendarUtils.toLocalDate(date));
                mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurBean) + mAdapter.getHeaderLayoutCount());
            }
        });
        window.showAtLocation(this, Gravity.END, 0, 0);
    }

    private List<String> getTypes() {
        if (mAdapter == null || mList == null) {
            return new ArrayList<>();
        } else {
            List<String> types = new ArrayList<>();
            for (AptitudeBean bean : mList) {
                if (!TextUtils.isEmpty(bean.getAptitudeType())) {
                    types.add(bean.getAptitudeType());
                }
            }
            return types;
        }
    }

    private void selectType() {
        List<String> selectTypes = getTypes();
        AptitudeTypeBean select = null;
        for (AptitudeTypeBean bean : mTypeList) {
            bean.setEnable(true);
            if (!TextUtils.equals(mCurBean.getAptitudeType(), bean.getAptitudeType())) {
                for (String type : selectTypes) {
                    if (type.equals(bean.getAptitudeType())) {
                        bean.setEnable(false);
                        break;
                    }
                }
            } else {
                select = bean;
            }
        }
        SingleSelectionDialog.newBuilder((Activity) getContext(), AptitudeTypeBean::getAptitudeName, AptitudeTypeBean::isEnable)
                .setTitleText("请选择证件类型")
                .refreshList(mTypeList)
                .select(select)
                .setOnSelectListener(aptitudeTypeBean -> {
                    if (mCurBean != null && mAdapter.getData().contains(mCurBean)) {
                        mCurBean.setAptitudeName(aptitudeTypeBean.getAptitudeName());
                        mCurBean.setAptitudeType(aptitudeTypeBean.getAptitudeType());
                        mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurBean) + mAdapter.getHeaderLayoutCount());
                    }
                })
                .create().show();
    }

    private List<AptitudeBean> filter(String searchWords) {
        if (TextUtils.isEmpty(searchWords)) {
            return mList == null ? null : new ArrayList<>(mList);
        }
        List<AptitudeBean> list = new ArrayList<>();
        for (AptitudeBean bean : mList) {
            if (bean.getAptitudeName().contains(searchWords)) {
                list.add(bean);
            }
        }
        return list;
    }

    private static class AptitudeAdapter extends BaseQuickAdapter<AptitudeBean, BaseViewHolder> {
        private final View.OnTouchListener mListener;
        private boolean mEditable;

        public AptitudeAdapter(View.OnTouchListener listener) {
            super(R.layout.item_aptitude);
            mListener = listener;
        }

        public void setEditable(boolean editable) {
            mEditable = editable;
            notifyDataSetChanged();
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            ((ImgUploadBlock) helper.getView(R.id.ia_image)).setOnDeleteListener(v -> {
                int position = helper.getAdapterPosition();
                AptitudeBean bean = mData.get(position - getHeaderLayoutCount());
                bean.setAptitudeUrl(null);
            });
            helper.addOnClickListener(R.id.ia_del)
                    .addOnClickListener(R.id.ia_type)
                    .addOnClickListener(R.id.ia_date)
                    .setOnTouchListener(R.id.ia_image, mListener);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, AptitudeBean item) {
            updateText(helper.getView(R.id.ia_type), item.getAptitudeName());
            updateText(helper.getView(R.id.ia_date), DateUtil.getReadableTime(item.getEndTime(), Constants.SLASH_YYYY_MM_DD));
            ImageView del = helper.getView(R.id.ia_del);
            del.setClickable(mEditable);
            del.setImageResource(mEditable ? R.drawable.ic_aptitude_del : 0);
            ImgUploadBlock image = helper.getView(R.id.ia_image);
            image.setTag(item);
            image.showImage(item.getAptitudeUrl());
            image.setEditable(mEditable);
            image.setBackgroundResource(mEditable ? R.drawable.base_bg_border_radius_3 : R.drawable.bg_order_reject_edit);
        }

        private void updateText(TextView textView, String content) {
            textView.setText(content);
            textView.setClickable(mEditable);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, mEditable ? R.drawable.ic_arrow_gray : 0, 0);
        }
    }
}
