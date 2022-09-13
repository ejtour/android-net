package com.hll_sc_app.api;

import com.hll_sc_app.base.bean.PvBean;
import com.hll_sc_app.base.http.HttpFactory;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.POST;

public interface PvService {

    PvService INSTANCE = HttpFactory.create(PvService.class, "https://5s.22city.cn");

    @POST("/pvDesc")
    Single<List<PvBean>> getPvDescList();
}
