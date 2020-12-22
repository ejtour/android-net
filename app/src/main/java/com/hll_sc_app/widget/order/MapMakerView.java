package com.hll_sc_app.widget.order;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/17/20.
 */
public class MapMakerView extends LinearLayout {
    @BindView(R.id.vmm_marker)
    TextView mMarker;
    @BindView(R.id.vmm_address)
    TextView mAddress;

    public MapMakerView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        View view = (ViewGroup) View.inflate(context, R.layout.view_map_marker, this);
        ButterKnife.bind(this, view);
    }

    public MapMakerView(Context context, String marker, String address) {
        super(context);
        init(context);
        mMarker.setText(marker);
        mAddress.setText(address);
    }

    public BitmapDescriptor asBitmapDescriptor() {
        setDrawingCacheEnabled(true);
        measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        layout(0, 0,
                getMeasuredWidth(),
                getMeasuredHeight());

        buildDrawingCache();
        Bitmap cacheBitmap = getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
