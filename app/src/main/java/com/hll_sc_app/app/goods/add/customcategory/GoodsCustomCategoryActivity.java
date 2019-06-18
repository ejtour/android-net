package com.hll_sc_app.app.goods.add.customcategory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义分类
 *
 * @author zhuyingsong
 * @date 2019/6/18
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_CUSTOM_CATEGORY, extras = Constant.LOGIN_EXTRA)
public class GoodsCustomCategoryActivity extends BaseLoadActivity implements GoodsCustomCategoryContract.IGoodsCustomCategoryView {
    @BindView(R.id.recyclerView_level1)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.recyclerView_level2)
    RecyclerView mRecyclerViewLevel2;
    private GoodsCustomCategoryPresenter mPresenter;
    private CustomCategoryAdapter mCustomCategoryAdapter1;
    private CustomCategoryAdapter mCustomCategoryAdapter2;
    private CustomCategoryResp mResp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_custom_category);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsCustomCategoryPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRecyclerViewLevel1.setLayoutManager(new LinearLayoutManager(this));
        mCustomCategoryAdapter1 = new CustomCategoryAdapter(null);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragAndSwipeCallback(mCustomCategoryAdapter1));
        mCustomCategoryAdapter1.enableDragItem(itemTouchHelper, R.id.img_sort, true);
        itemTouchHelper.attachToRecyclerView(mRecyclerViewLevel1);
        mCustomCategoryAdapter1.setOnItemClickListener((adapter, view, position) -> {
            CustomCategoryBean bean = (CustomCategoryBean) adapter.getItem(position);
            if (bean == null || bean.isChecked()) {
                return;
            }
            if (!CommonUtils.isEmpty(mCustomCategoryAdapter1.getData())) {
                for (CustomCategoryBean categoryBean : mCustomCategoryAdapter1.getData()) {
                    categoryBean.setChecked(false);
                }
            }
            bean.setChecked(true);
            mCustomCategoryAdapter2.setNewData(getCustomCategoryList(bean.getId(), mResp));
            mCustomCategoryAdapter1.notifyDataSetChanged();
        });
        mCustomCategoryAdapter1.setOnItemChildClickListener(this::editCustomCategory);
        mRecyclerViewLevel1.setAdapter(mCustomCategoryAdapter1);

        mRecyclerViewLevel2.setLayoutManager(new LinearLayoutManager(this));
        mCustomCategoryAdapter2 = new CustomCategoryAdapter(null);
        ItemTouchHelper itemTouchHelper2 = new ItemTouchHelper(new ItemDragAndSwipeCallback(mCustomCategoryAdapter2));
        mCustomCategoryAdapter2.enableDragItem(itemTouchHelper2, R.id.img_sort, true);
        itemTouchHelper2.attachToRecyclerView(mRecyclerViewLevel2);
        mCustomCategoryAdapter2.setOnItemChildClickListener(this::editCustomCategory);
        mRecyclerViewLevel2.setAdapter(mCustomCategoryAdapter2);
    }

    /**
     * 根据选中的一级分类筛选出二级分类
     *
     * @param id   一级分类 id
     * @param resp resp
     * @return 二级分类数据
     */
    private List<CustomCategoryBean> getCustomCategoryList(String id, CustomCategoryResp resp) {
        List<CustomCategoryBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(id) && resp != null && !CommonUtils.isEmpty(resp.getList3())) {
            for (CustomCategoryBean customCategoryBean : resp.getList3()) {
                if (TextUtils.equals(id, customCategoryBean.getShopCategoryPID())) {
                    list.add(customCategoryBean);
                }
            }
        }
        return list;
    }

    private void editCustomCategory(BaseQuickAdapter adapter, View view, int position) {
        CustomCategoryBean bean = (CustomCategoryBean) adapter.getItem(position);
        if (bean == null) {
            return;
        }
        if (view.getId() == R.id.img_del) {
            showDelNoticeDialog(bean);
        }
    }

    private void showDelNoticeDialog(CustomCategoryBean bean) {
        SuccessDialog.newBuilder(this)
            .setCancelable(false)
            .setImageTitle(R.drawable.ic_dialog_failure)
            .setImageState(R.drawable.ic_dialog_state_failure)
            .setMessageTitle("确定要删除吗？")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.delCustomCategory(bean);
                }
                dialog.dismiss();
            }, "手滑了~", "确定删除")
            .create().show();
    }

    @OnClick({R.id.img_close, R.id.txt_edit, R.id.txt_create_level1, R.id.txt_create_level2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_edit:
                // 编辑
                break;
            case R.id.txt_create_level1:
                // 新建一级分类
                break;
            case R.id.txt_create_level2:
                // 新建二级分类
                break;
            default:
                break;
        }
    }

    @Override
    public void showCustomCategoryList(CustomCategoryResp resp) {
        mResp = resp;
        mCustomCategoryAdapter1.setNewData(resp.getList2());
        if (!CommonUtils.isEmpty(resp.getList2())) {
            CustomCategoryBean item = resp.getList2().get(0);
            item.setChecked(true);
            mCustomCategoryAdapter2.setNewData(getCustomCategoryList(item.getId(), resp));
        } else {
            mCustomCategoryAdapter2.setNewData(null);
        }
    }

    class CustomCategoryAdapter extends BaseItemDraggableAdapter<CustomCategoryBean, BaseViewHolder> {

        CustomCategoryAdapter(@Nullable List<CustomCategoryBean> data) {
            super(R.layout.item_goods_custom_category, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            helper.setText(R.id.txt_categoryName, item.getCategoryName())
                .addOnClickListener(R.id.img_del)
                .setBackgroundColor(R.id.content, getColor(item));
        }

        private int getColor(CustomCategoryBean item) {
            if (TextUtils.equals(item.getCategoryLevel(), "2") && !item.isChecked()) {
                return ContextCompat.getColor(mContext, R.color.color_fafafa);
            }
            return ContextCompat.getColor(mContext, R.color.base_white);
        }
    }
}
