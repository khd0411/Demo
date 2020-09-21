/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author Admin
 */
public class NhomHang {
    private String maNH;
    private String tenNH;

    public NhomHang() {
    }

    public NhomHang(String maNH, String tenNH) {
        this.maNH = maNH;
        this.tenNH = tenNH;
    }

    public String getMaNH() {
        return maNH;
    }

    public void setMaNH(String maNH) {
        this.maNH = maNH;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }
    
}
