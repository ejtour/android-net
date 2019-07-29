package com.hll_sc_app.app.staffmanage.detail.role;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.staff.RoleBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 员工管理-选择员工岗位
 *
 * @author zhuyingsong
 * @date 2019/7/25
 */
public class RoleSelectFragment extends BaseLazyFragment {
    @BindView(R.id.txt_tips)
    TextView mTxtTips;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private int mType;
    private RoleListAdapter mAdapter;
    private ArrayList<RoleBean> mList;

    public static RoleSelectFragment newInstance(int type, ArrayList<RoleBean> list) {
        Bundle args = new Bundle();
        args.putInt("object0", type);
        args.putParcelableArrayList("parcelable", list);
        RoleSelectFragment fragment = new RoleSelectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt("object0");
            mList = bundle.getParcelableArrayList("parcelable");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_staff_manage_role_select, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showTips();
        mAdapter = new RoleListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RoleBean bean = mAdapter.getItem(position);
            if (bean != null) {
                bean.setSelect(!bean.isSelect());
                adapter.notifyItemChanged(position);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(),
            R.color.base_color_divider), UIUtils.dip2px(1)));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void showTips() {
        String tips = "";
        if (mType == RoleSelectActivity.TYPE_MANAGE) {
            tips = "管理型岗位用于日常维护商品管理、处理订单、配置功能设置等工作。";
        } else if (mType == RoleSelectActivity.TYPE_BUSINESS) {
            tips = "业务型岗位用于员工查看销售统计、业绩查询、客户管理、订单查询等。";
        } else if (mType == RoleSelectActivity.TYPE_DELIVERY) {
            tips = "配送型岗位主要用于司机配送过程中处理订单、验收货物、签收收款等。";
        }
        String look = "@点击查看";
        String permissions = "岗位权限表";
        SpannableString spannableString = new SpannableString(tips + look + permissions);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                RouterUtil.goToActivity(RouterConfig.STAFF_PERMISSION);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        }, tips.length(), tips.length() + look.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ImageSpan imageSpan = new ImageSpan(requireContext(), R.drawable.ic_staff_select_role_notice,
            DynamicDrawableSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, tips.length(), tips.length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTxtTips.setMovementMethod(LinkMovementMethod.getInstance());
        mTxtTips.setText(spannableString);
    }

    @Override
    protected void initData() {
        mAdapter.setNewData(mList);
        mAdapter.setEmptyView(EmptyView.newBuilder(requireActivity())
            .setTipsTitle("暂无此类型的员工岗位")
            .create());
    }

    public List<RoleBean> getSelectRole() {
        List<RoleBean> list = new ArrayList<>();
        if (!CommonUtils.isEmpty(mList)) {
            for (RoleBean bean : mList) {
                if (bean.isSelect()) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    class RoleListAdapter extends BaseQuickAdapter<RoleBean, BaseViewHolder> {

        RoleListAdapter() {
            super(R.layout.item_staff_role);
        }

        @Override
        protected void convert(BaseViewHolder helper, RoleBean bean) {
            helper.setText(R.id.txt_roleName, bean.getRoleName())
                .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
