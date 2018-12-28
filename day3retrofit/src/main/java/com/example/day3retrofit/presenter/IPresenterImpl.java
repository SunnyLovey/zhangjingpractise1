package com.example.day3retrofit.presenter;

import com.example.day3retrofit.model.IModelmpl;
import com.example.day3retrofit.utils.MyCallBack;
import com.example.day3retrofit.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelmpl iModelmpl;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModelmpl=new IModelmpl();
    }

    @Override
    public void startRequest(String path, Map<String, String> map, Class clazz) {
        iModelmpl.getData(path, map, clazz, new MyCallBack() {
            @Override
            public void successData(Object data) {
                iView.requestSuccess(data);
            }

            @Override
            public void failData(String error) {
             iView.requestFail(error);
            }
        });
    }
    //取消注册
    public void detachView(){
        if(iModelmpl!=null){
            iModelmpl=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}
