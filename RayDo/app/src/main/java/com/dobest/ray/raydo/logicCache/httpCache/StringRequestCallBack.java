package com.dobest.ray.raydo.logicCache.httpCache;

import com.android.volley.VolleyError;

public interface StringRequestCallBack {

	public void onStrResponse(String str);

	public void onStrResponseError(VolleyError error);

}
