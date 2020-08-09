package com.example.myclinic;

import java.util.HashMap;
import java.util.Map;

public class Appointment {
    private Map<String, Object> packet = new HashMap<>();

    public Appointment() {
    }

    public Map<String, Object> getPacket() {
        return packet;
    }

    public Appointment(String uid, String doctor, String datentime) {
        packet.put("uid", uid);
        packet.put("doctor", doctor);
        packet.put("datentime", datentime);
    }

    public String getDatentime() {
        return packet.get("datentime").toString();
    }

    public String getDoctor() {
        return packet.get("doctor").toString();
    }

    public String getUid() {
        return packet.get("uid").toString();
    }


    public void setDoctor(String doctor) {
        packet.put("doctor", doctor);
    }


    public void setDatentime(String datentime) {
        packet.put("datentime", datentime);
    }

    public void setUid(String uid) {
        packet.put("uid", uid);
    }


}
