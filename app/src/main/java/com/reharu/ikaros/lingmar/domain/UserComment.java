package com.reharu.ikaros.lingmar.domain;

/**
 * Created by Lingmar on 2017/3/22.
 */

public class UserComment {
    private String userName;
    private String content;
    private String commentDateTime;
    private String roomTypeName;

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getCommentDateTime() {
        return commentDateTime;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCommentDateTime(String commentDateTime) {
        this.commentDateTime = commentDateTime;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    @Override
    public String toString() {
        return "UserComment{" +
                "userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", commentDateTime='" + commentDateTime + '\'' +
                ", roomTypeName='" + roomTypeName + '\'' +
                '}';
    }
}
