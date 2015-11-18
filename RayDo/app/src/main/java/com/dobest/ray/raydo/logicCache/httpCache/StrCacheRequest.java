package com.dobest.ray.raydo.logicCache.httpCache;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dobest.ray.corelibs.Configuration;

public class StrCacheRequest<T> extends Request<T> {

	/**
	 * Callback for response delivery
	 */
	private Listener<T> mListener = null;

	private Map<String, String> param = new HashMap<String, String>();

	public StrCacheRequest(int method, String url, ErrorListener listener,
			Map<String, String> params, Listener<T> mListener) {
		super(method, url, listener);
		this.param = params;
		this.mListener = mListener;
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String json;
		try {
			json = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
			if (Configuration.isShowNetworkJson())
				Log.e("result", json);
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}

		return (Response<T>) Response.success(json,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return param;
	}

}
