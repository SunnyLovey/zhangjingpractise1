package com.example.day3retrofit.utils;

public interface MyCallBack<T> {
    void successData(T data);
    void failData(String error);
}
