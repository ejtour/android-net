package com.hll_sc_app.app.cooperation.detail.imgs;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.cooperation.OtherLicensesBean;
import com.hll_sc_app.widget.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 其他证照
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
@Route(path = RouterConfig.STORE_OTHER_LICENSE_IMAGE, extras = Constant.LOGIN_EXTRA)
public class OtherLicenseActivity extends BaseLoadActivity {
    @Autowired(name = "parcelable")
    ArrayList<OtherLicensesBean> imagePaths;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_store_other_license);
        ButterKnife.bind(this);
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.parseColor("#f3f3f3"), UIUtils.dip2px(10)));
        ImagesAdapter adapter = new ImagesAdapter(imagePaths);
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    private class ImagesAdapter extends BaseQuickAdapter<OtherLicensesBean, BaseViewHolder> {

        ImagesAdapter(@Nullable List<OtherLicensesBean> data) {
            super(R.layout.item_other_license_image, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OtherLicensesBean item) {
            helper.setText(R.id.txt_title, "其他证照" + (helper.getLayoutPosition() + 1))
                .setText(R.id.txt_licenseType, getName(item.getOtherLicenseType()));
            GlideImageView img = helper.getView(R.id.img);
            img.setImageURL(item.getOtherLicenseName());
        }

        private String getName(String type) {
            String typeName = null;
            switch (type) {
                case "1":
                    typeName = "税务登记证";
                    break;
                case "2":
                    typeName = "餐饮服务许可证";
                    break;
                case "3":
                    typeName = "食品经营许可证";
                    break;
                case "4":
                    typeName = "组织机构代码证";
                    break;
                case "5":
                    typeName = "民办非企业单位证件照";
                    break;
                case "6":
                    typeName = "其他证件";
                    break;
                default:
                    break;
            }
            return typeName;
        }
    }
}
