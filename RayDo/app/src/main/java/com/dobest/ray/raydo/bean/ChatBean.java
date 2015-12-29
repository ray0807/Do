package com.dobest.ray.raydo.bean;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by wangl01 on 2015/12/25.
 */
public class ChatBean implements Serializable {
    public String ChatContent;
    public String Address;
    public String To;
    public LatLng MLatLng;

    public ChatBean(String chatContent, int type, String to) {
        this.ChatContent = chatContent;
        this.Type = type;
        this.To = to;
    }

    public ChatBean() {
    }

    public int Type;

    @Override
    public String toString() {
        return "ChatBean{" +
                "chatContent='" + ChatContent + '\'' +
                ", mLatLng=" + MLatLng +
                '}';
    }
}
