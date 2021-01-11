package com.hll_sc_app.app.submit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.MyApplication;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */
@Route(path = RouterConfig.SUBMIT_SUCCESS)
public class SubmitSuccessActivity extends BaseActivity {
    @BindView(R.id.ass_header)
    TitleBar assHeader;

    /**
     * @param title       标题
     * @param desc        描述
     * @param detailsPath 详情路径
     * @param id          详情 id
     */
    public static void start(@NonNull String title,
                             @Nullable String desc,
                             @Nullable String detailsPath,
                             @Nullable String id) {
        SubmitParams params = new SubmitParams(id);
        params.setTitle(title);
        params.setDetailsPath(detailsPath);
        if (desc != null) {
            params.setDesc(desc);
        }
        ARouter.getInstance()
                .build(RouterConfig.SUBMIT_SUCCESS)
                .withParcelable("parcelable", params)
                .setProvider(new LoginInterceptor())
                .navigation();
    }

    public static void start(@NonNull String title) {
        start(title, null, null, null);
    }

    @BindView(R.id.ass_label)
    TextView assLabel;
    @BindView(R.id.ass_desc)
    TextView assDesc;
    @BindView(R.id.ass_goto_list)
    TextView assGotoList;
    @BindView(R.id.ass_view_detail)
    TextView assViewDetail;
    private BackType mBackType;
    private Unbinder unbinder;

    /**
     * 路由传递过来的 参数
     */
    @Autowired(name = "parcelable", required = true)
    SubmitParams entry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_success);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mBackType = ((MyApplication) getApplication()).getLastBackType();
        assLabel.setText(entry.getTitle()); // 设置标题
        assDesc.setText(entry.getDesc()); // 设置内容
        // 通过类型参数对按钮按钮状态进行调整
        assViewDetail.setVisibility(TextUtils.isEmpty(entry.getDetailsPath()) || TextUtils.isEmpty(entry.getDetailsId())
                ? View.GONE : View.VISIBLE);
        assGotoList.setText(mBackType.getLabel());
        assHeader.setLeftBtnClick(this::goBack);
    }

    @Override
    public void onBackPressed() {
        goBack(null);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick({R.id.ass_goto_list, R.id.ass_view_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ass_goto_list:
                goBack(view);
                break;
            case R.id.ass_view_detail:
                ARouter.getInstance()
                        .build(entry.getDetailsPath())
                        .withString("object0", entry.getDetailsId())
                        .setProvider(new LoginInterceptor())
                        .navigation();
                break;
            default:
                break;
        }
    }

    private void goBack(View view) {
        ARouter.getInstance()
                .build(mBackType.getPath())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .setProvider(new LoginInterceptor())
                .navigation(this);
    }
}
