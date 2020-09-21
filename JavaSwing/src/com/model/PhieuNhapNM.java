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
public class PhieuNhapNM {

    private String maPhieuNM;
    private String tenSP;
    private String donVi;
    private String soLuong;
    private String gia;
    private String tongTien;
    private String maNV;
    private String tenNM;
    private String ngayNhap;
    private int trangThai =1;

    public PhieuNhapNM() {
    }

    public PhieuNhapNM(String maPhieuNM, String tenSP, String donVi, String soLuong, String gia, String tongTien, String maNV, String tenNM, String ngayNhap, int trangThai) {
        this.maPhieuNM = maPhieuNM;
        this.tenSP = tenSP;
        this.donVi = donVi;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tongTien = tongTien;
        this.maNV = maNV;
        this.tenNM = tenNM;
        this.ngayNhap = ngayNhap;
        this.trangThai = trangThai;
    }

    public String getMaPhieuNM() {
        return maPhieuNM;
    }

    public void setMaPhieuNM(String maPhieuNM) {
        this.maPhieuNM = maPhieuNM;
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

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNM() {
        return tenNM;
    }

    public void setTenNM(String tenNM) {
        this.tenNM = tenNM;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
