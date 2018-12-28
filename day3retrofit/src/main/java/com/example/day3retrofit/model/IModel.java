package com.example.day3retrofit.model;

import com.example.day3retrofit.utils.MyCallBack;

import java.util.Map;

public interface IModel {
    void getData(String path, Map<String,String> map, Class clazz, MyCallBack myCallBack);
}
