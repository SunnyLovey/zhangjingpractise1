package com.example.zhangjingpractice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

public class ViewPagerAdapter extends PagerAdapter {
    private String[] str;
    private Context context;

    public ViewPagerAdapter(String[] str, Context context) {
        this.str = str;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
        simpleDraweeView.setImageURI(str[position%str.length]);
        container.addView(simpleDraweeView);
        return simpleDraweeView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
