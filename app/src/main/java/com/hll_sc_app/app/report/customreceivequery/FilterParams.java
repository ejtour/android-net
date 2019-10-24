package com.hll_sc_app.app.report.customreceivequery;

import java.util.ArrayList;
import java.util.List;

public class FilterParams {
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
