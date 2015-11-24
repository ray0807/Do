package com.dobest.ray.raydo.activity.details;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.dobest.ray.corelibs.ptr.PtrCanSetFreshNestedScrollview;
import com.dobest.ray.corelibs.ptr.PtrLollipopBaseView;
import com.dobest.ray.corelibs.ptr.PtrNestedScrollview;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.BaseActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.in.recycler.pro.PtrFrameLayout;

/**
 * Created by wangl01 on 2015/11/24.
 */
public class DetailsInfoActivity extends BaseActivity implements PtrLollipopBaseView.RALHandler, AppBarLayout.OnOffsetChangedListener {
    private Toolbar toolbar;
    private SimpleDraweeView back_drop;
    private SimpleDraweeView fb_imageview1;
    private SimpleDraweeView fb_imageview2;
    private SimpleDraweeView fb_imageview3;
    private static final String ImageUrl1 = "http://img5.imgtn.bdimg.com/it/u=3368709273,2917712832&fm=21&gp=0.jpg";
    private static final String ImageUrl2 = "http://img5.imgtn.bdimg.com/it/u=1929151791,3740603256&fm=21&gp=0.jpg";
    private static final String ImageUrl3 = "http://ww3.sinaimg.cn/bmiddle/b0cb01d6jw1eoo44fag8ig208w050x6p.gif";
    private static final String ImageUrl = "http://www.woaipet.cn/data/attachment/forum/201410/14/210843up0lgb2g2l3psbis.jpg.thumb.jpg";
    private PtrCanSetFreshNestedScrollview pn_scroll;
    private AppBarLayout app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_details_info);
        findViews();
        init();
        addListeners();
    }

    @Override
    public void findViews() {
        toolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Big Dog");
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);
        back_drop = (SimpleDraweeView) findViewById(R.id.back_drop);
        fb_imageview1 = (SimpleDraweeView) findViewById(R.id.fb_imageview1);
        fb_imageview2 = (SimpleDraweeView) findViewById(R.id.fb_imageview2);
        fb_imageview3 = (SimpleDraweeView) findViewById(R.id.fb_imageview3);
        pn_scroll = (PtrCanSetFreshNestedScrollview) findViewById(R.id.pn_scroll);
        app_bar= (AppBarLayout) findViewById(R.id.app_bar);
    }

    @Override
    public void init() {
        back_drop.setImageURI(Uri.parse(ImageUrl));
        fb_imageview1.setImageURI(Uri.parse(ImageUrl1));
        fb_imageview2.setImageURI(Uri.parse(ImageUrl2));

        fb_imageview1.setAspectRatio(1f);
        fb_imageview2.setAspectRatio(1f);

        //显示一张HTTP的GIF图片
        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(ImageUrl3))
                .setAutoPlayAnimations(true).build();
        fb_imageview3.setController(draweeController1);
        fb_imageview3.setAspectRatio(1f);
    }

    @Override
    public void addListeners() {

        app_bar.addOnOffsetChangedListener(this);
        pn_scroll.setRALHandler(this);
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.i("wanglei","i:"+i);
        if (i == 0) {
            pn_scroll.setCanRefresh(true);
        } else {
            pn_scroll.setCanRefresh(false);
        }
    }
    @Override
    protected void onResume() {
        app_bar.addOnOffsetChangedListener(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        app_bar.removeOnOffsetChangedListener(this);
        super.onStop();
    }
}
