package com.dobest.ray.raydo.activity.camera;

import java.io.File;
import java.io.IOException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.dobest.ray.raydo.constants.Paths;

/**
 * 存在 sd卡 /a/image/camera/.. .jpg
 * 
 * 
 * 
 */
public class UseCameraActivity extends Activity {
	private String mImageFilePath;
	public static final String IMAGEFILEPATH = "ImageFilePath";
	public final static String IMAGE_PATH = "image_path";
	static Activity mContext;
	public final static int GET_IMAGE_REQ = 5000;
	private static Context applicationContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {

			mImageFilePath = savedInstanceState.getString(IMAGEFILEPATH);

			Log.i("wanglei", mImageFilePath);

			File mFile = new File(IMAGEFILEPATH);
			if (mFile.exists()) {
				Intent rsl = new Intent();
				rsl.putExtra(IMAGE_PATH, mImageFilePath);
				setResult(Activity.RESULT_OK, rsl);
				finish();
			} else {
				Log.i("wanglei", "文件不存在");
			}
		}

		mContext = this;
		applicationContext = getApplicationContext();
		if (savedInstanceState == null) {
			initialUI();
		}

	}

	public void initialUI() {
		long ts = System.currentTimeMillis();
		mImageFilePath = getCameraPath() + ts + ".jpg";
		Log.i("wanglei", "mImageFilePath:" + mImageFilePath);
		int degree = readPictureDegree(mImageFilePath);
		Log.i("wanglei", "degree:" + degree);
		File out = new File(mImageFilePath);

		showCamera(out);

	}

	private Uri imageUri = null;

	private void showCamera(File out) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = Uri.fromFile(out);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // set
		startActivityForResult(intent, GET_IMAGE_REQ);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case GET_IMAGE_REQ:
				Intent it = new Intent();
				Log.i("wanglei", "imageUri:"+(imageUri == null) + "");
				it.setData(imageUri);
				it.putExtra("imageUri", imageUri.toString());
				mContext.setResult(2001, it);
				mContext.finish();
				break;
			case 2:
				Log.i("wanglei", "imageUri.toString():" + imageUri.toString());
				Intent rsl = new Intent();
				rsl.putExtra("imageUri", imageUri.toString());
				mContext.setResult(2001, rsl);
				mContext.finish();
				break;
			default:
				break;
			}

		}else{
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("ImageFilePath", mImageFilePath + "");

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

	}

	public static String getCameraPath() {
		String filePath = getImageRootPath() + "/camera/";
		File file = new File(filePath);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		file = null;
		return filePath;
	}

	public static String getImageRootPath() {
		String filePath = getAppRootPath() + "/image";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		return filePath;
	}

	public static String getAppRootPath() {
		String filePath = Paths.ApkImage;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			filePath = Environment.getExternalStorageDirectory() + filePath;
		} else {
			filePath = applicationContext.getCacheDir() + filePath;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = null;
		File nomedia = new File(filePath + "/.nomedia");
		if (!nomedia.exists())
			try {
				nomedia.createNewFile();
			} catch (IOException e) {
			}
		return filePath;
	}

	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

}
