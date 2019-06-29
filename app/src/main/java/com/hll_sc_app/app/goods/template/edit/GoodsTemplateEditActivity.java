package com.hll_sc_app.app.goods.template.edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入-商品编辑
 *
 * @author zhuyingsong
 * @date 2019/6/29
 */
@Route(path = RouterConfig.GOODS_TEMPLATE_EDIT, extras = Constant.LOGIN_EXTRA)
public class GoodsTemplateEditActivity extends BaseLoadActivity implements GoodsTemplateEditContract.IGoodsTemplateEditView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable", required = true)
    ArrayList<GoodsBean> mList;
    private GoodsListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_template_edit);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        GoodsTemplateEditPresenter presenter = GoodsTemplateEditPresenter.newInstance();
        presenter.register(this);
        presenter.start();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(5)));
        mAdapter = new GoodsListAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.txt_finish, R.id.txt_save, R.id.txt_saveAndUp})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_finish) {
            finish();
        } else if (id == R.id.txt_save) {

        } else if (id == R.id.txt_saveAndUp) {

        }
    }

    @Override
    public void showGoodsTemplateList(List<GoodsBean> list) {
        mAdapter.setNewData(list);
    }

    class GoodsListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        GoodsListAdapter(List<GoodsBean> list) {
            super(R.layout.item_goods_template_list_edit, list);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
            RecyclerView recyclerView = holder.getView(R.id.recyclerView_spec);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            ProductSpecAdapter adapter = new ProductSpecAdapter();
            recyclerView.setAdapter(adapter);
            return holder;
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            ((GlideImageView) (helper.setText(R.id.txt_productName, item.getProductName())
                .addOnClickListener(R.id.content)
                .setText(R.id.txt_category, String.format("%s - %s - %s", item.getCategoryName(),
                    item.getCategorySubName(), item.getCategoryThreeName()))
                .getView(R.id.img_imgUrl))).setImageURL(item.getImgUrl());
            ((ProductSpecAdapter) ((RecyclerView) helper.getView(R.id.recyclerView_spec)).getAdapter()).setNewData(item.getSpecs());
        }
    }

    class ProductSpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {

        ProductSpecAdapter() {
            super(R.layout.item_goods_template_list_edit_spec);
        }

        @Override
        protected void convert(BaseViewHolder helper, SpecsBean item) {
            helper.setVisible(R.id.txt_specContentTitle, helper.getAdapterPosition() == 0)
                .setText(R.id.txt_specContent, item.getSpecContent())
                .setText(R.id.txt_productPrice, CommonUtils.formatNumber(item.getProductPrice()));
            ;
        }
    }
}
