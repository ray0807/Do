package com.dobest.ray.raydo.bean;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by wangl01 on 2015/12/25.
 */
public class ChatBean implements Serializable {
    public String chatContent;
    public String address;
    public String to;
    public LatLng mLatLng;

    public ChatBean(String chatContent, int type, String to) {
        this.chatContent = chatContent;
        this.type = type;
        this.to = to;
    }

    public ChatBean() {
    }

    public int type;

    @Override
    public String toString() {
        return "ChatBean{" +
                "chatContent='" + chatContent + '\'' +
                ", mLatLng=" + mLatLng +
                '}';
    }
}
