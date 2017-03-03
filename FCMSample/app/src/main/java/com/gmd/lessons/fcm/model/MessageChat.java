package com.gmd.lessons.fcm.model;

/**
 * Created by emedinaa on 03/03/17.
 */

public class MessageChat {

    private String value;
    private int friendId;
    private String friendName;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "MessageChat{" +
                "value='" + value + '\'' +
                ", friendId=" + friendId +
                ", friendName='" + friendName + '\'' +
                '}';
    }
}
