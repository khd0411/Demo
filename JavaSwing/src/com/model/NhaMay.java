/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Administrator
 */
public class NhaMay {
    private String TenNM;
    private String DiaChi;
    private String SoDT;
    private int trangThai=1;

    public NhaMay() {
    }

    public NhaMay(String TenNM) {
        this.TenNM = TenNM;
    }

    public NhaMay(String TenNM, String DiaChi, String SoDT, int trangThai) {
        this.TenNM = TenNM;
        this.DiaChi = DiaChi;
        this.SoDT = SoDT;
        this.trangThai = trangThai;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }



    public String getTenNM() {
        return TenNM;
    }

    public void setTenNM(String TenNM) {
        this.TenNM = TenNM;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String SoDT) {
        this.SoDT = SoDT;
    }
    
    
}
