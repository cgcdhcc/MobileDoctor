package com.imedical.im.entity;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class MessageInfo extends LitePalSupport implements Serializable {
    public int sended=0;//0未发送，1已发送
    public int status=0;//未读
    public String messageType;//":"txt",
    public String content;//":"滚粗",
    public String fromUser;//":"15140281443",
    public String toUser;//":"dashan",
    public String uuid;//":"abaa28be-1a77-4427-ff6c-2687e9b3d1eb",
    public String timeStamp;//":1532508974318,
    public String appId;//":"a5cf64bb-6df2-11e8-ae82-00163e036ce4",
    public boolean success;//":true}
    public String contactId;
    public String fileName;//: '9275aaac-17ae-48b6-b7f9-d697d5cbca7d.jpeg',
    public String fileRemotePath;//: 'http://47.104.229.18:5555/9275aaac-17ae-48b6-b7f9-d697d5cbca7d.jpeg',
    public String originalName;//: '24.jpeg',
    public String thumbnailRemotePath;//: 'http://47.104.229.18:5555/s_9275aaac-17ae-48b6-b7f9-d697d5cbca7d.jpeg',
    public MessageInfo(String messageType,String content,String fromUser,String toUser,String timeStamp,String fileRemotePath,String thumbnailRemotePath){
            this.messageType=messageType;
            this.content=content;
            this.fromUser=fromUser;
            this.toUser=toUser;
            this.timeStamp=timeStamp;
            this.fileRemotePath=fileRemotePath;
            this.thumbnailRemotePath=thumbnailRemotePath;
            this.sended=0;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileRemotePath() {
        return fileRemotePath;
    }

    public void setFileRemotePath(String fileRemotePath) {
        this.fileRemotePath = fileRemotePath;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getThumbnailRemotePath() {
        return thumbnailRemotePath;
    }

    public void setThumbnailRemotePath(String thumbnailRemotePath) {
        this.thumbnailRemotePath = thumbnailRemotePath;
    }

    public int getSended() {
        return sended;
    }

    public void setSended(int sended) {
        this.sended = sended;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
