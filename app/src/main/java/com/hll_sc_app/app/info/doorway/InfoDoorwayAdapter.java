package com.hll_sc_app.app.info.doorway;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.ImgUploadBlock;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

public class InfoDoorwayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean mEditable;

    InfoDoorwayAdapter(boolean editable, List<String> data) {
        super(R.layout.item_info_doorway);
        mEditable = editable;
        if (mEditable && data.size() < 4) {
            data.add("");
        }
        setNewData(data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ImgUploadBlock block = helper.getView(R.id.iid_img);
        block.setOnDeleteListener(v -> {
            mData.remove(helper.getAdapterPosition());
        });
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImgUploadBlock block = helper.setText(R.id.iid_no, String.format("门头照%s", mData.indexOf(item) + 1)).getView(R.id.iid_img);
        block.showImage(item);
        block.setEditable(mEditable);
    }

    @Override
    public void addData(@NonNull String data) {
        mData.add(mData.size() - 1, data);
        while (mData.size() >= 5) {
            mData.remove(4);
        }
        notifyDataSetChanged();
    }
}
