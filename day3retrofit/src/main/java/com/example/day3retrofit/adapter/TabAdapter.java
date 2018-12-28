package com.example.day3retrofit.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.day3retrofit.fragment.FragementGoods;
import com.example.day3retrofit.fragment.FragmentDetail;
import com.example.day3retrofit.fragment.FragmentDiscuss;

public class TabAdapter extends FragmentPagerAdapter {
    private String[] str={"商品","详情","评论"};
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 1:
                return new FragementGoods();
            case 0:
                return new FragmentDetail();
            case 2:
                return new FragmentDiscuss();
                default:
                    return new Fragment();
        }

    }

    @Override
    public int getCount() {
        return str.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return str[position];
    }
}
