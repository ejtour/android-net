package com.hll_sc_app.app.order.trace;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.bean.order.trace.OrderTraceBean;
import com.hll_sc_app.bean.order.trace.OrderTraceParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.order.MapMakerView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/7
 */
@Route(path = RouterConfig.ORDER_TRACE)
public class OrderTraceActivity extends BaseLoadActivity implements IOrderTraceContract.IOrderTraceView {

    private AMap mAMap;

    /**
     * @param resp 订单信息
     * @param list 订单追踪列表
     */
    public static void start(OrderResp resp, List<OrderTraceBean> list) {
        OrderTraceParam param = new OrderTraceParam(resp, list);
        RouterUtil.goToActivity(RouterConfig.ORDER_TRACE, param);
    }

    @BindView(R.id.aot_title)
    TextView mTitle;
    @BindView(R.id.aot_list_view)
    RecyclerView mListView;
    @BindView(R.id.aot_map_view)
    MapView mMapView;
    @BindView(R.id.aot_header_view)
    LinearLayout mHeaderView;
    @BindViews({R.id.aot_complaint, R.id.aot_div})
    List<View> mComplaintViews;
    @Autowired(name = "parcelable")
    OrderTraceParam mTraceParam;
    private BottomSheetBehavior<RecyclerView> mBehavior;
    private View mHeader;
    private IOrderTraceContract.IOrderTracePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setTranslucent(getWindow(), true);
        StatusBarCompat.setLightStatusBar(getWindow(), true);
        setContentView(R.layout.activity_order_trace);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        initData();
        mPresenter = new OrderTracePresenter(mTraceParam.getPlateNumber(), mTraceParam.getSubBillID());
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    protected void onDestroy() {
        if (mAMap != null) {
            mAMap = null;
            mMapView.onDestroy();
        }
        super.onDestroy();
    }

    private void initData() {
        if (!UserConfig.crm()) {
            List<Integer> noExtras = Arrays.asList(1, 3, 5, 6, 10);
            for (OrderTraceBean bean : mTraceParam.getList()) {
                if (noExtras.contains(bean.getOpType())) continue;
                StringBuilder sb = new StringBuilder();
                if (!TextUtils.isEmpty(bean.getCreateBy())) {
                    sb.append("操作人：").append(bean.getCreateBy());
                }
                if (bean.getDeliverType() == 1 && !TextUtils.isEmpty(mTraceParam.getDriverName()) && bean.getOpType() == 4) {
                    if (sb.length() > 0) sb.append("\n");
                    sb.append("自有物流，").append("司机：").append(mTraceParam.getDriverName())
                            .append("，电话：").append(mTraceParam.getMobilePhone());
                    if (!TextUtils.isEmpty(mTraceParam.getMobilePhone())) {
                        String source = sb.toString();
                        SpannableString ss = new SpannableString(source);
                        ss.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                UIUtils.callPhone(mTraceParam.getMobilePhone());
                            }
                        }, source.lastIndexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        bean.setExtra(ss);
                        continue;
                    }
                }
                if (bean.getDeliverType() == 3 && !TextUtils.isEmpty(mTraceParam.getExpressName()) && bean.getOpType() == 4) {
                    if (sb.length() > 0) sb.append("\n");
                    sb.append("物流公司：").append(mTraceParam.getExpressName())
                            .append("\n物流单号：").append(mTraceParam.getExpressNo());
                }
                if (bean.getDeliverType() == 2 && (bean.getOpType() == 4 || bean.getOpType() == 7)) {
                    if (sb.length() > 0) sb.append("\n");
                    sb.append("采购商已提货");
                }
                if (!TextUtils.isEmpty(mTraceParam.getCancelRole()) && bean.getOpType() == 8) {
                    if (sb.length() > 0) sb.append("\n");
                    sb.append(mTraceParam.getCancelRole()).append("取消订单");
                }
                if (sb.length() > 0) {
                    bean.setExtra(sb);
                }
            }
        }
        mListView.setAdapter(new OrderTraceAdapter(mTraceParam.getList()));
        initHeader();
    }

    private void initView() {
        ((ViewGroup.MarginLayoutParams) mHeaderView.getLayoutParams()).topMargin = UIUtils.dip2px(20) + ViewUtils.getStatusBarHeight(this);
        mBehavior = BottomSheetBehavior.from(mListView);
        mHeader = View.inflate(this, R.layout.view_order_trace_header, null);
        View copy = mHeader.findViewById(R.id.oth_copy);
        if (!TextUtils.isEmpty(mTraceParam.getExpressNo())) {
            copy.setVisibility(View.VISIBLE);
            copy.setOnClickListener(v -> {
                UIUtils.setSysClipboardText(v.getContext(), mTraceParam.getExpressNo());
                showToast("复制成功");
            });
        } else {
            copy.setVisibility(View.GONE);
        }
        hideMap();
        ButterKnife.apply(mComplaintViews, (view, index) -> view.setVisibility(UserConfig.crm() ? View.VISIBLE : View.GONE));
        mTitle.setText(mTraceParam.getTitle());
    }

    private void initHeader() {
        boolean showCard = true;
        if (mTraceParam.getSubBillStatus() > 2 && mTraceParam.getSubBillStatus() < 7 // 已发货，待结算，已签收，已拒收
                && mTraceParam.getDeliverType() != 2) { // 非自提订单
            if (mTraceParam.getDeliverType() == 1) { // 自有配送
                mHeader.<TextView>findViewById(R.id.oth_status_title).setText("供应商自有物流配送");
                if (!TextUtils.isEmpty(mTraceParam.getDriverName())) { // 对接 tms
                    String source = String.format("送货司机：%s，电话：%s", mTraceParam.getDriverName(), mTraceParam.getMobilePhone());
                    SpannableString ss = new SpannableString(source);
                    ss.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            UIUtils.callPhone(mTraceParam.getMobilePhone());
                        }
                    }, source.lastIndexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mHeader.<TextView>findViewById(R.id.oth_status_desc).setText(ss);
                } else if (mTraceParam.getSubBillStatus() == 3) {
                    mHeader.<TextView>findViewById(R.id.oth_status_desc).setText(UserConfig.crm() ? "您的货物正在快马加鞭而来，请耐心等待～" :
                            "对方的货物正在快马加鞭送去，请耐心等待～");
                } else {
                    showCard = false;
                }
            } else if (mTraceParam.getDeliverType() == 3) { // 三方物流
                mHeader.<TextView>findViewById(R.id.oth_status_title).setText(String.format("三方物流：%s", mTraceParam.getExpressName()));
                mHeader.<TextView>findViewById(R.id.oth_status_desc).setText(String.format("物流单号：%s", mTraceParam.getExpressNo()));
            } else {
                showCard = false;
            }
        } else {
            showCard = false;
        }
        OrderTraceAdapter adapter = (OrderTraceAdapter) mListView.getAdapter();
        if (!showCard) {
            adapter.removeAllHeaderView();
        } else if (adapter.getHeaderLayoutCount() == 0) {
            mHeader.<GlideImageView>findViewById(R.id.oth_status_logo).setImageURL(GreenDaoUtils.getUser().getGroupLogoUrl());
            adapter.setHeaderView(mHeader);
        }
    }

    private void hideMap() {
        ((CoordinatorLayout.LayoutParams) mListView.getLayoutParams()).setBehavior(null);
        ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).topMargin = UIUtils.dip2px(65) + ViewUtils.getStatusBarHeight(this);
    }

    @Override
    public void setData(List<LatLng> list) {
        if (CommonUtils.isEmpty(list)) return;
        ((CoordinatorLayout.LayoutParams) mListView.getLayoutParams()).setBehavior(mBehavior);
        ((ViewGroup.MarginLayoutParams) mListView.getLayoutParams()).topMargin = 0;
        mMapView.setVisibility(View.VISIBLE);
        if (mAMap == null) {
            mMapView.onCreate(null);
            mAMap = mMapView.getMap();
            mAMap.getUiSettings().setRotateGesturesEnabled(false);
            mAMap.getUiSettings().setZoomControlsEnabled(false);
        }
        if (!TextUtils.isEmpty(mTraceParam.getLatGaoDe()) && !TextUtils.isEmpty(mTraceParam.getLonGaoDe())) {
            LatLng latLng = new LatLng(CommonUtils.getDouble(mTraceParam.getLatGaoDe()), CommonUtils.getDouble(mTraceParam.getLonGaoDe()));
            list.add(latLng);
        }
        LatLng centerPoint = list.size() > 1 ? list.get(list.size() - 2) : null;
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(centerPoint, list), 100));
        drawPath(list);
    }

    private LatLngBounds getLatLngBounds(LatLng centerPoint, List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (LatLng p : pointList) {
            b.include(p);
            if (centerPoint != null) {
                b.include(new LatLng(centerPoint.latitude * 2 - p.latitude, centerPoint.longitude * 2 - p.longitude));
            }
        }
        return b.build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAMap != null) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAMap != null) {
            mMapView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (mAMap != null) {
            mMapView.onSaveInstanceState(outState);
        }
    }

    private void drawPath(List<LatLng> list) {
        mAMap.addPolyline(new PolylineOptions().
                addAll(list.subList(0, list.size() - 1))
                .width(UIUtils.dip2px(6))
                .color(ContextCompat.getColor(this, R.color.colorPrimary))
                .useGradient(true));

        // 绘制路径起始头
        MarkerOptions startMarkerOption = new MarkerOptions();
        startMarkerOption.position(list.get(list.size() - 1));
        startMarkerOption.draggable(false); // 设置 Marker 可拖动
        startMarkerOption.setFlat(true); // 设置 marker 平贴地图效果
        startMarkerOption.visible(true);
        startMarkerOption.icon(new MapMakerView(this, "收", mTraceParam.getTargetAddress()).asBitmapDescriptor());
        mAMap.addMarker(startMarkerOption);
        // 绘制路径当前头
        MarkerOptions currentMarkerOption = new MarkerOptions();
        currentMarkerOption.position(list.get(list.size() - 2));
        currentMarkerOption.draggable(false);// 设置 Marker 可拖动
        currentMarkerOption.setFlat(true);// 设置 marker 平贴地图效果
        currentMarkerOption.visible(true);
        currentMarkerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_map_car_yellow)));
        //计算汽车的角度
        if (list.size() > 1) {
            double angle = calculateXYAngle(list.get(list.size() - 1).longitude, list.get(list.size() - 1).latitude,
                    list.get(list.size() - 2).longitude, list.get(list.size() - 2).latitude);
            currentMarkerOption.rotateAngle((float) angle + 180);
        }
        Marker currentMarker = mAMap.addMarker(currentMarkerOption);
        //设置汽车的锚点
        currentMarker.setAnchor(0.5f, 0.5f);
    }

    private float calculateXYAngle(double startx, double starty, double endx,
                                   double endy) {
        float tan = (float) (Math.atan(Math.abs((endy - starty)
                / (endx - startx))) * 180 / Math.PI);
        if (endx > startx && endy > starty) // 第一象限
        {
            return -tan;
        } else if (endx > startx && endy < starty) // 第二象限
        {
            return tan;
        } else if (endx < startx && endy > starty) // 第三象限
        {
            return tan - 180;
        } else {
            return 180 - tan;
        }
    }

    @OnClick({R.id.aot_close, R.id.aot_complaint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aot_close:
                finish();
                break;
            case R.id.aot_complaint:
                ComplainMangeAddActivity.start(null, COMPLAIN_MANAGE);
                break;
        }
    }
}
