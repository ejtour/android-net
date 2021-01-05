package com.hll_sc_app.app.message.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.message.MessageSettingBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/28/20.
 */
@Route(path = RouterConfig.MESSAGE_SETTINGS)
public class MessageSettingsActivity extends BaseLoadActivity implements IMessageSettingsContract.IMessageSettingsView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    private MessageSettingAdapter mAdapter;
    private IMessageSettingsContract.IMessageSettingsPresenter mPresenter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        initView();
        mPresenter = MessageSettingsPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("设置不接收消息");
        mTitleBar.setRightText("保存");
        mTitleBar.setRightBtnClick(this::save);
        int space = UIUtils.dip2px(10);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mListView.getItemAnimator()).setSupportsChangeAnimations(false);
        mListView.setPadding(space, space, space, space);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mAdapter = new MessageSettingAdapter();
        mListView.setAdapter(mAdapter);
    }

    private void setHeaderView() {
        View header = View.inflate(this, R.layout.view_message_settings_header, null);
        mAdapter.setHeaderView(header);
    }

    @Override
    public void setData(List<MessageSettingBean> list) {
        setHeaderView();
        mAdapter.setNewData(list);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        initEmptyView();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .setNetError(true)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    private void save(View view) {
        List<MessageSettingBean> data = mAdapter.getData();
        List<String> types = new ArrayList<>();
        for (MessageSettingBean bean : data) {
            if (bean.isHasSelect()) {
                types.add(bean.getServiceType());
            }
        }
        mPresenter.save(types);
    }

    @Override
    public void saveSuccess() {
        onBackPressed();
    }

    private static class MessageSettingAdapter extends BaseQuickAdapter<MessageSettingBean, BaseViewHolder> {

        MessageSettingAdapter() {
            super(R.layout.item_message_settings);
            setOnItemClickListener((adapter, view, position) -> {
                MessageSettingBean bean = getItem(position);
                bean.setHasSelect(!bean.isHasSelect());
                notifyItemChanged(position + getHeaderLayoutCount());
            });
        }

        @Override
        protected void convert(BaseViewHolder helper, MessageSettingBean item) {
            helper.setText(R.id.ims_name, item.getServiceTypeName())
                    .setText(R.id.ims_tips, item.getMessageTitle());
            helper.getView(R.id.ims_check_box).setSelected(item.isHasSelect());
            ((GlideImageView) helper.getView(R.id.ims_image)).setImageURL(item.getLogoUrl());
        }
    }
}
