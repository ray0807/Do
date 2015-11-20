package com.dobest.ray.raydo.activity;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dobest.ray.corelibs.ptr.PtrLollipopBaseView;
import com.dobest.ray.corelibs.ptr.PtrNestedScrollview;
import com.dobest.ray.corelibs.utils.FragmentController;
import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.activity.main.MainFragment;
import com.dobest.ray.raydo.activity.moments.MomentsFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.in.recycler.pro.PtrFrameLayout;

import carbon.widget.FrameLayout;


public class MainActivity extends BaseActivity implements View.OnClickListener ,PtrLollipopBaseView.RALHandler,AppBarLayout.OnOffsetChangedListener{
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
    /**
     * fragment control center
     */
    private FragmentController mFragmentController;
    private AppBarLayout app_bar;

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
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Big Dog");
        app_bar= (AppBarLayout) findViewById(R.id.app_bar);

        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        drawer_header = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.drawer_header, null);
        navigation_view.addHeaderView(drawer_header);

        mSimpleDraweeView = (SimpleDraweeView) drawer_header.findViewById(R.id.fb_imageview);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        fl_main = (FrameLayout) findViewById(R.id.fl_main);
        ll_moments = (carbon.widget.LinearLayout) drawer_header.findViewById(R.id.ll_moments);
        pn_scroll= (PtrNestedScrollview) findViewById(R.id.pn_scroll);


    }

    public void init() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawer_layout, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawer_layout.setDrawerListener(mActionBarDrawerToggle);
        //设置侧滑栏头像
        mSimpleDraweeView.setImageURI(Uri.parse(ImageUrl));

        mFragmentController = new FragmentController(
                getSupportFragmentManager(), R.id.fl_main,
                fragmentTags);
        mFragmentController.add(MainFragment.class,
                fragmentTags[0], null);

    }

    public void addListeners() {
        ll_moments.setOnClickListener(this);
        pn_scroll.setRALHandler(this);
        app_bar.addOnOffsetChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        drawer_layout.closeDrawers();
        switch (v.getId()) {
            case R.id.ll_moments:
                mFragmentController.add(MomentsFragment.class,
                        fragmentTags[1], null);
                break;
        }
    }

    private Handler mHandler=new Handler(){

    };
    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("wanglei","ad");
                pn_scroll.refreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.i("wanglei", "i：" + i);
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
