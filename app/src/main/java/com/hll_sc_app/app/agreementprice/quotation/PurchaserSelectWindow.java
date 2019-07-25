package com.hll_sc_app.app.agreementprice.quotation;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采购商弹窗选择
 *
 * @author 朱英松
 * @date 2019/7/8
 */
public class PurchaserSelectWindow extends BasePopupWindow {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.edt_search_content)
    EditText mEdtSearchContent;
    private SelectConfirmListener mListener;
    private List<PurchaserBean> mList;
    private PurchaserListAdapter mAdapter;

    PurchaserSelectWindow(Activity context, List<PurchaserBean> list) {
        super(context);
        this.mList = list;
        View view = View.inflate(context, R.layout.window_purchaser_select, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PurchaserListAdapter(mList);
        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            PurchaserBean purchaserBean = (PurchaserBean) adapter1.getItem(position);
            if (purchaserBean != null) {
                clearSelectStatus();
                purchaserBean.setSelect(true);
                adapter1.notifyDataSetChanged();
                if (mListener != null) {
                    mListener.confirm(purchaserBean);
                }
                dismiss();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mEdtSearchContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return true;
        });
    }

    private void clearSelectStatus() {
        if (CommonUtils.isEmpty(mList)) {
            return;
        }
        for (PurchaserBean bean : mList) {
            bean.setSelect(false);
        }
    }

    /**
     * 搜索
     */
    private void toSearch() {
        String searchContent = mEdtSearchContent.getText().toString().trim();
        if (CommonUtils.isEmpty(mList)) {
            return;
        }
        List<PurchaserBean> listFilter = new ArrayList<>();
        for (PurchaserBean purchaserBean : mList) {
            if (purchaserBean.getPurchaserName().contains(searchContent)) {
                listFilter.add(purchaserBean);
            }
        }
        mAdapter.setNewData(listFilter);
    }

    public void setListener(SelectConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick(R.id.txt_search)
    public void onViewClicked() {
        toSearch();
    }

    public interface SelectConfirmListener {
        /**
         * 确定
         *
         * @param houseBean bean
         */
        void confirm(PurchaserBean houseBean);
    }

    private static class PurchaserListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter(@Nullable List<PurchaserBean> data) {
            super(R.layout.item_window_purchaser_select, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            TextView textView = helper.getView(R.id.txt_purchaserName);
            textView.setText(item.getPurchaserName());
            textView.setSelected(item.isSelect());
        }
    }
}
