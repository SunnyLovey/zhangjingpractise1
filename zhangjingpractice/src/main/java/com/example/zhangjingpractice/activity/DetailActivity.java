package com.example.zhangjingpractice.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangjingpractice.MainActivity;
import com.example.zhangjingpractice.R;
import com.example.zhangjingpractice.adapter.ViewPagerAdapter;
import com.example.zhangjingpractice.bean.DetailBean;
import com.example.zhangjingpractice.bean.MessageBean;
import com.example.zhangjingpractice.utils.OkHttpUtils;
import com.example.zhangjingpractice.utils.Path;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.text_price)
    TextView textView_price;
    @BindView(R.id.text_title)
    TextView textView_title;
   @BindView(R.id.viewpager)
    ViewPager viewPager;
   @BindView(R.id.button_join)
    Button button_join;
    @BindView(R.id.simple_image)
    SimpleDraweeView simpleDraweeView;
    private String[] split;
    private ViewPagerAdapter adapter;
    private int i;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i++;
            viewPager.setCurrentItem(i);
            sendEmptyMessageDelayed(0,2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(DetailActivity.this).setShareConfig(config);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        //获取传过来的值
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 2);
        //请求数据
        Map<String,String> params=new HashMap<>();
        params.put("pid",pid+"");
        OkHttpUtils.getInstance().postRequest(Path.path_detail,params,DetailBean.class);


    }
    //第三方登录
    @OnClick({R.id.button_join})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.button_join:
               // Toast.makeText(DetailActivity.this,"111",Toast.LENGTH_LONG).show();
                UMShareAPI umShareAPI = UMShareAPI.get(this);
                umShareAPI.getPlatformInfo(this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        //获取头像
                        String profile_image_url = map.get("profile_image_url");
                        simpleDraweeView.setImageURI(profile_image_url);

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //请求成功地数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventInfo(MessageBean messageBean){
        DetailBean bean= (DetailBean) messageBean.getObject();
        DetailBean.DataBean data = bean.getData();
        String images = data.getImages();
        split = images.split("\\|");
        //创建适配器
        adapter=new ViewPagerAdapter(split,this);
        viewPager.setAdapter(adapter);
         i = viewPager.getCurrentItem();
         handler.sendEmptyMessageDelayed(i,2000);
        //设值
        textView_title.setText(data.getTitle());
        textView_price.setText("￥"+data.getPrice()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        handler.removeCallbacksAndMessages(null);
    }
}
