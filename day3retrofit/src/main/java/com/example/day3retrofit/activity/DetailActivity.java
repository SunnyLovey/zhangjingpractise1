package com.example.day3retrofit.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.day3retrofit.R;
import com.example.day3retrofit.adapter.TabAdapter;
import com.example.day3retrofit.view.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
@BindView(R.id.tab_layout)
    TabLayout tabLayout;
@BindView(R.id.viewpager)
    ViewPager viewPager;
private TabAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);


    }
    public int getPositionData(){
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 2);
        return pid;
    }


}
