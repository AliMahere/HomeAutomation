package com.google.firebase.udacity.friendlychat;

public class Room {
    private  String roomName;
    private String roomIpAddress;
    private String roomId;
    Room(){}
    Room(String roomName, String roomIpAddress,String roomId){
        this.roomName = roomName;
        this.roomIpAddress = roomIpAddress;
        this.roomId = roomId;

    }

    public String getRoomIpAddress() {
        return roomIpAddress;
    }

    public void setRoomIpAddress(String roomIpAddress) {
        this.roomIpAddress = roomIpAddress;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
