package com.example.day3retrofit.view;

public interface IView<T> {
    void requestSuccess(T data);
    void requestFail(String e);
}
