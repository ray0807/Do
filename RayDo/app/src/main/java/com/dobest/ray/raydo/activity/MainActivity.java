package com.dobest.ray.raydo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dobest.ray.corelibs.AppManager;
import com.dobest.ray.corelibs.cache.ACache;
import com.dobest.ray.corelibs.ptr.PtrLollipopBaseView;
import com.dobest.ray.corelibs.ptr.PtrNestedScrollview;
import com.dobest.ray.corelibs.utils.FragmentController;
import com.dobest.ray.corelibs.utils.ToastMgr;
import com.dobest.ray.raydo.App;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.camera.UseCameraActivity;
import com.dobest.ray.raydo.activity.chat.ChatFragment;
import com.dobest.ray.raydo.activity.details.DetailsInfoActivity;
import com.dobest.ray.raydo.activity.main.MainFragment;
import com.dobest.ray.raydo.activity.map.MapChatActivity;
import com.dobest.ray.raydo.activity.map.MapFragment;
import com.dobest.ray.raydo.activity.moments.MomentsFragment;
import com.dobest.ray.raydo.bean.BaseData;
import com.dobest.ray.raydo.utils.ImageUploader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.in.recycler.pro.PtrFrameLayout;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import carbon.beta.TransitionLayout;
import carbon.widget.FrameLayout;


public class MainActivity extends BaseActivity implements View.OnClickListener, PtrLollipopBaseView.RALHandler {
    private String[] fragmentTags = {"fragment_main",
            "fragment_moments", "fragment_resume_manager", "fragment_job_fair",
            "fragment_hot_information", "fragment_career_guidance",
            "fragment_setting"};
    private Toolbar toolbar;
    private SimpleDraweeView mSimpleDraweeView;
    private DrawerLayout drawer_layout;
    private LinearLayout drawer_header;
    private NavigationView navigation_view;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private PtrNestedScrollview pn_scroll;
    private static final String ImageUrl = "http://www.woaipet.cn/data/attachment/forum/201410/14/210843up0lgb2g2l3psbis.jpg.thumb.jpg";

    private FrameLayout fl_main;
    private carbon.widget.LinearLayout ll_moments;
    private carbon.widget.LinearLayout ll_chat;
    private carbon.widget.LinearLayout ll_public_topic;
    private carbon.widget.LinearLayout ll_map;
    private carbon.widget.CardView powerMenu;
    public static final int TAKE_PICTURE = 1;
    public static final int CROP_PICTURE = 10;
    public static final int CHOOSE_PICTURE = 2;
    /**
     * fragment control center
     */
    private FragmentController mFragmentController;
    private AppBarLayout app_bar;
    boolean vibration = false, volume = true, airplaneMode = false;

//    private SimpleDraweeView back_drop;

    //上传头像使用
    private ImageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
        addListeners();

    }


    public void findViews() {
        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("Big Dog");
        toolbar.setTitle("Big Dog");
        app_bar = (AppBarLayout) findViewById(R.id.app_bar);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        drawer_header = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        navigation_view.addHeaderView(drawer_header);

        //头像信息
        mSimpleDraweeView = (SimpleDraweeView) drawer_header.findViewById(R.id.fb_imageview);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        ll_moments = (carbon.widget.LinearLayout) drawer_header.findViewById(R.id.ll_moments);
        ll_chat = (carbon.widget.LinearLayout) drawer_header.findViewById(R.id.ll_chat);
        ll_public_topic = (carbon.widget.LinearLayout) drawer_header.findViewById(R.id.ll_public_topic);
        ll_map = (carbon.widget.LinearLayout) drawer_header.findViewById(R.id.ll_map);
        powerMenu = (carbon.widget.CardView) findViewById(R.id.powerMenu);
        pn_scroll = (PtrNestedScrollview) findViewById(R.id.pn_scroll);
//        back_drop = (SimpleDraweeView) findViewById(R.id.back_drop);
//        //设置首页图片  测试
//        back_drop.setImageURI(Uri.parse(ImageUrl));
    }

    public void init() {

        //设置实时定位
        App.getInstance().setWebSocketEnable(true);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer_layout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                isShow = true;
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isShow = false;
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        drawer_layout.setDrawerListener(mActionBarDrawerToggle);
        //设置侧滑栏头像
        mSimpleDraweeView.setImageURI(Uri.parse(ImageUrl));

        mFragmentController = new FragmentController(
                getSupportFragmentManager(), R.id.fl_main,
                fragmentTags);
        mFragmentController.add(MainFragment.class,
                fragmentTags[0], null);

        //初始化图像上传工具
        uploader = new ImageUploader(this);

    }

    public void addListeners() {
        ll_moments.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        ll_public_topic.setOnClickListener(this);
        ll_map.setOnClickListener(this);
        pn_scroll.setOnClickListener(this);
        pn_scroll.setRALHandler(this);
        //设置对话框
        setPopDialog();
        //上传头像回调
        uploader.setOnResultOkListener(new ImageUploader.OnResultOk() {
            @Override
            public void onResultOk(int key, int resultCode, String data) {
                if (resultCode == ImageUploader.RESULT_NO_DATA) {
                } else if (resultCode == ImageUploader.RESULT_OK) {
                    Gson gson = new Gson();
                    BaseData reData = gson.fromJson(data, BaseData.class);
                    String ImageUrl = "";
                    if (reData.errorCode == 0) {
                        ImageUrl = reData.result.ImageUrl;
                        ACache.get(MainActivity.this).put("ImageUrl", ImageUrl);
                        mSimpleDraweeView.setImageURI(Uri.parse(ImageUrl));
//                        back_drop.setImageURI(Uri.parse(ImageUrl));
                        Snackbar.make(drawer_layout, "上传成功", Snackbar.LENGTH_LONG).show();
                    }

                }
                uploader.clearCache();

            }
        });
        uploader.setOnResultErrListener(new ImageUploader.OnResultErr() {
            @Override
            public void onResultErr(int key, int resultCode, String data) {
                Snackbar.make(drawer_layout, "上传失败", Snackbar.LENGTH_LONG).show();
                uploader.clearCache();
            }
        });

    }

    @Override
    public void onClick(final View v) {
        drawer_layout.closeDrawers();
        switch (v.getId()) {
            case R.id.ll_moments:
                mFragmentController.add(MomentsFragment.class,
                        fragmentTags[2], null);
                toolbar.setTitle("朋友圈");

                break;
            case R.id.ll_public_topic:
                mFragmentController.add(MainFragment.class,
                        fragmentTags[0], null);
                toolbar.setTitle("Big Dog");
                break;
            case R.id.ll_map:
//                mFragmentController.add(MapFragment.class,
//                        fragmentTags[1], null);
//                toolbar.setTitle("地图");
                Intent it = new Intent(this, MapChatActivity.class);
                startActivity(it);
                break;
            case R.id.ll_chat:
                mFragmentController.add(ChatFragment.class,
                        fragmentTags[3], null);
                toolbar.setTitle("Chat Room");
                break;

        }
    }

    private void setPopDialog() {
        final TransitionLayout transitionLayout = (TransitionLayout) findViewById(R.id.transition);

        findViewById(R.id.fb_imageview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                drawer_layout.closeDrawers();
                if (powerMenu.getVisibility() == View.VISIBLE)
                    return;
                transitionLayout.setCurrentChild(0);
                final List<View> viewsWithTag = ((carbon.widget.LinearLayout) transitionLayout.getChildAt(0)).findViewsWithTag("animate");
                for (int i = 0; i < viewsWithTag.size(); i++)
                    viewsWithTag.get(i).setVisibility(View.INVISIBLE);
                powerMenu.setVisibility(View.VISIBLE);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < viewsWithTag.size(); i++) {
                            final int finalI = i;
                            v.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                                }
                            }, i * 40);
                        }
                    }
                }, 200);
            }
        });

        /**
         * 照相程序
         */
        findViewById(R.id.powerOff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final List<View> viewsWithTag = ((FrameLayout) transitionLayout.getChildAt(1)).findViewsWithTag("animate");
                for (int i = 0; i < viewsWithTag.size(); i++)
                    viewsWithTag.get(i).setVisibility(View.INVISIBLE);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < viewsWithTag.size(); i++) {
                            final int finalI = i;
                            v.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                                }
                            }, i * 20);
                        }
                    }
                }, 400);
                transitionLayout.setHotspot(v.findViewById(R.id.powerOffIcon));
                transitionLayout.startTransition(1, TransitionLayout.TransitionType.Radial);
                //照相前先清除数据
                App.getInstance().clearImageCache();
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, UseCameraActivity.class);
                        startActivityForResult(intent, TAKE_PICTURE);
                        powerMenu.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        });

        /**
         * 图库
         */
        findViewById(R.id.reboot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final List<View> viewsWithTag = ((FrameLayout) transitionLayout.getChildAt(2)).findViewsWithTag("animate");
                for (int i = 0; i < viewsWithTag.size(); i++)
                    viewsWithTag.get(i).setVisibility(View.INVISIBLE);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < viewsWithTag.size(); i++) {
                            final int finalI = i;
                            v.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                                }
                            }, i * 20);
                        }
                    }
                }, 400);
                transitionLayout.setHotspot(v.findViewById(R.id.rebootIcon));
                transitionLayout.startTransition(2, TransitionLayout.TransitionType.Radial);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentFromGallery = new Intent();
                        intentFromGallery.setType("image/*"); // 设置文件类型
                        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
                        MainActivity.this.startActivityForResult(intentFromGallery, CHOOSE_PICTURE);
                        powerMenu.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        });
        /**
         * 取消
         */
        findViewById(R.id.airplaneMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final List<View> viewsWithTag = ((FrameLayout) transitionLayout.getChildAt(3)).findViewsWithTag("animate");
                for (int i = 0; i < viewsWithTag.size(); i++)
                    viewsWithTag.get(i).setVisibility(View.INVISIBLE);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < viewsWithTag.size(); i++) {
                            final int finalI = i;
                            v.getHandler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    viewsWithTag.get(finalI).setVisibility(View.VISIBLE);
                                }
                            }, i * 20);
                        }
                    }
                }, 400);
                transitionLayout.setHotspot(v.findViewById(R.id.airplaneModeIcon));
                transitionLayout.startTransition(3, TransitionLayout.TransitionType.Radial);
                v.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent it = new Intent(MainActivity.this, DetailsInfoActivity.class);
                        startActivity(it);
                        powerMenu.setVisibility(View.INVISIBLE);
                    }
                }, 1000);
            }
        });


    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pn_scroll.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 2001:
                //照相程序返回
                if (data != null) {
                    startPhotoZoom(data.getData());
                } else {
                    Log.e("wanglei", "CHOOSE_SMALL_PICTURE: data = " + data);
                    return;
                }
                break;
        }
        switch (requestCode) {
            case CROP_PICTURE:
                //剪裁后返回
                Bitmap bitmap = null;
                if (data != null) {
                    bitmap = getImageToView(data);
                }
                bitmap = uploader.compressBitmap(bitmap);
                upLoadImage(bitmap);
                break;
            case CHOOSE_PICTURE:
                if (data != null) {
                    startPhotoZoom(data.getData());
                } else {
                    Log.e("wanglei", "CHOOSE_SMALL_PICTURE: data = " + data);
                    return;
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        if (null == uri) {
            ToastMgr.show("加载数据失败");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private Bitmap getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            return photo;
        } else {
            return null;
        }
    }


    private Map<String, File> files = new LinkedHashMap<String, File>();

    /**
     * 上传头像使用
     *
     * @param bm
     */
    private void upLoadImage(Bitmap bm) {


        Map<String, String> params = new HashMap<String, String>();
        params.put("userName", "15971470520");
        if (bm != null) {
            try {
                files.put("img", uploader.bitmapTofile(bm));
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploader.post(params, files);
        }

    }

    private boolean isShow = false;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow) {
                drawer_layout.closeDrawers();
                isShow = false;
                return false;
            } else if (!(mFragmentController.getCurrentFragment() instanceof MainFragment)) {
                mFragmentController
                        .add(MainFragment.class, fragmentTags[0], null);
            } else {
                doublePressBackToast();
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            // 在这里做你想做的事情
            drawer_layout.openDrawer(Gravity.LEFT);
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    private boolean isBackPressed;

    /**
     * 双击返回退出
     *
     * @return void
     * @throws
     * @author ZhuFan
     * @Package com.bm.talonmobile.activity.main
     * @Date 2015-6-1 下午3:28:36
     */

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            Snackbar.make(drawer_layout, "再次点击返回退出程序", Snackbar.LENGTH_LONG).show();
        } else {
            //设置实时定位
            App.getInstance().setWebSocketEnable(false);
            //销毁定位
            App.getInstance().destroyLocation();
            App.getInstance().closeWebSocket();

            AppManager.getAppManager().appExit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

}
