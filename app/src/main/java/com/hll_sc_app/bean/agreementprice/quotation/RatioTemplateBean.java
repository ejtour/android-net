package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 比例模板
 *
 * @author zhuyingsong
 * @date 2019-07-10
 */
public class RatioTemplateBean {
    private String actionTime;
    private String createBy;
    private String actionBy;
    private String createTime;
    private String templateName;
    private String categoryCount;
    private String groupID;
    private String templateID;
    private List<CategoryRatioListBean> categoryRatioList;

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(String categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getTemplateID() {
        return templateID;
    }

    public void setTemplateID(String templateID) {
        this.templateID = templateID;
    }

    public List<CategoryRatioListBean> getCategoryRatioList() {
        return categoryRatioList;
    }

    public void setCategoryRatioList(List<CategoryRatioListBean> categoryRatioList) {
        this.categoryRatioList = categoryRatioList;
    }
}
