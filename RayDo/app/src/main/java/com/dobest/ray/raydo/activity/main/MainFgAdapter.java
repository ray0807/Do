package com.dobest.ray.raydo.activity.main;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.dobest.ray.raydo.R;
import com.dobest.ray.raydo.bean.childBean.MainInfoBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.TextView;

/**
 * Created by wangl01 on 2015/11/25.
 */
public class MainFgAdapter extends RecyclerView.Adapter<MainFgAdapterHolder> {
    private Context context;
    private onLayoutSetHeight listener;
    private List<MainInfoBean> bean = new ArrayList<MainInfoBean>();

    public MainFgAdapter(Context context, onLayoutSetHeight listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MainFgAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_fg, parent, false);
        //为item检测高度
        LinearLayout ll_height = (LinearLayout) view.findViewById(R.id.ll_height);
        ll_height.getViewTreeObserver().addOnGlobalLayoutListener(
                new MyOnGlobalLayoutListener(ll_height));
        //为item检测高度
        MainFgAdapterHolder holder = new MainFgAdapterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainFgAdapterHolder holder, int position) {
        holder.fb_imageview.setAspectRatio(1f);
        if (bean.get(position).imageUrl.endsWith(".gif")) {
            //显示一张HTTP的GIF图片
            DraweeController draweeController1 = Fresco.newDraweeControllerBuilder().setUri(Uri.parse(bean.get(position).imageUrl))
                    .setAutoPlayAnimations(true).build();
            holder.fb_imageview.setController(draweeController1);
        } else {
            holder.fb_imageview.setImageURI(Uri.parse(bean.get(position).imageUrl));
        }
        holder.tv_item.setText(bean.get(position).text);

    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    public void setData(List<MainInfoBean> bean) {
        this.bean.addAll(bean);
        notifyDataSetChanged();

    }

    /**
     * 动态获取高度
     */
    public interface onLayoutSetHeight {
        public void setHeight(int height);
    }

    public int itemHeight = 0;

    /**
     * 获取高度监听
     */
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        LinearLayout view;

        public MyOnGlobalLayoutListener(LinearLayout view) {
            this.view = view;
        }

        @Override
        public void onGlobalLayout() {
            itemHeight = view.getHeight();
            if (listener != null) {
                listener.setHeight(itemHeight * (getItemCount() + 1));
            }

            view.getViewTreeObserver().removeGlobalOnLayoutListener(this);

        }
    }
}


class MainFgAdapterHolder extends RecyclerView.ViewHolder {
    SimpleDraweeView fb_imageview;
    TextView tv_item;

    public MainFgAdapterHolder(View view) {
        super(view);
        fb_imageview = (SimpleDraweeView) view.findViewById(R.id.fb_imageview);
        tv_item = (TextView) view.findViewById(R.id.tv_item);
    }

}

