package com.hll_sc_app.app.marketingsetting.product.add;

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


}
