package com.hll_sc_app.app.marketingsetting.product.add;

import com.hll_sc_app.api.MarketingSettingService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddReq;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddResp;

import java.util.ArrayList;
import java.util.List;


public class ProductMarketingAddPresenter implements IProductMarketingAddContract.IPresenter {
    private IProductMarketingAddContract.IView mView;


    static public ProductMarketingAddPresenter newInstance() {
        return new ProductMarketingAddPresenter();
    }

    @Override
    public void start() {
    }

    @Override
    public void register(IProductMarketingAddContract.IView view) {
        mView = view;
    }

    @Override
    public void addMarketingProduct() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        MarketingProductAddReq addReq = new MarketingProductAddReq();
        addReq.setGroupID(userBean.getGroupID());
        addReq.setAreaList(mView.getAreaList());
        addReq.setAreaScope(mView.getAreaScope());
        addReq.setDiscountName(mView.getMarketingTheme());
        addReq.setDiscountStartTime(mView.getStartTime());
        addReq.setDiscountEndTime(mView.getEndTime());
        addReq.setProductList(processProductBean(mView.getProducts()));
        addReq.setRuleList(mView.getRuleList());
        addReq.setDiscountType(mView.getDiscountType());
        addReq.setCustomerScope(mView.getCustomerScope());
        addReq.setDiscountStage(mView.getDiscountStage());
        addReq.setDiscountRuleType(mView.getRuleType());

        productAddRespObservable(addReq, new SimpleObserver<MarketingProductAddResp>(mView) {
            @Override
            public void onSuccess(MarketingProductAddResp marketingProductAddResp) {
                mView.addSuccess(marketingProductAddResp);
            }
        });
    }

    /**
     * 将SkuGoodsBean转为请求接口里的格式：AddProductBean
     *
     * @param skuGoodsBeans
     * @return
     */
    private List<MarketingProductAddReq.AddProductBean> processProductBean(List<SkuGoodsBean> skuGoodsBeans) {
        List<MarketingProductAddReq.AddProductBean> addProductBeans = new ArrayList<>();
        for (SkuGoodsBean skuGoodsBean : skuGoodsBeans) {
            MarketingProductAddReq.AddProductBean addProductBean = new MarketingProductAddReq.AddProductBean();
            addProductBean.setProductID(skuGoodsBean.getProductID());
            addProductBean.setSpecID(skuGoodsBean.getSpecID());
            addProductBean.setPromoteNum(skuGoodsBean.getPromoteNum());
            addProductBean.setNonRefund(skuGoodsBean.getNonRefund());
            addProductBeans.add(addProductBean);
        }
        return addProductBeans;
    }

    /**
     * 新增促销
     *
     * @return
     */
    public static void productAddRespObservable(MarketingProductAddReq req, SimpleObserver<MarketingProductAddResp> observer) {
        BaseReq<MarketingProductAddReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .addProductMarketing(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);

    }

    @Override
    public void modifyMarketingProduct() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        MarketingProductAddReq addReq = new MarketingProductAddReq();
        addReq.setId(mView.getId());
        addReq.setGroupID(userBean.getGroupID());
        addReq.setAreaList(mView.getAreaList());
        addReq.setAreaScope(mView.getAreaScope());
        addReq.setDiscountName(mView.getMarketingTheme());
        addReq.setDiscountStartTime(mView.getStartTime());
        addReq.setDiscountEndTime(mView.getEndTime());
        addReq.setProductList(processProductBean(mView.getProducts()));
        addReq.setRuleList(mView.getRuleList());
        addReq.setDiscountType(mView.getDiscountType());
        addReq.setCustomerScope(mView.getCustomerScope());
        addReq.setDiscountStage(mView.getDiscountStage());
        addReq.setDiscountRuleType(mView.getRuleType());

        productModifyRespObservable(addReq, new SimpleObserver<MarketingProductAddResp>(mView) {
            @Override
            public void onSuccess(MarketingProductAddResp marketingProductAddResp) {
                mView.modifySuccess(marketingProductAddResp);
            }
        });
    }

    /**
     * 新增促销
     *
     * @return
     */
    public static void productModifyRespObservable(MarketingProductAddReq req, SimpleObserver<MarketingProductAddResp> observer) {
        BaseReq<MarketingProductAddReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        MarketingSettingService.INSTANCE
                .modifyMarketingDetail(baseReq)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);

    }
}
