package com.dobest.ray.raydo.logicCache.httpCache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dobest.ray.corelibs.cache.DataCache;
import com.dobest.ray.corelibs.http.GsonRequest;
import com.dobest.ray.corelibs.http.RequestManager;
import com.dobest.ray.corelibs.http.VolleyErrorHelper;
import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.App;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.utils.CheckNetworkType;
import com.google.gson.Gson;

/**
 * 使用有网络缓存的
 * 
 * @author wangl01
 * 
 * @param <T>
 */
public class HttpVolleyRequestCanCache<T> {
	private BaseLogic.NListener<T> mListener;
	private ErrorListener errorListener;
	private Listener<T> successListener;
	private Activity mAct;
	private boolean isCache = true; // 是否可以缓存
	private boolean isExit = true; // 判断缓存文件是否存在
	private String urlKey;
	private HashMap<String, String> params;
	private int method;

	public HttpVolleyRequestCanCache() {

	}

	public HttpVolleyRequestCanCache(Activity mAct) {
		this.mAct = mAct;
	}

	public HttpVolleyRequestCanCache(Activity mAct, boolean isCache) {
		this.isCache = isCache;
		this.mAct = mAct;
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// 防止网络请求在延时前就已结束，此时缓存已经过期了
			T t = null;
			if (method == Method.GET) {
				t = (T) App.getInstance().getCache()
						.getAsObject(getCacheKey((String) msg.obj));
			} else {
				t = (T) App.getInstance().getCache()
						.getAsObject(getPostCacheKey((String) msg.obj, params));
			}
			mListener.onResponse(t);
		}
	};

	/**
	 * Volley Get方法
	 * 
	 * @param url
	 *            请求地址
	 * @param parentClass
	 *            父类数据结构
	 * @param class1
	 *            子类数据结构
	 * @param listener
	 *            成功监听
	 *            错误监听
	 */
	@SuppressWarnings("unchecked")
	public void HttpVolleyRequestGet(String url, Class<T> parentClass,
			Class<?> class1, BaseLogic.NListener<T> listener, Context context) {
		this.mListener = listener;
		this.urlKey = url;
		this.method = Method.GET;

		/**
		 * 没有网络连接时，请求缓存
		 */
		if (!CheckNetworkType.checkNetworkState(context)) {
			RequestQueue queue = RequestManager.getRequestQueue();
			if (queue.getCache().get(url) != null) {
				// response exists
				String cachedResponse = new String(
						queue.getCache().get(url).data);

				Type objectType;
				if (class1 != null) {
					objectType = type(parentClass, class1);
				} else {
					objectType = parentClass;
				}

				Gson gson = new Gson();
				T t = gson.fromJson(cachedResponse, objectType);

				if (mListener != null) {
					// 返回缓存数据
					mListener.onResponse(t);
				}
			} else {
				mListener.onErrResponse(new VolleyError("获取缓存失败，请连接网络"));
			}
			return;
		}

		GsonRequest<T> request = new GsonRequest<T>(Method.GET, url,
				parentClass, class1, SuccessListener(), ErrorListener());
		RequestManager.getRequestQueue().add(request);

		// 把缓存带出去
		if (isCache) {
			T t = (T) App.getInstance().getCache()
					.getAsObject(getCacheKey(url));
			if (t != null) {
				handler.sendMessageDelayed(handler.obtainMessage(0, url), 300);
			} else {
				isExit = false;
			}
		} else {
			isExit = false;
		}
	}

	/**
	 * Volley Post方法
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            上传的Map数据结构
	 * @param parentClass
	 *            父类数据结构
	 * @param class1
	 *            子类数据结构
	 * @param listener
	 *            成功监听
	 *            错误监听
	 */
	public void HttpVolleyRequestPost(String url,
			HashMap<String, String> params, Class<T> parentClass,
			Class<?> class1, BaseLogic.NListener<T> listener, Context context) {

		mListener = listener;
		/**
		 * 没有网络连接时，请求缓存
		 */
		if (!CheckNetworkType.checkNetworkState(context)) {
			RequestQueue queue = RequestManager.getRequestQueue();
			if (queue.getCache().get(url) != null) {
				// response exists
				String cachedResponse = new String(
						queue.getCache().get(url).data);

				Type objectType;
				if (class1 != null) {
					objectType = type(parentClass, class1);
				} else {
					objectType = parentClass;
				}

				Gson gson = new Gson();
				T t = gson.fromJson(cachedResponse, objectType);

				if (mListener != null) {
					// 返回缓存数据
					mListener.onResponse(t);
				}
			} else {
				ToastMgr.show("获取缓存失败，请连接网络");
			}
			return;
		}

		GsonRequest<T> request = new GsonRequest<T>(Method.POST, url, params,
				parentClass, class1, SuccessListener(), ErrorListener());

		RequestManager.getRequestQueue().add(request);

		this.urlKey = url;
		this.method = Method.POST;
		this.params = params;
		// 把缓存带出去
		if (isCache) {
			@SuppressWarnings("unchecked")
			T t = (T) App.getInstance().getCache()
					.getAsObject(getPostCacheKey(url, params));
			if (t != null) {
				handler.sendMessageDelayed(handler.obtainMessage(0, url), 300);
			} else {
				isExit = false;
			}
		} else {
			isExit = false;
		}

	}

	/**
	 * 返回成功Response
	 * 
	 * @return
	 */
	private Listener<T> SuccessListener() {
		return new Listener<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public void onResponse(T response) {
				if (response == null) {
					return;
				}

				if ("0".equals(((BaseData<T>) response).errorCode)) {
					if (isCache) {
						if (!isExit) {
							if (mListener != null) {
								mListener.onResponse(response);
							}
						}
						if (method == Method.GET) {
							App.getInstance()
									.getCache()
									.put(getCacheKey(urlKey),
											(BaseData<T>) response,
											DataCache.TIME_DAY);
						} else {
							App.getInstance()
									.getCache()
									.put(getPostCacheKey(urlKey, params),
											(BaseData<T>) response,
											DataCache.TIME_DAY);
						}

					} else {
						if (mListener != null) {
							mListener.onResponse(response);
						}
					}
				} else {
					if (mListener != null) {
						mListener.onResponse(response);
					}
				}
			}
		};
	}

	/**
	 * 返回错误Response 集中处理错误提示消息
	 * 
	 * @return
	 */
	private ErrorListener ErrorListener() {
		return new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				if (mAct != null) {
					// toast显示错误信息
					Toast.makeText(mAct,
							VolleyErrorHelper.getMessage(error, mAct),
							Toast.LENGTH_SHORT).show();
				}

				if (mListener != null)
					mListener.onErrResponse(error);
			}
		};
	}

	/**
	 * 完全匹配
	 * 
	 * @param url
	 * @return
	 */
	private String getCacheKey(String url) {
		// if (url.contains("&")) {
		// return url.substring(0, url.indexOf("&"));
		// } else {
		// return url;
		// }
		return url;
	}

	private String getPostCacheKey(String url, HashMap<String, String> map) {
		return url + getParams(map);
	}

	/**
	 * 根据Map 转成parmas字符�?
	 * 
	 * @return
	 */
	public String getParams(HashMap<String, String> map) {
		String str1 = "";
		if (null == map) {
			return str1;
		}
		// 参数为空
		if (map.isEmpty()) {
			return str1;
		}
		Iterator<String> localIterator = map.keySet().iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				return str1.substring(0, -1 + str1.length());
			}
			String str2 = localIterator.next();
			str1 = str1 + str2 + "_" + map.get(str2) + ",";
		}
	}

	static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {

			@Override
			public Type getRawType() {
				return raw;
			}

			@Override
			public Type[] getActualTypeArguments() {
				return args;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
		};
	}
}
