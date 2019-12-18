package com.hll_sc_app.app.stockmanage.purchaserorder.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchRecord;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderSearchResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 采购单详情查询
 *
 * @author 初坤
 * @date 20190720
 */
@Route(path = RouterConfig.STOCK_PURCHASER_ORDER_SEARCH)
public class PurchaserOrderSearchActivity extends BaseLoadActivity implements PurchaserOrderSearchContract.IPurchaserOrderSearchView {
    private static final int REQ_CODE = 0x144;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.img_clear)
    ImageView imageClearView;

    private Disposable mDisposable;

    private List<String> selectiveSuppliers = new ArrayList<>();

    private PurchaserOrderSearchAdapter mAdapter;
    private PurchaserOrderSearchPresenter mPresenter;

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.STOCK_PURCHASER_ORDER_SEARCH, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_purchaser_order_search);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = PurchaserOrderSearchPresenter.newInstance();
        mAdapter = new PurchaserOrderSearchAdapter();
        mAdapter.setOnItemClickListener((adapter,view,position)->{
            PurchaserOrderSearchRecord item  = (PurchaserOrderSearchRecord) adapter.getItem(position);
            String supplierID = item.getSupplierID();
            if(!selectiveSuppliers.contains(supplierID)){
                selectiveSuppliers.add(supplierID);
            }else{
                selectiveSuppliers.remove(supplierID);
            }
            //再次渲染adapter的convert方法
            mAdapter.notifyDataSetChanged();
        });
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.register(this);
        mDisposable = textChangeObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::querySupplyChainGroup);
    }

    private void querySupplyChainGroup(String word){
        imageClearView.setVisibility(View.VISIBLE);
        mPresenter.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.img_back,R.id.edt_search,R.id.img_clear,R.id.txt_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_clear:
                edtSearch.setText("");
                break;
            case R.id.txt_search:
                Intent intent = new Intent();
                String suppliers = "";
                if(selectiveSuppliers!=null && selectiveSuppliers.size()>0){
                    for(int i=0;i<selectiveSuppliers.size();i++){
                        if(i!=selectiveSuppliers.size()-1){
                            suppliers+=selectiveSuppliers.get(i)+",";
                        }else{
                            suppliers+=selectiveSuppliers.get(i);
                        }
                    }
                }
                intent.putExtra("result",suppliers);
                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void showPurchaserOrderSearchList(PurchaserOrderSearchResp purchaserOrderSearchResp,boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(purchaserOrderSearchResp.getRecords())) {
                mAdapter.addData(purchaserOrderSearchResp.getRecords());
            }
        } else {
            mAdapter.setNewData(purchaserOrderSearchResp.getRecords());
        }
    }

    /**
     * 500 毫秒去抖
     */
    private Observable<String> textChangeObservable() {
        Observable<String> observable = Observable.create(emitter -> {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().length() > 0) {
                        imageClearView.setVisibility(View.VISIBLE);
                    } else {
                        imageClearView.setVisibility(View.GONE);
                    }
                    emitter.onNext(s.toString().trim());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (0 == s.toString().length()) {
                        imageClearView.setVisibility(View.GONE);
                        edtSearch.setText("");
                    }
                }
            };
            edtSearch.addTextChangedListener(textWatcher);

        });
        return observable.filter(q -> q.length() > 0).debounce(500, TimeUnit.MILLISECONDS);
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public List<String> getSelectiveSuppliers() {
        return selectiveSuppliers;
    }

    @Override
    public String getSearchKey() {
        return edtSearch.getText().toString().trim();
    }

    class PurchaserOrderSearchAdapter extends BaseQuickAdapter<PurchaserOrderSearchRecord, BaseViewHolder> {

        PurchaserOrderSearchAdapter() {
            super(R.layout.item_stock_purchaser_order_search);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserOrderSearchRecord bean) {
            helper.setText(R.id.txt_purchaser_order_search, bean.getSupplierName());
            CheckBox checkBox = helper.getView(R.id.purchaser_order_search_checkbox);
            checkBox.setClickable(false);
            if(selectiveSuppliers.contains(bean.getSupplierID())) {
                checkBox.setChecked(true);
            }else{
                checkBox.setChecked(false);
            }
        }
    }

}
