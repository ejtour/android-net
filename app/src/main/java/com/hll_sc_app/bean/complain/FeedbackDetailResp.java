package com.hll_sc_app.bean.complain;

import java.util.List;

/**
 * 意见反馈详情
 */
public class FeedbackDetailResp {
    private String createTime;
    private String feedbackID;
    private String userName;
    private String userPhone;
    private String groupName;
    private int isAnswer;
    private List<DetailBean> details;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(String feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(int isAnswer) {
        this.isAnswer = isAnswer;
    }

    public List<DetailBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailBean> details) {
        this.details = details;
    }

    public static class DetailBean {
        private boolean isTail;//是否是最末尾的 前端维护的字段
        private String content;
        private String contentImg;
        private String createTime;
        private String groupName;
        private int isAnswer;
        private int type;
        private String subFeedbackId;
        private String userId;
        private String userImg;
        private String userName;

        public boolean isTail() {
            return isTail;
        }

        public void setTail(boolean tail) {
            isTail = tail;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getContentImg() {
            return contentImg;
        }

        public void setContentImg(String contentImg) {
            this.contentImg = contentImg;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getIsAnswer() {
            return isAnswer;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getSubFeedbackId() {
            return subFeedbackId;
        }

        public void setSubFeedbackId(String subFeedbackId) {
            this.subFeedbackId = subFeedbackId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }


}
