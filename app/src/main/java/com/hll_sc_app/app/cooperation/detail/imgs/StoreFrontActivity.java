package com.hll_sc_app.app.cooperation.detail.imgs;

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
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 门头照
 *
 * @author zhuyingsong
 * @date 2019-07-22
 */
@Route(path = RouterConfig.STORE_FRONT_IMAGE, extras = Constant.LOGIN_EXTRA)
public class StoreFrontActivity extends BaseLoadActivity {
    @Autowired(name = "object0")
    String imagePath;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_store_front_image);
        ButterKnife.bind(this);
        mRecyclerView.setAdapter(new ImagesAdapter(Arrays.asList(imagePath.split(","))));
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    private class ImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        ImagesAdapter(@Nullable List<String> data) {
            super(R.layout.item_store_image, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.txt_title, "门头照" + (helper.getLayoutPosition() + 1));
            GlideImageView img = helper.getView(R.id.img);
            img.setImageURL(item);
        }
    }
}
