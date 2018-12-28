package com.example.day3retrofit.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {
    private static  RetrofitUtils retrofitUtils;
    private OkHttpClient client;
    private BaseApis baseApis;
    //创建单例
    public static RetrofitUtils getInstance(){
        if(retrofitUtils==null){
            synchronized (RetrofitUtils.class){
                retrofitUtils=new RetrofitUtils();
            }
        }
        return retrofitUtils;
    }
    //构造方法
    public RetrofitUtils(){
        //日志拦截
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client=new OkHttpClient.Builder()
                .addInterceptor(interceptor1)//添加拦截器
                .writeTimeout(5000, TimeUnit.SECONDS)//写超时
                .readTimeout(5000,TimeUnit.SECONDS)//读超时
                .connectTimeout(5000,TimeUnit.SECONDS)//连接超时
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("http://www.zhaoapi.cn/product/")
                .client(client)
                .build();
          baseApis=retrofit.create(BaseApis.class);
    }

    public Map<String, RequestBody> requestBody(Map<String,String> params){
        Map<String, RequestBody> map=new HashMap<>();
        for (String key:params.keySet()){
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),
                    params.get(key)==null ? "" : params.get(key));
            map.put(key,requestBody);

        }
        return map;
    }
    //get请求
    public RetrofitUtils get(String path){
        baseApis.get(path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitUtils;

    }
    //表单post请求
    public RetrofitUtils postFormBody(String path,Map<String,RequestBody> map){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.postFormBody(path,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitUtils;
    }
    //普通post请求
    public RetrofitUtils post(String path,Map<String,String> map){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.post(path,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return retrofitUtils;
    }

    private Observer observer=new Observer<ResponseBody>() {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if(mCallBack!=null){
                mCallBack.onFail(e.getMessage());
            }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String result = responseBody.string();
                if(mCallBack!=null){
                    mCallBack.onSuccess(result);
                }

            } catch (IOException e) {
                 e.printStackTrace();
                 if(mCallBack!=null){
                     mCallBack.onFail(e.getMessage());
                 }
            }
        }
    };
    public MCallBack mCallBack;
    public void setCallBack(MCallBack callBack){
        this.mCallBack=callBack;
    }



    public interface MCallBack{
        void onSuccess(String data);
        void onFail(String e);
    }


}
