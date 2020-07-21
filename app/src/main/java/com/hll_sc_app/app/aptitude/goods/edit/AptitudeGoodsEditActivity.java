package com.hll_sc_app.app.aptitude.goods.edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.AptitudePresenter;
import com.hll_sc_app.app.aptitude.IAptitudeCallback;
import com.hll_sc_app.app.aptitude.IAptitudeContract;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.bean.aptitude.AptitudeReq;
import com.hll_sc_app.bean.aptitude.AptitudeTypeBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.aptitude.AptitudeListView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/10
 */
@Route(path = RouterConfig.APTITUDE_GOODS_EDIT)
public class AptitudeGoodsEditActivity extends BaseLoadActivity implements IAptitudeCallback, IAptitudeContract.IAptitudeView {
    public static final int REQ_CODE = 0x6;
    @BindView(R.id.age_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.age_list_view)
    AptitudeListView mListView;
    @Autowired(name = "parcelable")
    GoodsBean mGoodsBean;
    private IAptitudeContract.IAptitudePresenter mPresenter;
    private boolean mEditable;
    private boolean mHasChanged;
    private boolean mIsFirst = true;
    private List<AptitudeBean> mRawList = new ArrayList<>();

    public static void start(Activity activity, GoodsBean goodsBean) {
        RouterUtil.goToActivity(RouterConfig.APTITUDE_GOODS_EDIT, activity, REQ_CODE, goodsBean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_aptitude_goods_edit);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null
                && requestCode == ImgUploadBlock.REQUEST_CODE_CHOOSE) {
            List<String> list = Matisse.obtainPathResult(data);
            if (!CommonUtils.isEmpty(list)) mPresenter.upload(list.get(0));
        }
    }

    private void initData() {
        mPresenter = AptitudePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mTitleBar.setRightBtnClick(v -> rightClick());
        mListView.setListPaddingTop(UIUtils.dip2px(10));
        View header = View.inflate(this, R.layout.item_aptitude_goods, null);
        header.setBackgroundResource(R.drawable.base_bg_white_radius_5_solid);
        GlideImageView goodsImage = header.findViewById(R.id.iag_image);
        TextView name = header.findViewById(R.id.iag_name);
        TextView spec = header.findViewById(R.id.iag_spec);
        TextView code = header.findViewById(R.id.iag_code);
        goodsImage.setImageURL(mGoodsBean.getImgUrl());
        name.setText(mGoodsBean.getProductName());
        code.setText(String.format("编码：%s", mGoodsBean.getProductCode()));
        spec.setText(String.format("规格：%s种", mGoodsBean.getSaleSpecNum()));
        mListView.setHeaderView(header);
    }

    @Override
    public void rightClick() {
        if (mEditable) {
            AptitudeReq req = new AptitudeReq();
            req.setGroupID(UserConfig.getGroupID());
            req.setProductID(mGoodsBean.getProductID());
            req.setAptitudeList(mListView.getList());
            mPresenter.save(req);
        } else if (mListView.getTypeList() == null) {
            mPresenter.getTypeList();
        } else {
            setEditable(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (hasChanged(mRawList, mListView.getList())) {
            SuccessDialog.newBuilder(this)
                    .setCancelable(false)
                    .setImageTitle(R.drawable.ic_dialog_failure)
                    .setImageState(R.drawable.ic_dialog_state_failure)
                    .setMessageTitle("确认返回上级页面么")
                    .setMessage("当前页面已进行数据操作\n直接返回上级页面将导致数据丢失")
                    .setButton((dialog, item) -> {
                        if (item == 1) {
                            close();
                        }
                        dialog.dismiss();
                    }, "点错了", "确认返回")
                    .create().show();
        } else {
            close();
        }
    }

    private boolean hasChanged(List<AptitudeBean> oldList, List<AptitudeBean> newList) {
        if (CommonUtils.isEmpty(oldList)) {
            return !CommonUtils.isEmpty(newList);
        } else if (CommonUtils.isEmpty(newList) || oldList.size() > newList.size()) {
            return true;
        } else {
            return !oldList.containsAll(newList);
        }
    }

    private void close() {
        if (mHasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public boolean isEditable() {
        return mEditable;
    }

    @Override
    public void setEditable(boolean editable) {
        mEditable = editable;
        mListView.setEditable(editable);
        mTitleBar.setRightText(editable ? "保存" : "编辑");
    }

    @Override
    public void setImageUrl(String url) {
        mListView.setImageUrl(url);
    }

    @Override
    public void setData(List<AptitudeBean> list) {
        if (mIsFirst) {
            mIsFirst = false;
            if (CommonUtils.isEmpty(list)) {
                mPresenter.getTypeList();
            }
        }
        mRawList.clear();
        if (!CommonUtils.isEmpty(list)) {
            for (AptitudeBean bean : list) {
                mRawList.add((AptitudeBean) bean.clone());
            }
        }
        mListView.setList(list);
    }

    @Override
    public void cacheTypeList(List<AptitudeTypeBean> list) {
        mListView.cacheTypeList(list);
        setEditable(true);
    }

    @Override
    public int getType() {
        return 2;
    }

    @Override
    public void saveSuccess() {
        setEditable(false);
        mHasChanged = true;
        mPresenter.start();
    }

    @Override
    public String getProductID() {
        return mGoodsBean.getProductID();
    }
}
