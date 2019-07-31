package com.hll_sc_app.app.report.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.report.resp.group.PurchaserGroupBean;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.CUSTOMER_SALE_SEARCH)
public class ReportSearchActivity extends BaseLoadActivity implements IReportSearchContract.IReportSearchView {

    @BindView(R.id.edt_search)
    EditText editText;
    @BindView(R.id.txt_search)
    TextView textView;
    @BindView(R.id.report_search_recyclerView)
    RecyclerView mRecycleView;


    private IReportSearchContract.IReportSearchPresenter presenter;

    private ReportSearchListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_search);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        presenter = ReportSearchPresenter.newInstance();
        mAdapter = new ReportSearchListAdapter();
        mAdapter.setOnItemClickListener((adapter,view,position)->{
            PurchaserGroupBean bean = (PurchaserGroupBean) adapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("result",bean);
            setResult(RESULT_OK,intent);
            finish();
        });
        mRecycleView.setAdapter(mAdapter);
        presenter.register(this);
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showSearchList(List<PurchaserGroupBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public String getSearchWord() {
        return editText.getText().toString().trim();
    }

    @OnClick({R.id.txt_search,R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_search:
                presenter.querySearchList("0", getSearchWord(), 1, 50);
                break;
            default:
                break;
        }

    }

    class ReportSearchListAdapter extends BaseQuickAdapter<PurchaserGroupBean, BaseViewHolder> {

        ReportSearchListAdapter() {
            super(R.layout.item_report_search_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserGroupBean bean) {
            helper.setText(R.id.txt_search_name, bean.getPurchaserName());
        }
    }
}
