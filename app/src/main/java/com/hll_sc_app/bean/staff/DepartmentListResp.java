package com.hll_sc_app.bean.staff;

import java.util.List;

public class DepartmentListResp {
    private List<DepartmentLisBean> list;
    private int totalSize;

    public List<DepartmentLisBean> getList() {
        return list;
    }

    public void setList(List<DepartmentLisBean> list) {
        this.list = list;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public class DepartmentLisBean {
        private String deptName;
        private String groupID;
        private String id;

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
