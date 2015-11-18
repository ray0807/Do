package com.dobest.ray.raydo.logicCache;

import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.raydo.logicCache.httpCache.StringRequestCallBack;

import java.util.HashMap;


/**
 * 网络请求封装类, 可以使用链式方式调用
 * 
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 * 
 * @author ZhuFan
 * @Date: 2015-6-1
 * @param <T>
 */
public class NetworkParamsCache<T> {
	/** 请求参数 */
	private HashMap<String, String> params = new HashMap<String, String>();
	/** 请求地址 */
	private String url;
	/** 解析基类 */
	private Class<T> baseClass;
	/** 解析子类 */
	private Class<T> childClass;
	/** 请求回调 */
	private BaseLogic.NListener<T> listener;
	private StringRequestCallBack strListener;
	private BaseLogic.OnEmulatingData dataListener;

	/**
	 * 重置请求参数, 不会重置URL等其他参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:34:13
	 */
	public NetworkParamsCache<T> resetParams() {
		if (params != null)
			params.clear();
		return this;
	}

	/**
	 * 重置所有网络请求参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:35:18
	 */
	public NetworkParamsCache<T> resetAll() {
		if (params != null)
			params.clear();
		url = "";
		listener = null;
		return this;
	}

	/**
	 * 获取请求参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return HashMap<String,String>
	 * @throws
	 * @Date 2015-6-1 下午3:36:01
	 */
	public HashMap<String, String> getParams() {
		setEmulating(null);
		return params;
	}

	public NetworkParamsCache<T> setEmulating(BaseLogic.OnEmulatingData s) {
		dataListener = s;
		return this;
	}

	public BaseLogic.OnEmulatingData getEmulating() {
		return dataListener;
	}

	/**
	 * 替换请求参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param params
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:37:28
	 */
	public NetworkParamsCache<T> setParams(HashMap<String, String> params) {
		this.params = params;
		return this;
	}

	/**
	 * 压入请求参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param key
	 * @param value
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:37:41
	 */
	public NetworkParamsCache<T> addParam(String key, String value) {
		if (params == null)
			params = new HashMap<String, String>();
		params.put(key, value);
		return this;
	}
	

	/**
	 * 获取网络地址
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return String
	 * @throws
	 * @Date 2015-6-1 下午3:37:51
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置网络地址
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param url
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:37:58
	 */
	public NetworkParamsCache<T> setUrl(String url) {
		this.url = url;
		return this;
	}

	/**
	 * 获取解析基类
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return Class<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:05
	 */
	public Class<T> getBaseClass() {
		return baseClass;
	}

	/**
	 * 设置解析基类
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param baseClass
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:12
	 */
	public NetworkParamsCache<T> setBaseClass(Class baseClass) {
		this.baseClass = baseClass;
		return this;
	}

	/**
	 * 获取解析子类
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return Class<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:18
	 */
	public Class<T> getChildClass() {
		return childClass;
	}

	/**
	 * 设置解析子类
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param childClass
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:27
	 */
	public NetworkParamsCache<T> setChildClass(Class childClass) {
		this.childClass = childClass;
		return this;
	}

	/**
	 * 获取请求回调
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return NListener<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:33
	 */
	public BaseLogic.NListener<T> getListener() {
		return listener;
	}

	/**
	 * 设置请求回调
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param listener
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:43
	 */
	public NetworkParamsCache<T> setListener(BaseLogic.NListener<T> listener) {
		this.listener = listener;
		return this;
	}

	/**
	 * 设置请求回调
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param listener
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:38:43
	 */
	public NetworkParamsCache<T> setStrListener(StringRequestCallBack listener) {
		this.strListener = listener;
		return this;
	}

	public StringRequestCallBack getStrListener() {
		return strListener;
	}

	public String getGetUrl() {
		StringBuffer getUrl = new StringBuffer(url);
		if (params.size() == 0) {
			return getUrl.toString();
		}
		getUrl.append("?");
		for (String key : params.keySet()) {
			getUrl.append(key).append("=").append(params.get(key)).append("&");
		}
		return getUrl.subSequence(0, getUrl.length() - 1).toString();
	}

}
