package com.hll_sc_app.app.info.doorway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.info.ModifyType;
import com.hll_sc_app.app.upload.IImageUploadContract;
import com.hll_sc_app.app.upload.ImageUploadPresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

@Route(path = RouterConfig.INFO_DOORWAY)
public class InfoDoorwayActivity extends BaseLoadActivity implements IImageUploadContract.IImageUploadView {
    private static final int REQ_CODE = 877;
    @BindView(R.id.aid_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aid_tip)
    TextView mTip;
    @BindView(R.id.aid_list_view)
    RecyclerView mListView;
    @Autowired(name = "object0")
    boolean mEditable;
    @Autowired(name = "object1")
    String mContent;
    private InfoDoorwayAdapter mAdapter;
    private IImageUploadContract.IImageUploadPresenter mPresenter;

    public static void start(Activity context, boolean editable, String content) {
        Object[] args = {editable, content};
        RouterUtil.goToActivity(RouterConfig.INFO_DOORWAY, context, REQ_CODE, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_info_doorway);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = ImageUploadPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        mAdapter = new InfoDoorwayAdapter(mEditable, TextUtils.isEmpty(mContent) ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(mContent.split(","))));
        if (mEditable) {
            mTitleBar.setRightText("保存");
            mTitleBar.setRightBtnClick(this::save);
        } else {
            mTip.setVisibility(View.GONE);
            ((LinearLayout.LayoutParams) mListView.getLayoutParams()).topMargin = UIUtils.dip2px(10);
        }
        mListView.setAdapter(mAdapter);
    }

    private void save(View view) {
        Intent intent = new Intent();
        List<String> list = new ArrayList<>();
        for (String s : mAdapter.getData()) {
            if (!TextUtils.isEmpty(s))
                list.add(s);
        }
        intent.putExtra("content", TextUtils.join(",", list));
        intent.putExtra("type", ModifyType.DOORWAY);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == Constant.IMG_SELECT_REQ_CODE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }

    @Override
    public void setImageUrl(String url) {
        mAdapter.addData(url);
    }
}
