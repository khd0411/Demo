/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.helper.Connect;
import com.model.PhieuNhapNM;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PhieuNhapHangTuNhaMayDAO {
     public void insert(PhieuNhapNM model) {
        String sql1 = "Insert into PhieuNhapNM(MaPhieuNM, manv, ngaynhap) values (?,?,?)";
        Connect.executeUpdate(sql1,
                model.getMaPhieuNM(),
                model.getMaNV(),
                model.getNgayNhap());
        String sql = "Insert into PhieuNhapNMCT(MaPhieuNM, tensp, soluong,donvi, gia, tongtien, manv, ngaynhap, tennm, trangthai) values (?,?,?,?,?,?,?,?,?,?)";
        Connect.executeUpdate(sql,
                model.getMaPhieuNM(),
                model.getTenSP(),
                model.getSoLuong(),
                model.getDonVi(),
                model.getGia(),
                model.getTongTien(),
                model.getMaNV(),
                model.getNgayNhap(),
                model.getTenNM(),
                model.getTrangThai());
        String sql2 = "update sanpham set hangton = hangton + ? where tensp = ?";
        Connect.executeUpdate(sql2,
                model.getSoLuong(),
                model.getTenSP());
    }

    public List<PhieuNhapNM> select() {
        String sql = "SELECT * FROM PhieuNhapNMCT where trangthai=1";
        return select(sql);
    }

    private List<PhieuNhapNM> select(String sql, Object... args) {
        List<PhieuNhapNM> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuNhapNM model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private PhieuNhapNM readFromResultSet(ResultSet rs) throws SQLException {
        PhieuNhapNM model = new PhieuNhapNM();
        model.setMaPhieuNM(rs.getString("MaPhieuNM"));
        model.setTenSP(rs.getString("TenSP"));
        model.setSoLuong(rs.getString("Soluong"));
        model.setDonVi(rs.getString("DonVi"));
        model.setGia(rs.getString("Gia"));
        model.setTongTien(rs.getString("TongTien"));
        model.setMaNV(rs.getString("Manv"));
        model.setNgayNhap(rs.getString("NgayNhap"));
        model.setTenNM(rs.getString("TenNM"));
        model.setTrangThai(rs.getInt("Trangthai"));
        return model;
    }

    public void update(PhieuNhapNM model) {
        String sql = "update PhieuNhapNMCT set  Trangthai= 'False' where MaPhieuNM = ?";
        Connect.executeUpdate(sql,
                model.getMaPhieuNM());
    }

    private List<PhieuNhapNM> selectMP(String sql, Object... args) {
        List<PhieuNhapNM> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuNhapNM model = readFromResultSetMP(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<PhieuNhapNM> selectMP() {
        String sql = "SELECT Top 1 maphieunm + 1 as MaSau from PhieuNhapNM order by maphieunm desc";
        return selectMP(sql);
    }

    private PhieuNhapNM readFromResultSetMP(ResultSet rs) throws SQLException {
        PhieuNhapNM model = new PhieuNhapNM();
        model.setMaPhieuNM(rs.getString("MaSau"));
        return model;
    }
}
