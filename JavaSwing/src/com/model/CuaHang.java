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
public class CuaHang {

    private String TenCH;
    private String DiaChi;
    private String SoDT;
    private int trangThai;

    public CuaHang() {
    }

    public CuaHang(String TenCH, String DiaChi, String SoDT, int trangThai) {
        this.TenCH = TenCH;
        this.DiaChi = DiaChi;
        this.SoDT = SoDT;
        this.trangThai = trangThai;
    }

    public CuaHang(String TenCH) {
        this.TenCH = TenCH;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenCH() {
        return TenCH;
    }

    public void setTenCH(String TenCH) {
        this.TenCH = TenCH;
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
