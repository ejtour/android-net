package com.hll_sc_app.bean.complain;

import java.util.List;

/*投诉状态响应*/
public class ComplainStatusResp {
    private String createTime;
    private int source;
    private int status;
    private List<ReplyBean> replyList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ReplyBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyBean> replyList) {
        this.replyList = replyList;
    }

    public class ReplyBean {
        private String createBy;
        private String createTIme;
        private String reply;
        private int source;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTIme() {
            return createTIme;
        }

        public void setCreateTIme(String createTIme) {
            this.createTIme = createTIme;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }
    }
}
