package com.abisininstagram.instagramcc.instagramcc.models;

import java.util.Date;

/**
 * Created by Camokan on 15.12.2017.
 */

public class Message {
    private String messageText;
    private String messageUser;
    private String messageTime;
    private String messageUser2;
    private String messageUserName;
    private String messageUserName2;

    public Message(String messageText, String messageUser, String messageTime, String messageUser2, String messageUserName, String messageUserName2) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
        this.messageUser2 = messageUser2;
        this.messageUserName = messageUserName;
        this.messageUserName2 = messageUserName2;
    }
    public Message()
    {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageUser2() {
        return messageUser2;
    }

    public void setMessageUser2(String messageUser2) {
        this.messageUser2 = messageUser2;
    }

    public String getMessageUserName() {
        return messageUserName;
    }

    public void setMessageUserName(String messageUserName) {
        this.messageUserName = messageUserName;
    }

    public String getMessageUserName2() {
        return messageUserName2;
    }

    public void setMessageUserName2(String messageUserName2) {
        this.messageUserName2 = messageUserName2;
    }
}
