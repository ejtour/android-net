package com.hll_sc_app.bean.complain;

import java.util.List;

public class FeedbackListResp {

    private int totalSize;
    private List<FeedbackBean> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<FeedbackBean> getList() {
        return list;
    }

    public void setList(List<FeedbackBean> list) {
        this.list = list;
    }

    public static class FeedbackBean {
        private String groupName;
        private String createTime;
        private int isAnswerRead;
        private String userPhone;
        private String feedbackID;
        private int source;
        private String userName;
        private String content;
        private String contentImg;
        private int isAnswer;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getIsAnswerRead() {
            return isAnswerRead;
        }

        public void setIsAnswerRead(int isAnswerRead) {
            this.isAnswerRead = isAnswerRead;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getFeedbackID() {
            return feedbackID;
        }

        public void setFeedbackID(String feedbackID) {
            this.feedbackID = feedbackID;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public int getIsAnswer() {
            return isAnswer;
        }

        public void setIsAnswer(int isAnswer) {
            this.isAnswer = isAnswer;
        }
    }
}
