package com.dobest.ray.raydo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * MapData封装所有数据类型
 */
public class GenericMapData<T> implements Serializable {

	private static final long serialVersionUID = 2L;

	/* 数据List */
	public List<T> list;

	public Page page;
	
	public List<T> prod;
	
	public List<T> cities;

}
