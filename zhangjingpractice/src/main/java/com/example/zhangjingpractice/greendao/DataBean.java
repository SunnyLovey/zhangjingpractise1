package com.example.zhangjingpractice.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DataBean {
    @Id
    private Long id;
    private String images;
    private int pid;
    private double price;
    private String title;
    @Generated(hash = 2089673293)
    public DataBean(Long id, String images, int pid, double price, String title) {
        this.id = id;
        this.images = images;
        this.pid = pid;
        this.price = price;
        this.title = title;
    }
    @Generated(hash = 908697775)
    public DataBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public int getPid() {
        return this.pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }


}
