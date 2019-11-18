package com.hll_sc_app.bean.message;

import com.google.gson.annotations.SerializedName;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/18
 */

public class MessageBean {
    @SerializedName(value = "senderName", alternate = "messageTypeName")
    private String name;
    @SerializedName(value = "unreadCount", alternate = "unreadNum")
    private int unreadCount;
    @SerializedName(value = "senderLogo", alternate = "logoUrl")
    private String imgUrl;
    @SerializedName(value = "lastMsgTime", alternate = "actionTime")
    private String time;
    @SerializedName(value = "lastMsg", alternate = "messageContent")
    private String message;
    private String receiver;
    private String dataType;
    private int source;
    private String receiverLogo;
    private String reveiverName;
    private String topicID;
    private String createTime;
    private String sender;
    private String topic;
    private String topicName;
    private int receiveType;
    private int messageTypeCode;

    public void preProcess() {
        time = DateUtil.getReadableTime(time, Constants.SLASH_YYYY_MM_DD_HH_MM);
        message = UIUtils.isPicture(message) ? "[图片]" : UIUtils.replaceBlank(UIUtils.delHTMLTag(message));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getReceiverLogo() {
        return receiverLogo;
    }

    public void setReceiverLogo(String receiverLogo) {
        this.receiverLogo = receiverLogo;
    }

    public String getReveiverName() {
        return reveiverName;
    }

    public void setReveiverName(String reveiverName) {
        this.reveiverName = reveiverName;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(int receiveType) {
        this.receiveType = receiveType;
    }

    public int getMessageTypeCode() {
        return messageTypeCode;
    }

    public void setMessageTypeCode(int messageTypeCode) {
        this.messageTypeCode = messageTypeCode;
    }
}
