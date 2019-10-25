package com.hll_sc_app.app.search;


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
    }
}
