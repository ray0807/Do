package com.dobest.ray.raydo.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.util.Log;

import com.dobest.ray.corelibs.utils.Tools;
import com.dobest.ray.raydo.bean.childBean.ImageUrls;
import com.dobest.ray.raydo.constants.Urls;

/**
 * 图片上传工具类
 * 
 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
 * 
 * @author ZhuFan
 * @Date: 2015-1-14
 */
@SuppressLint("SdCardPath")
public class ImageUploader {

	/**
	 * 缓存图片文件夹路径
	 */
	public static final String IMAGE_PATH = "/sdcard/xbrc/data/image/";

	/**
	 * 缓存图片基础文件名, 具体文件名为基础文件名+时间戳
	 */
	public static final String IMAGE_BASE_NAME = "tem";

	/**
	 * 缓存图片文件类型
	 */
	public static final String IMAGE_BASE_TYPE = ".jpeg";

	/**
	 * 图片压缩的大小
	 */
	public static final int IMAGE_MAX_SIZE = 720;

	/**
	 * 错误类型, 服务器出错时返回的错误
	 */
	public static final int RESULT_ERR = 0;

	/**
	 * 错误类型, 网络出错时返回的错误
	 */
	public static final int CONNECTION_ERR = 1;

	/**
	 * 成功类型, 成功并有返回数据
	 */
	public static final int RESULT_OK = 200;

	/**
	 * 成功类型, 成功但无返回数据
	 */
	public static final int RESULT_NO_DATA = 201;

	/**
	 * 请求成功回调
	 */
	private OnResultOk ok;

	/**
	 * 请求失败回调
	 */
	private OnResultErr err;

	/**
	 * 区别码, 用于区分网络请求
	 */
	private int key;

	/**
	 * 请求返回的数据
	 */
	private String data;

	/**
	 * 活动, 用于将代码发送到主线程去运行
	 */
	private Activity activity;

	/**
	 * 网络请求地址
	 */
	private String actionUrl = Urls.ROOT_URL + "upload";

	/**
	 * Post请求附带参数
	 */
	private Map<String, String> params;

	/**
	 * 需要上传的文件的集合
	 */
	private Map<String, File> files;

	public ImageUploader(int key, Activity activity) {
		this.key = key;
		this.activity = activity;
	}

	public ImageUploader(Activity activity) {
		this(0, activity);
	}

	public void setUrl(String url) {
		this.actionUrl = url;

	}

	/**
	 * 设置请求成功时的回调
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param listener
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:44:22
	 */
	public void setOnResultOkListener(OnResultOk listener) {
		ok = listener;
	}

	/**
	 * 设置请求失败时的回调
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param listener
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:44:36
	 */
	public void setOnResultErrListener(OnResultErr listener) {
		err = listener;
	}

	/**
	 * 设置区别码
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param key
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:45:10
	 */
	public void setKey(int key) {
		this.key = key;
	}

	/**
	 * 将Bitmap转换成File的方法
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param bmp
	 * @return
	 * @throws IOException
	 * @return File
	 * @throws
	 * @Date 2015-1-14 下午4:46:03
	 */
	@SuppressLint("SdCardPath")
	public File bitmapTofile(Bitmap bmp) throws IOException {
		String filename = IMAGE_BASE_NAME + System.currentTimeMillis()
				+ IMAGE_BASE_TYPE;
		File file = new File(IMAGE_PATH + filename);
		if (!file.exists()) {
			File dir = new File(IMAGE_PATH);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			file.createNewFile();
		}
		CompressFormat format = CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(IMAGE_PATH + filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		boolean result = bmp.compress(format, quality, stream);

		if (result) {
			return file;
		}

		return null;
	}

	/**
	 * 根据传入的maxSize压缩图片
	 * 
	 * @author ZhuFan
	 * @Package com.bm.zhengpinmao.utils
	 * @param bitmap
	 * @param maxSize
	 * @return
	 * @return Bitmap
	 * @throws
	 * @Date 2015-4-22 下午2:01:27
	 */
	public Bitmap compressBitmap(Bitmap bitmap, int maxSize) {
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		if (mWidth <= mHeight) {
			scaleWidth = maxSize / (float) mWidth;
			scaleHeight = maxSize / (float) mHeight;
			scaleWidth = scaleHeight;
		} else {
			scaleWidth = maxSize / (float) mWidth;
			scaleHeight = maxSize / (float) mHeight;
			scaleHeight = scaleWidth;
		}
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);

		bitmap.recycle();
		return newBitmap;
	}

	/**
	 * 压缩图片, 默认720
	 * 
	 * @author ZhuFan
	 * @Package com.bm.zhengpinmao.utils
	 * @param bitmap
	 * @return
	 * @return Bitmap
	 * @throws
	 * @Date 2015-4-22 下午2:01:42
	 */
	public Bitmap compressBitmap(Bitmap bitmap) {
		return compressBitmap(bitmap, IMAGE_MAX_SIZE);
	}

	/**
	 * 将JSON解析成ImageUrls
	 * 
	 * @author ZhuFan
	 * @Package com.bm.zhengpinmao.utils
	 * @param data
	 * @return
	 * @return ImageUrls
	 * @throws
	 * @Date 2015-4-22 下午2:05:59
	 */
	public ImageUrls dataToUrls(String data) {
		ImageUrls urls = new ImageUrls();
		try {
			JSONObject object = new JSONObject(data);
			JSONObject jsonData = object.getJSONObject("data");
			String status = object.getString("errorCode");
			urls.status = status;
			if (status.equals("0")) {
				urls.img1 = jsonData.getString("img1");
				urls.img2 = jsonData.getString("img2");
				urls.img3 = jsonData.getString("img3");
				urls.img4 = jsonData.getString("img4");
				urls.img5 = jsonData.getString("img5");

				HashMap<String, String> urlList = new HashMap<String, String>();
				if (!urls.isUrlEmpty(urls.img1))
					urlList.put("img1", urls.img1);
				if (!urls.isUrlEmpty(urls.img2))
					urlList.put("img2", urls.img2);
				if (!urls.isUrlEmpty(urls.img3))
					urlList.put("img3", urls.img3);
				if (!urls.isUrlEmpty(urls.img4))
					urlList.put("img4", urls.img4);
				if (!urls.isUrlEmpty(urls.img5))
					urlList.put("img5", urls.img5);
				urls.urls = urlList;
			}
		} catch (JSONException e) {
			urls.status = "0";
			e.printStackTrace();
		}
		return urls;
	}

	/**
	 * 清空图片文件缓存
	 * 
	 * @author ZhuFan
	 * @Package com.bm.zhengpinmao.utils
	 * @return void
	 * @throws
	 * @Date 2015-4-23 上午10:21:24
	 */
	public void clearCache() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				File dir = new File(IMAGE_PATH);
				String[] tmps = dir.list();
				if (tmps == null || tmps.length <= 0)
					return;
				for (int i = 0; i < tmps.length; i++) {
					File file;
					if (tmps[i].endsWith(File.separator)) {
						file = new File(IMAGE_PATH + tmps[i]);
					} else {
						file = new File(IMAGE_PATH + File.separator + tmps[i]);
					}
					file.delete();
				}
			}
		}).start();
	}

	/**
	 * 新建一个线程体用于发送post请求, 并使用默认的请求地址
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param files
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:46:23
	 */
	public void post(Map<String, File> files) {
		post(this.actionUrl, null, files);
	}

	/**
	 * 将参数保存并新建一个线程体用于发送post请求, 并使用默认的请求地址
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param params
	 * @param files
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:46:23
	 */
	public void post(Map<String, String> params, Map<String, File> files) {
		post(this.actionUrl, params, files);
	}

	/**
	 * 将参数保存并新建一个线程体用于发送post请求
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @param actionUrl
	 * @param params
	 * @param files
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:46:23
	 */
	public void post(String actionUrl, Map<String, String> params,
			Map<String, File> files) {
		this.actionUrl = actionUrl;
		this.params = params;
		this.files = files;
		if (files != null && files.size() > 0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					post();
				}
			}).start();
		} else {
			if (ok != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ok.onResultOk(key, RESULT_NO_DATA, "未找到需要上传的图片");
					}
				});
			}
		}
	}

	/**
	 * 发送post请求
	 * 
	 * @author ZhuFan
	 * @Package com.bm.chengkai.utils
	 * @return void
	 * @throws
	 * @Date 2015-1-14 下午4:47:05
	 */
	private void post() {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri;
		HttpURLConnection conn = null;
		DataOutputStream outStream = null;
		try {
			uri = new URL(actionUrl);
			conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);

			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINEND);
					sb.append("Content-Disposition: form-data; name=\""
							+ entry.getKey() + "\"" + LINEND);
					sb.append("Content-Type: text/plain; charset=" + CHARSET
							+ LINEND);
					sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
					sb.append(LINEND);
					sb.append(entry.getValue());
					sb.append(LINEND);
				}
			}

			outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(sb.toString().getBytes());

			// 发送文件数据
			if (files != null && files.size() > 0) {
				for (Map.Entry<String, File> file : files.entrySet()) {
					File tmpFile = file.getValue();
					String param = file.getKey();
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\""
							+ param + "\"" + "; filename=\""
							+ tmpFile.getName() + "\"" + LINEND);
					sb1.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINEND);
					sb1.append(LINEND);
					try {
						outStream.write(sb1.toString().getBytes());
						InputStream is = new FileInputStream(file.getValue());
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = is.read(buffer)) != -1) {
							outStream.write(buffer, 0, len);
						}

						is.close();
						outStream.write(LINEND.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();

			// 得到响应码
			int res = conn.getResponseCode();

			InputStream in = conn.getInputStream();
			InputStreamReader isReader = new InputStreamReader(in);
			BufferedReader bufReader = new BufferedReader(isReader);
			String line = null;
			data = "";

			while ((line = bufReader.readLine()) != null) {
				data += line;
			}

			if (res == 200) {
				if (ok != null) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ok.onResultOk(key, RESULT_OK, data);
						}
					});
				}
			} else {
				if (err != null) {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							err.onResultErr(key, RESULT_ERR, data);
						}
					});
				}
			}
			outStream.close();
			conn.disconnect();
		} catch (IOException e) {
			if (err != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						err.onResultErr(key, CONNECTION_ERR, "IO异常");
					}
				});
			}
			e.printStackTrace();
		}
	}

	/**
	 * 网络请求成功回调
	 * 
	 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
	 * 
	 * @author ZhuFan
	 * @Date: 2015-1-14
	 */
	public interface OnResultOk {
		/**
		 * 成功时的回调
		 * 
		 * @author ZhuFan
		 * @Package com.bm.chengkai.utils
		 * @param key
		 *            用于区分网络请求的key
		 * @param resultCode
		 *            返回的结果类型, 200为成功, 201为没有需要上传的图片
		 * @param data
		 *            服务器返回的数据
		 * @return void
		 * @throws
		 * @Date 2015-1-16 上午10:01:14
		 */
		public void onResultOk(int key, int resultCode, String data);
	}

	/**
	 * 网络请求失败回调
	 * 
	 * @Copyright Copyright © 2014 蓝色互动. All rights reserved.
	 * 
	 * @author ZhuFan
	 * @Date: 2015-1-14
	 */
	public interface OnResultErr {
		/**
		 * 失败时的回调
		 * 
		 * @author ZhuFan
		 * @Package com.bm.chengkai.utils
		 * @param key
		 *            用于区分网络请求的key
		 * @param resultCode
		 *            返回的结果类型, 0为请求成功但服务器返回的错误, 1为网络连接失败
		 * @param data
		 *            服务器返回的数据
		 * @return void
		 * @throws
		 * @Date 2015-1-16 上午10:01:19
		 */
		public void onResultErr(int key, int resultCode, String data);
	}

}
