package com.hll_sc_app.bean.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageDetailBean implements Parcelable {
    private int readStatus;
    private String serviceType;
    private String actionTime;
    private String messageTypeCode;
    private String groupID;
    private String messageTitle;
    private String source;
    private String imgUrl;
    private String score;
    private int jumpTarget;
    private String odmId;
    private int action;
    private String id;
    private String serviceID;
    private String messageContent;
    private int messageSrc;
    private String extGroupName;
    private String content;
    private List<FileAttachment> fileInfoList;

    protected MessageDetailBean(Parcel in) {
        readStatus = in.readInt();
        serviceType = in.readString();
        actionTime = in.readString();
        messageTypeCode = in.readString();
        groupID = in.readString();
        messageTitle = in.readString();
        source = in.readString();
        imgUrl = in.readString();
        score = in.readString();
        jumpTarget = in.readInt();
        odmId = in.readString();
        action = in.readInt();
        id = in.readString();
        serviceID = in.readString();
        messageContent = in.readString();
        messageSrc = in.readInt();
        extGroupName = in.readString();
        content = in.readString();
        fileInfoList = in.createTypedArrayList(FileAttachment.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(readStatus);
        dest.writeString(serviceType);
        dest.writeString(actionTime);
        dest.writeString(messageTypeCode);
        dest.writeString(groupID);
        dest.writeString(messageTitle);
        dest.writeString(source);
        dest.writeString(imgUrl);
        dest.writeString(score);
        dest.writeInt(jumpTarget);
        dest.writeString(odmId);
        dest.writeInt(action);
        dest.writeString(id);
        dest.writeString(serviceID);
        dest.writeString(messageContent);
        dest.writeInt(messageSrc);
        dest.writeString(extGroupName);
        dest.writeString(content);
        dest.writeTypedList(fileInfoList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageDetailBean> CREATOR = new Creator<MessageDetailBean>() {
        @Override
        public MessageDetailBean createFromParcel(Parcel in) {
            return new MessageDetailBean(in);
        }

        @Override
        public MessageDetailBean[] newArray(int size) {
            return new MessageDetailBean[size];
        }
    };

    public void preProcess() {
        actionTime = DateUtil.getReadableTime(actionTime, Constants.SLASH_YYYY_MM_DD_HH_MM_SS);
        content = UIUtils.replaceBlank(UIUtils.delHTMLTag(messageContent));
    }

    public List<FileAttachment> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileAttachment> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getMessageTypeCode() {
        return messageTypeCode;
    }

    public void setMessageTypeCode(String messageTypeCode) {
        this.messageTypeCode = messageTypeCode;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getJumpTarget() {
        return jumpTarget;
    }

    public void setJumpTarget(int jumpTarget) {
        this.jumpTarget = jumpTarget;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public int getMessageSrc() {
        return messageSrc;
    }

    public void setMessageSrc(int messageSrc) {
        this.messageSrc = messageSrc;
    }

    public String getExtGroupName() {
        return extGroupName;
    }

    public void setExtGroupName(String extGroupName) {
        this.extGroupName = extGroupName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
