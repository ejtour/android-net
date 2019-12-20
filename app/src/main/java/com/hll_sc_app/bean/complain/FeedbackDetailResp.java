package com.hll_sc_app.bean.complain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 意见反馈详情
 */
public class FeedbackDetailResp implements Parcelable {
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

    public static class DetailBean implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte(this.isTail ? (byte) 1 : (byte) 0);
            dest.writeString(this.content);
            dest.writeString(this.contentImg);
            dest.writeString(this.createTime);
            dest.writeString(this.groupName);
            dest.writeInt(this.isAnswer);
            dest.writeInt(this.type);
            dest.writeString(this.subFeedbackId);
            dest.writeString(this.userId);
            dest.writeString(this.userImg);
            dest.writeString(this.userName);
        }

        public DetailBean() {
        }

        protected DetailBean(Parcel in) {
            this.isTail = in.readByte() != 0;
            this.content = in.readString();
            this.contentImg = in.readString();
            this.createTime = in.readString();
            this.groupName = in.readString();
            this.isAnswer = in.readInt();
            this.type = in.readInt();
            this.subFeedbackId = in.readString();
            this.userId = in.readString();
            this.userImg = in.readString();
            this.userName = in.readString();
        }

        public static final Parcelable.Creator<DetailBean> CREATOR = new Parcelable.Creator<DetailBean>() {
            @Override
            public DetailBean createFromParcel(Parcel source) {
                return new DetailBean(source);
            }

            @Override
            public DetailBean[] newArray(int size) {
                return new DetailBean[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.createTime);
        dest.writeString(this.feedbackID);
        dest.writeString(this.userName);
        dest.writeString(this.userPhone);
        dest.writeString(this.groupName);
        dest.writeInt(this.isAnswer);
        dest.writeTypedList(this.details);
    }

    public FeedbackDetailResp() {
    }

    protected FeedbackDetailResp(Parcel in) {
        this.createTime = in.readString();
        this.feedbackID = in.readString();
        this.userName = in.readString();
        this.userPhone = in.readString();
        this.groupName = in.readString();
        this.isAnswer = in.readInt();
        this.details = in.createTypedArrayList(DetailBean.CREATOR);
    }

    public static final Parcelable.Creator<FeedbackDetailResp> CREATOR = new Parcelable.Creator<FeedbackDetailResp>() {
        @Override
        public FeedbackDetailResp createFromParcel(Parcel source) {
            return new FeedbackDetailResp(source);
        }

        @Override
        public FeedbackDetailResp[] newArray(int size) {
            return new FeedbackDetailResp[size];
        }
    };
}
