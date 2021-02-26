package com.hll_sc_app.app.setting.account.third;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.ThirdAccountBean;
import com.hll_sc_app.widget.TitleBar;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/13
 */

@Route(path = RouterConfig.ACCOUNT_THIRD)
public class ThirdAccountActivity extends BaseLoadActivity implements IThirdAccountContract.IThirdAccountView {
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    private ThirdAccountBean mCurBean;
    private IThirdAccountContract.IThirdAccountPresenter mPresenter;
    private ThirdAccountAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        ButterKnife.bind(this);
        initView();
        mPresenter = ThirdAccountPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("第三方账号管理");
        mAdapter = new ThirdAccountAdapter();
        mAdapter.setNewData(Collections.singletonList(new ThirdAccountBean("微信", UserConfig.WX_UNION_ID, 1, R.drawable.ic_login_wx)));
        mAdapter.setOnItemClickListener((adapter1, view, position) -> {
            Object item = adapter1.getItem(position);
            if (item instanceof ThirdAccountBean) {
                mCurBean = (ThirdAccountBean) item;
                showConfirmDialog();
            }
        });
        mListView.setAdapter(mAdapter);
    }

    private void showConfirmDialog() {
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle("确认解除绑定吗")
                .setMessage("解除绑定后，您就不能使用" + mCurBean.getName() + "帐号\n" +
                        "快速登录" + getString(R.string.app_name) + "了噢～")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    if (item == 1) {
                        mPresenter.unbind(mCurBean.getType());
                    }
                    dialog.dismiss();
                }, "我再想想", "确认解绑").create().show();
    }

    @Override
    public void success() {
        showToast("解绑成功");
        mCurBean.unBind();
        mAdapter.notifyDataSetChanged();
    }

    static class ThirdAccountAdapter extends BaseQuickAdapter<ThirdAccountBean, BaseViewHolder> {
        public ThirdAccountAdapter() {
            super(R.layout.item_account_third);
        }

        @Override
        protected void convert(BaseViewHolder helper, ThirdAccountBean bean) {
            helper.itemView.setClickable(bean.isBound());
            helper.setText(R.id.iat_name, bean.getName())
                    .setText(R.id.iat_status, bean.isBound() ? "解除绑定" : "未绑定")
                    .setImageResource(R.id.iat_flag, bean.getIcon())
                    .setBackgroundRes(R.id.iat_flag, bean.isBound() ? R.drawable.bg_02c160_circle : R.drawable.bg_ddddd_circle);
            TextView status = helper.getView(R.id.iat_status);
            status.setCompoundDrawablesWithIntrinsicBounds(0, 0, bean.isBound() ? R.drawable.ic_arrow_gray : 0, 0);
        }
    }
}
