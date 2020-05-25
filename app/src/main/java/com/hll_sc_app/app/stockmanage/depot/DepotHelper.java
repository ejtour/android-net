package com.hll_sc_app.app.stockmanage.depot;

import android.content.Context;

import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.delivery.AreaListBean;
import com.hll_sc_app.bean.delivery.CityListBean;
import com.hll_sc_app.bean.delivery.ProvinceListBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/14
 */

public class DepotHelper {
    public static List<ProvinceListBean> getAllProvinceList(Context context) {
        List<AreaBean> areaBeanList = UIUtils.getAreaList(context, false);
        List<ProvinceListBean> provinceList = new ArrayList<>();
        for (AreaBean bean : areaBeanList) {
            ProvinceListBean province = new ProvinceListBean();
            provinceList.add(province);
            province.setProvinceName(bean.getName());
            province.setProvinceCode(bean.getCode());
            List<CityListBean> cityList = new ArrayList<>();
            province.setCityList(cityList);
            int count = 0;
            for (AreaBean.ChildBeanX beanX : bean.getChild()) {
                CityListBean city = new CityListBean();
                cityList.add(city);
                city.setCityCode(beanX.getCode());
                city.setCityName(beanX.getName());
                List<AreaListBean> areaList = new ArrayList<>();
                city.setAreaList(areaList);
                for (AreaBean.ChildBeanX.ChildBean child : beanX.getChild()) {
                    AreaListBean area = new AreaListBean();
                    areaList.add(area);
                    area.setAreaName(child.getName());
                    area.setAreaCode(child.getCode());
                }
                count += areaList.size();
            }
            province.setSelectedNum(count);
        }
        return provinceList;
    }

    /**
     * 获取各省份总的可选数量的映射
     */
    public static Map<String, Integer> getTotalNumMap(Context context) {
        Map<String, Integer> map = new HashMap<>();
        List<AreaBean> areaBeanList = UIUtils.getAreaList(context, false);
        for (AreaBean bean : areaBeanList) {
            int count = 0;
            for (AreaBean.ChildBeanX beanX : bean.getChild()) {
                count += beanX.getChild().size();
            }
            map.put(bean.getCode(), count);
        }
        return map;
    }
}
