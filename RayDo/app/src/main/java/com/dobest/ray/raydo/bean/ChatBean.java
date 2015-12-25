package com.dobest.ray.raydo.bean;

import com.baidu.mapapi.model.LatLng;

import java.io.Serializable;

/**
 * Created by wangl01 on 2015/12/25.
 */
public class ChatBean implements Serializable{
    public String chatContent;
    public String address;
    public LatLng mLatLng;

    @Override
    public String toString() {
        return "ChatBean{" +
                "chatContent='" + chatContent + '\'' +
                ", mLatLng=" + mLatLng +
                '}';
    }
}
