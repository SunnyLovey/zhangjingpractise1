package com.example.zhangjingpractice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.example.zhangjingpractice.bean.MessageBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtils {
    private static volatile OkHttpUtils instance;
    private OkHttpClient client;
    private Handler handler=new Handler(Looper.getMainLooper());
    //创建单例
    public static OkHttpUtils getInstance(){
        if(instance==null){
            synchronized (OkHttpUtils.class){
                instance=new OkHttpUtils();
            }
        }
        return instance;
    }
    //构造方法
    public OkHttpUtils(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client=new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)//连接超时
                .readTimeout(5000,TimeUnit.SECONDS)//读超时
                .writeTimeout(5000,TimeUnit.SECONDS)//写超时
                .addInterceptor(interceptor1)//添加拦截器
                .build();
    }
    //异步post请求
    public void postRequest(String path, Map<String,String> params, final Class clazz){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body=builder.build();
        Request request = new Request.Builder()
                .post(body)
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               EventBus.getDefault().post(new MessageBean(Identifier.flag_num,e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                       try {
                           String result = response.body().string();
                           Gson gson=new Gson();
                            Object o = gson.fromJson(result, clazz);
                           EventBus.getDefault().post(new MessageBean(Identifier.flag_num,o));

                       }catch (Exception e){
                           EventBus.getDefault().post(new MessageBean(Identifier.flag_num,e.getMessage()));
                       }
            }
        });
    }
    //是否有可用网络
    public static boolean hasNetwork(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

}
