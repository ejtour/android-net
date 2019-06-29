package com.hll_sc_app.app.goods.template.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.app.goods.add.specs.depositproducts.DepositProductsActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.InputDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
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
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
            if (bean != null) {
                bean.setEditFrom(GoodsBean.EDIT_FROM_TEMPLATE);
                RouterUtil.goToActivity(RouterConfig.ROOT_HOME_GOODS_ADD, GoodsTemplateEditActivity.this,
                    ImgUploadBlock.REQUEST_CODE_CHOOSE, bean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            GoodsBean bean = data.getParcelableExtra(DepositProductsActivity.INTENT_TAG);
            if (bean != null) {
                int index = mList.indexOf(bean);
                mList.set(index, bean);
                mAdapter.notifyDataSetChanged();
            }
        }
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
            adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                SpecsBean specsBean = (SpecsBean) adapter1.getItem(position);
                if (specsBean != null) {
                    showInputDialog(specsBean, adapter1, position);
                }
            });
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


        private void showInputDialog(SpecsBean specsBean, BaseQuickAdapter adapter, int position) {
            if (mContext instanceof Activity) {
                InputDialog.newBuilder((Activity) mContext)
                    .setCancelable(false)
                    .setTextTitle("输入" + specsBean.getSpecContent() + "平台价格")
                    .setHint("输入平台价格")
                    .setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .setText(CommonUtils.formatNumber(specsBean.getProductPrice()))
                    .setTextWatcher((GoodsSpecsAddActivity.CheckTextWatcher) s -> {
                        if (!GoodsSpecsAddActivity.PRODUCT_PRICE.matcher(s.toString()).find() && s.length() > 1) {
                            s.delete(s.length() - 1, s.length());
                            showToast("平台价格支持7位整数或小数点后两位");
                        }
                    })
                    .setButton((dialog, item) -> {
                        if (item == 1) {
                            if (TextUtils.isEmpty(dialog.getInputString())) {
                                showToast("输入平台价格不能为空");
                                return;
                            }
                            specsBean.setProductPrice(dialog.getInputString());
                            adapter.notifyItemChanged(position);
                        }
                        dialog.dismiss();
                    }, "取消", "确定")
                    .create().show();
            }
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
                .addOnClickListener(R.id.txt_productPrice)
                .setText(R.id.txt_productPrice, CommonUtils.formatNumber(item.getProductPrice()));
            ;
        }
    }
}
