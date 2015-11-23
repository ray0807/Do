package com.dobest.ray.raydo.bean.childBean;

import java.io.Serializable;
import java.util.HashMap;

public class ImageUrls implements Serializable{

	private static final long serialVersionUID = -4182370464414943115L;

	public HashMap<String, String> urls;
	
	public String img1;
	public String img2;
	public String img3;
	public String img4;
	public String img5;
	
	public String status;
	
	public boolean isUrlEmpty(String url){
		return (url == null || url.length() <= 0);
	}
}
