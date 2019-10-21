package com.hll_sc_app.app.report.customreceivequery;

import java.util.ArrayList;
import java.util.List;

public class FilterParams {
    public static List<TypeBean> getFilterTypeList() {
        List<TypeBean> typeBeans = new ArrayList<>();
        typeBeans.add(new TypeBean("验货入库", 1));
        typeBeans.add(new TypeBean("入库冲销", 3));
        typeBeans.add(new TypeBean("入库退货", 4));
        typeBeans.add(new TypeBean("直发单", 13));
        typeBeans.add(new TypeBean("采购验货", 18));
        typeBeans.add(new TypeBean("采购退货", 19));
        typeBeans.add(new TypeBean("直发冲销", 22));
        typeBeans.add(new TypeBean("直发退货", 23));
        typeBeans.add(new TypeBean("赠品入库", 24));
        typeBeans.add(new TypeBean("代仓验收入库单", 30));
        typeBeans.add(new TypeBean("代仓入库冲销单", 31));
        typeBeans.add(new TypeBean("代仓入库退货单", 32));
        typeBeans.add(new TypeBean("司机补货单", 27));
        typeBeans.add(new TypeBean("库存差异调整", 28));
        return typeBeans;
    }

    public static class TypeBean {

        private String typeName;
        private int type;

        public TypeBean(String typeName, int type) {
            this.typeName = typeName;
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class StatusBean {

        private String statusName;
        private int status;

        public StatusBean(String statusName, int status) {
            this.statusName = statusName;
            this.status = status;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
