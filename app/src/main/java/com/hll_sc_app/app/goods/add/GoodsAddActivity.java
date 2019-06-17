package com.hll_sc_app.app.goods.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.ImgShowDelBlock;
import com.hll_sc_app.base.widget.ImgUploadBlock;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增商品
 *
 * @author zhuyingsong
 * @date 2019/6/17
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_ADD, extras = Constant.LOGIN_EXTRA)
public class GoodsAddActivity extends BaseLoadActivity implements GoodsAddContract.IGoodsAddView {
    @BindView(R.id.img_imgUrl)
    ImgUploadBlock mImgImgUrl;
    @BindView(R.id.ll_imgUrlSub)
    LinearLayout mLlImgUrlSub;
    @BindView(R.id.img_imgUrlSub)
    ImgUploadBlock mImgImgUrlSub;
    @BindView(R.id.et_productCode)
    EditText mEtProductCode;
    @BindView(R.id.et_productName)
    EditText mEtProductName;
    @BindView(R.id.txt_categoryName)
    TextView mTxtCategoryName;
    @BindView(R.id.txt_categoryName_copy)
    TextView mTxtCategoryNameCopy;
    @BindView(R.id.txt_shopProductCategorySubName)
    TextView mTxtShopProductCategorySubName;
    private GoodsAddPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        // 主图
        mImgImgUrl.setRequestCode(ImgUploadBlock.REQUEST_CODE_IMG_URL);
        // 辅图
        mImgImgUrlSub.setRequestCode(ImgUploadBlock.REQUEST_CODE_IMG_URL_SUB);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        List<String> list = Matisse.obtainPathResult(data);
        if (!CommonUtils.isEmpty(list)) {
            mPresenter.uploadImg(new File(list.get(0)), requestCode);
        }
    }

    @OnClick({R.id.img_close, R.id.rl_categoryName, R.id.rl_shopProductCategorySubName, R.id.txt_categoryName_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.rl_categoryName:
                finish();
                break;
            case R.id.rl_shopProductCategorySubName:
                finish();
                break;
            case R.id.txt_categoryName_copy:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void uploadSuccess(String url, int requestCode) {
        if (requestCode == ImgUploadBlock.REQUEST_CODE_IMG_URL) {
            mImgImgUrl.showImage(url, v -> mImgImgUrl.deleteImage());
        } else if (requestCode == ImgUploadBlock.REQUEST_CODE_IMG_URL_SUB) {
            mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
            ImgShowDelBlock block = new ImgShowDelBlock(this);
            block.setImgUrl(url);
            block.setDeleteListener(v -> {
                mImgImgUrlSub.setVisibility(mLlImgUrlSub.getChildCount() == 4 ? View.GONE : View.VISIBLE);
                mLlImgUrlSub.removeView(block);
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(80), UIUtils.dip2px(80));
            params.rightMargin = UIUtils.dip2px(10);
            mLlImgUrlSub.addView(block, mLlImgUrlSub.getChildCount() - 1, params);
        }
    }

    @OnClick(R.id.txt_categoryName_copy)
    public void onViewClicked() {
    }
}
