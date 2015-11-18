package com.dobest.ray.raydo.logicCache;


import com.dobest.ray.corelibs.logic.BaseLogic;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.bean.GenericBaseData;
import com.dobest.ray.raydo.bean.Page;

@SuppressWarnings("rawtypes")
public class BaseManagerCache {
	protected BaseLogicCache<BaseData> logic = new BaseLogicCache<BaseData>();

	protected BaseLogicCache<GenericBaseData> genericLogic = new BaseLogicCache<GenericBaseData>();
	protected Page page = new Page(1, 15, 0);
	protected String token = "";

	public void setToken(String token) {
		this.token = token;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setMaxPage(int max) {
		page.totalpage = max;
	}

	public int getMaxPage() {
		return page.totalpage;
	}

	public int getCurrentPage() {
		return page.page;
	}

	public void getFirst(BaseLogic.NListener<BaseData> l) {
		page.page = 1;
		getList(l);
	}

	public void getNext(BaseLogic.NListener<BaseData> l) {
		page.page++;
		if (page.page <= page.totalpage)
			getList(l);
	}

	public void getGenericFirst(BaseLogic.NListener<GenericBaseData> l) {
		page.page = 1;
		getGenericList(l);
	}

	public void getGenericNext(BaseLogic.NListener<GenericBaseData> l) {
		page.page++;
		if (page.page <= page.totalpage)
			getGenericList(l);
	}

	protected void getList(BaseLogic.NListener<BaseData> l) {
	};

	protected void getGenericList(final BaseLogic.NListener<GenericBaseData> nl) {
	};
}
