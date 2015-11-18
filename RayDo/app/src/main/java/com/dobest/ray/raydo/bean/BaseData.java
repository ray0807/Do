package com.dobest.ray.raydo.bean;


import java.io.Serializable;

/**
 * 父类数据结构 map里面无限添加对象
 */
public class BaseData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /* 标志位 : 0成功, 其他 失败 */
    public int errorCode;

    /* 返回的信息 */
    public String msg;

    /* 总数据结构 */
    public MapData result;
    public Page page;
}