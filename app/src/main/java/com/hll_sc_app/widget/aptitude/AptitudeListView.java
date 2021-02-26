package com.hll_sc_app.widget.aptitude;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.type.AptitudeTypeActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.ImageUploadGroup;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ImageUploadGroup mCurUpload;
    private AptitudeBean mCurBean;
    private final AptitudeAdapter mAdapter;
    private String mSearchWords;
    private int mDefaultPaddingTop;

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
        mAdapter = new AptitudeAdapter();
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

    public void clearHeaderView() {
        mAdapter.removeAllHeaderView();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCurUpload != null) {
            mCurUpload.onActivityResult(requestCode, resultCode, data);
        }
        if (data != null
                && requestCode == AptitudeTypeActivity.REQ_CODE) {
            changeType(data.getParcelableExtra("parcelable"), data.getStringArrayExtra("types"));
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
            case R.id.ia_image:
                mCurUpload = (ImageUploadGroup) view;
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
                if (bean == mCurBean) continue;
                if (!TextUtils.isEmpty(bean.getAptitudeType())) {
                    types.add(bean.getAptitudeType());
                }
            }
            return types;
        }
    }

    private void selectType() {
        AptitudeTypeActivity.start((Activity) getContext(), 1, getTypes(), mCurBean.getAptitudeType());
    }

    public void changeType(AptitudeBean bean, String[] types) {
        List<String> typeList = Arrays.asList(types);
        // 清除已删除的资质类型
        boolean doDel = false;
        if (CommonUtils.isEmpty(mList)) {
            for (AptitudeBean item : mList) {
                if (!typeList.contains(item.getAptitudeType())) {
                    item.setAptitudeName(null);
                    item.setAptitudeType(null);
                    doDel = true;
                }
            }
        }
        if (mCurBean != null && mAdapter.getData().contains(mCurBean) && bean != null) {
            mCurBean.setAptitudeName(bean.getAptitudeName());
            mCurBean.setAptitudeType(bean.getAptitudeType());
            if (!doDel) {
                mAdapter.notifyItemChanged(mAdapter.getData().indexOf(mCurBean) + mAdapter.getHeaderLayoutCount());
            } else {
                mAdapter.notifyDataSetChanged();
            }
        } else if (doDel) {
            mAdapter.notifyDataSetChanged();
        }
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
        private boolean mEditable;

        public AptitudeAdapter() {
            super(R.layout.item_aptitude);
        }

        public void setEditable(boolean editable) {
            mEditable = editable;
            notifyDataSetChanged();
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            ((ImageUploadGroup) helper.getView(R.id.ia_image)).setChangedListener(urls -> {
                AptitudeBean bean = mData.get(helper.getAdapterPosition() - getHeaderLayoutCount());
                String url = TextUtils.join(",", urls);
                bean.setAptitudeUrl(url);
            });
            helper.addOnClickListener(R.id.ia_del)
                    .addOnClickListener(R.id.ia_type)
                    .addOnClickListener(R.id.ia_date)
                    .addOnClickListener(R.id.ia_image);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, AptitudeBean item) {
            helper.setGone(R.id.ia_remain_day_group, item.getExpirationDay() <= 30 && !mEditable)
                    .setText(R.id.ia_remain_day, item.getExpirationDay() <= 0 ? "已到期" : ("剩余" + item.getExpirationDay() + "天"))
                    .setTextColor(R.id.ia_remain_day, ContextCompat.getColor(helper.itemView.getContext(),
                            item.getExpirationDay() <= 0 ? R.color.color_f56564 : R.color.color_f5a623));
            updateText(helper.getView(R.id.ia_type), item.getAptitudeName());
            updateText(helper.getView(R.id.ia_date), DateUtil.getReadableTime(item.getEndTime(), Constants.SLASH_YYYY_MM_DD));
            ImageView del = helper.getView(R.id.ia_del);
            del.setClickable(mEditable);
            del.setImageResource(mEditable ? R.drawable.ic_aptitude_del : 0);
            ImageUploadGroup image = helper.getView(R.id.ia_image);
            image.showImages(TextUtils.isEmpty(item.getAptitudeUrl()) ? null : item.getAptitudeUrl().split(","));
            image.setEditable(mEditable);
        }

        private void updateText(TextView textView, String content) {
            textView.setText(content);
            textView.setClickable(mEditable);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, mEditable ? R.drawable.ic_arrow_gray : 0, 0);
        }
    }
}
