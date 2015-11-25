package com.dobest.ray.raydo.bean.childBean;

/**
 * Created by wangl01 on 2015/11/25.
 */
public class EventBusBean {
    public String tag;
    public int index;
    public String text;

    public EventBusBean(String tag, String text, int index) {
        this.tag = tag;
        this.index = index;
        this.text = text;
    }
}
