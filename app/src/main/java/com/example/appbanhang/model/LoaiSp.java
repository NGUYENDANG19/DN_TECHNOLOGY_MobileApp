package com.example.appbanhang.model;

public class LoaiSp {
    int id;
    String tensampham;
    String hinhanh;


    public LoaiSp(String tensampham, String hinhanh) {
        this.tensampham = tensampham;
        this.hinhanh = hinhanh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensampham() {
        return tensampham;
    }

    public void setTensampham(String tensampham) {
        this.tensampham = tensampham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
