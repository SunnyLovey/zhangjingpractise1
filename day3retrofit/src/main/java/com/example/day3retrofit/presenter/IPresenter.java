package com.example.day3retrofit.presenter;

import java.util.Map;

public interface IPresenter {
    void startRequest(String path, Map<String,String> map,Class clazz);
}
