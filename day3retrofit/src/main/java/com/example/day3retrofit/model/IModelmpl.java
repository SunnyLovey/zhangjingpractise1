package com.example.day3retrofit.model;

import com.example.day3retrofit.utils.MyCallBack;
import com.example.day3retrofit.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelmpl implements IModel {
    @Override
    public void getData(String path, Map<String, String> map, final Class clazz, final MyCallBack myCallBack) {

       RetrofitUtils.getInstance().post(path,map).setCallBack(new RetrofitUtils.MCallBack() {
           @Override
           public void onSuccess(String data) {
               try {
                   Gson gson=new Gson();
                   Object o = gson.fromJson(data, clazz);
                   myCallBack.successData(o);
               }catch (Exception e){
                   e.printStackTrace();
                   myCallBack.failData(e.getMessage());
               }
           }

           @Override
           public void onFail(String e) {
               myCallBack.failData(e);
           }
       });
    }
}
