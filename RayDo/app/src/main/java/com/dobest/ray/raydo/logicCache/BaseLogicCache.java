package com.dobest.ray.raydo.logicCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.dobest.ray.corelibs.Configuration;
import com.dobest.ray.raydo.constants.Constant;
import com.dobest.ray.raydo.logicCache.httpCache.HttpVolleyRequestCanCache;
import com.dobest.ray.raydo.logicCache.httpCache.HttpVolleyStrRequestCanCache;
import com.dobest.ray.raydo.utils.StringUtils;


/**
 * 基础逻辑, 内部包含一个NetworkParams, 通过edit()方法来设置网络参数,
 * 通过doPost()/doGet()发送网络请求
 * 
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 * 
 * @author ZhuFan
 * @Date: 2015-6-1
 * @param <T>
 */
public class BaseLogicCache<T> {
	private Context context;

	private NetworkParamsCache<T> params = new NetworkParamsCache<T>();

	public BaseLogicCache() {
	}

	/**
	 * 获取网络参数并编辑
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return
	 * @return NetworkParams<T>
	 * @throws
	 * @Date 2015-6-1 下午3:40:02
	 */
	public NetworkParamsCache<T> edit(Context context) {
		this.context = context;
		return params;
	}

	/**
	 * 强制设置网络参数
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @param params
	 * @return void
	 * @throws
	 * @Date 2015-6-1 下午3:40:22
	 */
	public void setParams(NetworkParamsCache<T> params) {
		this.params = params;
	}

	/**
	 * 发送post请求
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return void
	 * @throws
	 * @Date 2015-6-1 下午3:40:33
	 */
	@SuppressWarnings("unchecked")
	public void doPost() {
		if (params.getEmulating() != null) {
			T resObject = (T) params.getEmulating().onEmulating();
			params.getListener().onResponse(resObject);
		} else {
			HashMap<String, String> paramsAdd = params.getParams();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			paramsAdd.put(
					"authKey",
					StringUtils.md5(Constant.AUTH_KEY
							+ sf.format(new Date(System.currentTimeMillis()))));
			HttpVolleyRequestCanCache<T> request = new HttpVolleyRequestCanCache<T>();
			request.HttpVolleyRequestPost(params.getUrl(), paramsAdd/*
																	 * params.
																	 * getParams
																	 * ()
																	 */,
					params.getBaseClass(), params.getChildClass(),
					params.getListener(), context);

			if (Configuration.isShowNetworkParams()) {
				Log.e("URL", params.getUrl());
				Log.e("params", params.getParams().toString());
			}
		}
	}

	/**
	 * 发送get请求
	 * 
	 * @author ZhuFan
	 * @Package com.bm.corelibs.logic
	 * @return void
	 * @throws
	 * @Date 2015-6-1 下午3:40:41
	 */
	public void doGet() {
		HashMap<String, String> paramsAdd = params.getParams();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		paramsAdd.put(
				"authKey",
				StringUtils.md5(Constant.AUTH_KEY
						+ sf.format(new Date(System.currentTimeMillis()))));
		params.setParams(paramsAdd);
		HttpVolleyRequestCanCache<T> request = new HttpVolleyRequestCanCache<T>();
		request.HttpVolleyRequestGet(params.getGetUrl(), params.getBaseClass(),
				params.getChildClass(), params.getListener(), context);

	}

	/**
	 * 返回原始数据，json格式数据
	 */
	public void doStrGet() {
		HttpVolleyStrRequestCanCache<T> request = new HttpVolleyStrRequestCanCache<T>();
		request.HttpVolleyRequestStrGet(params.getGetUrl(),
				params.getStrListener(), context);
	}

	/**
	 * 返回原始数据，json格式数据
	 */
	public void doStrPost() {
		HttpVolleyStrRequestCanCache<T> request = new HttpVolleyStrRequestCanCache<T>();
		request.HttpVolleyRequestStrPost(params.getUrl(), params.getParams(),
				params.getStrListener(), context);
	}

	/**
	 * 网络请求回调函数
	 * 
	 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
	 * 
	 * @author ZhuFan
	 * @Date: 2015-6-1
	 * @param <T>
	 */
//	public interface NListener<T> {
//		/**
//		 * 请求成功时调用此回调
//		 *
//		 * @author ZhuFan
//		 * @Package com.bm.corelibs.logic
//		 * @param data
//		 *            解析后的数据
//		 * @return void
//		 * @throws
//		 * @Date 2015-6-1 下午3:39:04
//		 */
//		void onResponse(T data);
//
//		/**
//		 * 网络请求失败时调用此回调
//		 *
//		 * @author ZhuFan
//		 * @Package com.bm.corelibs.logic
//		 * @param error
//		 * @return void
//		 * @throws
//		 * @Date 2015-6-1 下午3:39:26
//		 */
//		void onErrResponse(VolleyError error);
//	}
//
//	public interface OnEmulatingData {
//		Object onEmulating();
//	}
}
