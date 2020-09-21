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
public class SanPham {
    private String tenSP;
    private int soLuong;
    private String donVi;
    private int giaNhap;
    private int giaBan;
    private String tenNH;
    private String tenNM;
    private int hangTon = 0;
    private int trangThai=1;

    public SanPham(String tenSP) {
        this.tenSP = tenSP;
    }

    public SanPham() {
    }

    public SanPham(String tenSP, int soLuong, String donVi, int giaNhap, int giaBan, String tenNH, String tenNM, int hangTon, int trangThai) {
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donVi = donVi;
        this.giaNhap = giaNhap;
        this.giaBan = giaBan;
        this.tenNH = tenNH;
        this.tenNM = tenNM;
        this.hangTon = hangTon;
        this.trangThai = trangThai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }



    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getTenNH() {
        return tenNH;
    }

    public void setTenNH(String tenNH) {
        this.tenNH = tenNH;
    }

    public String getTenNM() {
        return tenNM;
    }

    public void setTenNM(String tenNM) {
        this.tenNM = tenNM;
    }

    public int getHangTon() {
        return hangTon;
    }

    public void setHangTon(int hangTon) {
        this.hangTon = hangTon;
    }

}
