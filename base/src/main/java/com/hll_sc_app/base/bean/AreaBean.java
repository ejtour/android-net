package com.hll_sc_app.base.bean;

import java.util.List;

/**
 * 地区数据Bean
 *
 * @author zys
 * @date 2018/87
 */
public class AreaBean {
    private int areaNumQueryByProince;
    private int cityNumQueryByProince;
    private String code;
    private String name;
    private List<ChildBeanX> child;

    public int getAreaNumQueryByProince() {
        return areaNumQueryByProince;
    }

    public void setAreaNumQueryByProince(int areaNumQueryByProince) {
        this.areaNumQueryByProince = areaNumQueryByProince;
    }

    public int getCityNumQueryByProince() {
        return cityNumQueryByProince;
    }

    public void setCityNumQueryByProince(int cityNumQueryByProince) {
        this.cityNumQueryByProince = cityNumQueryByProince;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBeanX> getChild() {
        return child;
    }

    public void setChild(List<ChildBeanX> child) {
        this.child = child;
    }

    public static class ChildBeanX {
        private int areaNumQueryByCity;
        private String code;
        private String name;
        private List<ChildBean> child;

        public int getAreaNumQueryByCity() {
            return areaNumQueryByCity;
        }

        public void setAreaNumQueryByCity(int areaNumQueryByCity) {
            this.areaNumQueryByCity = areaNumQueryByCity;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        public static class ChildBean {
            private String code;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
