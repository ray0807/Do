package com.dobest.ray.raydo.bean;

public class Page {

	public int page;
	public int totalpage;
	public int pagesize;

	public Page(int page, int pagesize, int totalpage) {
		this.page = page;
		this.totalpage = totalpage;
		this.pagesize = pagesize;

		this.pageNo = page;
		this.pageSize = pagesize;
		this.totalCount = totalpage;
	}

	public int pageNo;
	public int pageSize;
	public int totalCount;

}
