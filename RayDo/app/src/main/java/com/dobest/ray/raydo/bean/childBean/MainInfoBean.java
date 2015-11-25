package com.dobest.ray.raydo.bean.childBean;

/**
 * Created by wangl01 on 2015/11/25.
 */
public class MainInfoBean {
    public String imageUrl;
    public String text;

    public MainInfoBean(String imageUrl, String text) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
