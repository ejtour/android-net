package com.hll_sc_app.app.complainmanage.history;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.complain.ComplainHistoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 投诉详情
 */
@Route(path = RouterConfig.ACTIVITY_COMPLAIN_HISTORY)
public class ComplainHistorylActivity extends BaseLoadActivity implements IComplainHistoryContract.IView {
    @Autowired(name = "object0")
    String mCompaintId;
    @BindView(R.id.recyclerView)
    RecyclerView mHistoryList;
    private Unbinder unbinder;
    private IComplainHistoryContract.IPresent mPresent;


    public static void start(String compaintId) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_COMPLAIN_HISTORY, compaintId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_history);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresent = ComplainHistoryPresent.newInstance();
        mPresent.register(this);
        mPresent.queryHistory();
    }

    private void initView() {
        mHistoryList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public String getComplaintID() {
        return mCompaintId;
    }

    @Override
    public void queryHistorySucess(ComplainHistoryResp complainHistoryResp) {
        ComplianHistoryAdapter adapter;
        if (!CommonUtils.isEmpty(complainHistoryResp.getList())) {
            adapter = new ComplianHistoryAdapter(this, complainHistoryResp.getList());
        } else {
            adapter = new ComplianHistoryAdapter(this, null);
            adapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前没有协商历史噢~").create());
        }
        mHistoryList.setAdapter(adapter);
    }
}
