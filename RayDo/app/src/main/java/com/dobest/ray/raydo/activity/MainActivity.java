package com.dobest.ray.raydo.activity;

import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.utils.DisPlay;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


public class MainActivity extends BaseActivity {
    private Toolbar toolbar;
    private SimpleDraweeView mSimpleDraweeView;
    private SimpleDraweeView fb_imageview1;
    private SimpleDraweeView fb_imageview2;
    private SimpleDraweeView fb_imageview3;
    private LinearLayout drawer_header;
    private NavigationView navigation_view;

    private static final  String ImageUrl="http://www.woaipet.cn/data/attachment/forum/201410/14/210843up0lgb2g2l3psbis.jpg.thumb.jpg";
    private static final  String ImageUrl1="http://img5.imgtn.bdimg.com/it/u=3368709273,2917712832&fm=21&gp=0.jpg";
    private static final  String ImageUrl2="http://img5.imgtn.bdimg.com/it/u=1929151791,3740603256&fm=21&gp=0.jpg";
    private static final  String ImageUrl3="http://ww3.sinaimg.cn/bmiddle/b0cb01d6jw1eoo44fag8ig208w050x6p.gif";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        init();
        addListeners();
    }


    public void findViews() {
        toolbar=(Toolbar) this.findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_input_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Big Dog");


        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        drawer_header= (LinearLayout) LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        navigation_view.addHeaderView(drawer_header);
        mSimpleDraweeView= (SimpleDraweeView) drawer_header.findViewById(R.id.fb_imageview);
        fb_imageview1= (SimpleDraweeView) findViewById(R.id.fb_imageview1);
        fb_imageview2= (SimpleDraweeView) findViewById(R.id.fb_imageview2);
        fb_imageview3= (SimpleDraweeView) findViewById(R.id.fb_imageview3);


    }

    public void init() {



        mSimpleDraweeView.setImageURI(Uri.parse(ImageUrl));
        fb_imageview1.setImageURI(Uri.parse(ImageUrl1));
        fb_imageview2.setImageURI(Uri.parse(ImageUrl2));

        fb_imageview1.setAspectRatio(1f);
        fb_imageview2.setAspectRatio(1f);


        //显示一张HTTP的GIF图片
        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(ImageUrl3))
                .setAutoPlayAnimations(true).build();
        fb_imageview3.setController(draweeController1);
        Log.i("wanglei", "width:" + fb_imageview3.getDrawable().getIntrinsicWidth());
        Log.i("wanglei", "height:" + fb_imageview3.getDrawable().getIntrinsicHeight());

        fb_imageview3.setAspectRatio(1f);
    }

    public void addListeners() {

    }
}
