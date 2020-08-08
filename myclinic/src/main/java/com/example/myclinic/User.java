package com.example.myclinic;

import java.util.HashMap;
import java.util.Map;

public class User {
    Map<String, Object> packet = new HashMap<>();

    public User() {
    }

    public User(String uid, String userPhone, String userName, String userAddress) {
        packet.put("uid", uid);
        packet.put("name", userName);
        packet.put("phoneno", userPhone);
        packet.put("address", userAddress);
    }

    public User(Map<String, Object> packet) {
        this.packet = packet;
    }

    public Object getUid() {
        return packet.get("uid");
    }

    public Object getName() {
        return packet.get("name");
    }

    public Object getPhoneno() {
        return packet.get("phoneno");
    }

    public Object getAddress() {
        return packet.get("address");
    }

    public void setUid(String uid) {
        packet.put("uid", uid);
    }

    public void setName(String name) {
        packet.put("name", name);
    }

    public void setPhoneno(String phoneno) {
        packet.put("phoneno", phoneno);
    }

    public void setAddress(String address) {
        packet.put("address", address);
    }

}

