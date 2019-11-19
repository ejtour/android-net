package com.hll_sc_app.bean.message;

import java.util.HashMap;

import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.commons.models.IUser;

/**
 * IM 消息
 *
 * @author zhuyingsong
 * @date 2019/3/25
 */
public class IMBean implements IMessage {
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "img";
    private String dataType;
    private String content;
    private int serviceType;
    private String groupID;
    private String sender;
    private String sendTime;
    /**
     * 1采购商app2供应商App 3微信商城4pc商城,5shop
     */
    private int from;

    // message
    private int type;
    private IUser user;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    @Override
    public String getMsgId() {
        return null;
    }

    @Override
    public IUser getFromUser() {
        return user;
    }

    @Override
    public String getTimeString() {
        return sendTime;
    }

    @Override
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return MessageStatus.SEND_SUCCEED;
    }

    @Override
    public String getText() {
        return content;
    }

    @Override
    public String getMediaFilePath() {
        return content;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public String getProgress() {
        return null;
    }

    @Override
    public HashMap<String, String> getExtras() {
        return null;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getServiceType() {
        return serviceType;
    }

    public void setServiceType(int serviceType) {
        this.serviceType = serviceType;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }
}
