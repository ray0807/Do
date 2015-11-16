package com.dobest.ray.raydo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.dobest.ray.corelibs.AppManager;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.corelibs.views.LoadingDialog;
import com.dobest.ray.raydo.R;

public abstract class BaseActivity extends AppCompatActivity {

	public Handler mHandler;

	public abstract void findViews();

	public abstract void init();

	public abstract void addListeners();

	protected LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		loadingDialog = new LoadingDialog(this);
		loadingDialog.setTitle("加载数据中");
		mHandler = new Handler();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	public void showToast(String s) {
		ToastMgr.show(s, 2);
	}

	public void showToast(int r) {
		ToastMgr.show(r, 2);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.in_left_right, R.anim.out_left_right);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
	}

	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_right_left, R.anim.out_right_left);
	}

	/**
	 * JPush需要在每个activity中做如此操作
	 */
	@Override
	protected void onResume() {
		// JPushInterface.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// JPushInterface.onPause(this);
		super.onPause();
	}
}
