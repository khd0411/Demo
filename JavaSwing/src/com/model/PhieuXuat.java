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
public class PhieuXuat {

    private String maPhieu;
    private String tenSP;
    private String soLuong;
    private String donVi;
    private String gia;
    private String tongTien;
    private String tenCH;
    private String maNV;
    private String ngayXuat;
    private int trangThai=1;

    public PhieuXuat() {
    }

    public PhieuXuat(String maPhieu, String tenSP, String soLuong, String donVi, String gia, String tongTien, String tenCH, String maNV, String ngayXuat, int trangThai) {
        this.maPhieu = maPhieu;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donVi = donVi;
        this.gia = gia;
        this.tongTien = tongTien;
        this.tenCH = tenCH;
        this.maNV = maNV;
        this.ngayXuat = ngayXuat;
        this.trangThai = trangThai;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
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

    public String getTenCH() {
        return tenCH;
    }

    public void setTenCH(String tenCH) {
        this.tenCH = tenCH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

}
