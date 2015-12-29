package com.dobest.ray.raydo.bean;

/**
 * Created by wangl01 on 2015/12/22.
 */
public class MessageBean {
    public int Type;
    public int Timestamp;
    public String User;
    public String Content;

    public MessageBean(int type, int timestamp, String user, String content) {
        Type = type;
        Timestamp = timestamp;
        User = user;
        Content = content;
    }
    public MessageBean() {

    }
    @Override
    public String toString() {
        return "MessageBean{" +
                "Type=" + Type +
                ", Timestamp=" + Timestamp +
                ", User='" + User + '\'' +
                ", Content='" + Content + '\'' +
                '}';
    }
}
