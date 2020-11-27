package com.hll_sc_app.app.search;


import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;

import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.window.NameValue;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class ISearchContract {
    public interface ISearchView extends ILoadView {
        void refreshSearchData(List<NameValue> list);

        default Bundle getExtra() {
            return null;
        }
    }

    public interface ISearchPresenter extends IPresenter<ISearchView> {
        void requestSearch(String searchWords);
    }

    public interface ISearchStrategy {
        default ISearchPresenter getSearchPresenter() {
            return null;
        }

        String getEditHint();

        @LayoutRes
        default int getLayoutRes() {
            return R.layout.item_search;
        }

        default String getEmptyTip() {
            return getEditHint();
        }

        @DrawableRes
        default int getEmptyImage() {
            return R.drawable.ic_empty_shop_view;
        }

        /**
         * 触发搜索方式：如果为true，点击搜索按钮，取搜索结果列表的第一个
         * 默认为false，则直接取输入词。
         * 场景：比如搜索供应商，传的是id不是name，这时直接点击搜索按钮，而不是点击列表，搜索请求只能获取name，而没有id。
         * @return
         */
        default boolean isSearchByResult() {
            return false;
        }
    }
}
