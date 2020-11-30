package com.hll_sc_app.app.aptitude.type;

import android.text.TextUtils;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aptitude.AptitudeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Aptitude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/11/26
 */
class AptitudeTypePresenter implements IAptitudeTypeContract.IAptitudeTypePresenter {
    private IAptitudeTypeContract.IAptitudeTypeView mView;
    private final int mDataType;

    public static AptitudeTypePresenter newInstance(int dataType) {
        return new AptitudeTypePresenter(dataType);
    }

    private AptitudeTypePresenter(int dataType) {
        mDataType = dataType;
    }

    @Override
    public void edit(String id, String name) {
        Aptitude.editAptitudeType(id, mDataType, name, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                start();
            }

            @Override
            public void onFailure(UseCaseException e) {
                if (TextUtils.equals("00120113118", e.getCode())) {
                    mView.delFailure();
                } else {
                    super.onFailure(e);
                }
            }
        });
    }

    @Override
    public void start() {
        Aptitude.queryAptitudeTypeList(mDataType, "", new SimpleObserver<List<AptitudeBean>>(mView) {
            @Override
            public void onSuccess(List<AptitudeBean> aptitudeBeans) {
                List<AptitudeBean> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(aptitudeBeans)) {
                    for (AptitudeBean bean : aptitudeBeans) {
                        if ("0".equals(bean.getAptitudeType())) {
                            continue;
                        }
                        list.add(bean);
                    }
                }
                mView.setData(list);
            }
        });
    }

    @Override
    public void register(IAptitudeTypeContract.IAptitudeTypeView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
