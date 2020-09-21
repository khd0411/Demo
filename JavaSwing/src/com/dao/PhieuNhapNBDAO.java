/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.helper.Connect;
import com.model.PhieuNhapNB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PhieuNhapNBDAO {

    public void insert(PhieuNhapNB model) {
        String sql1 = "Insert into PhieuNhapNB(MaPN, manv, ngaynhap) values (?,?,?)";
        Connect.executeUpdate(sql1,
                model.getMaPN(),
                model.getMaNV(),
                model.getNgayNhap());
        String sql = "Insert into PhieuNhapNBCT(MaPN, tensp, soluong,donvi, gia, tongtien, tenCH, manv, ngaynhap, trangthai) values (?,?,?,?,?,?,?,?,?,?)";
        Connect.executeUpdate(sql,
                model.getMaPN(),
                model.getTenSP(),
                model.getSoLuong(),
                model.getDonVi(),
                model.getGia(),
                model.getTongTien(),
                model.getTenCH(),
                model.getMaNV(),
                model.getNgayNhap(),
                model.getTrangThai());
        String sql2 = "update sanpham set hangton = hangton + ? where tensp = ?";
        Connect.executeUpdate(sql2,
                model.getSoLuong(),
                model.getTenSP());
    }

    public List<PhieuNhapNB> select() {
        String sql = "SELECT * FROM PhieuNhapNBCT where trangthai=1";
        return select(sql);
    }

    private List<PhieuNhapNB> select(String sql, Object... args) {
        List<PhieuNhapNB> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuNhapNB model = readFromResultSet(rs);
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

    private PhieuNhapNB readFromResultSet(ResultSet rs) throws SQLException {
        PhieuNhapNB model = new PhieuNhapNB();
        model.setMaPN(rs.getString("MaPN"));
        model.setTenSP(rs.getString("TenSP"));
        model.setSoLuong(rs.getString("Soluong"));
        model.setDonVi(rs.getString("DonVi"));
        model.setGia(rs.getString("Gia"));
        model.setTongTien(rs.getString("TongTien"));
        model.setTenCH(rs.getString("TenCH"));
        model.setMaNV(rs.getString("Manv"));
        model.setNgayNhap(rs.getString("NgayNhap"));
        model.setTrangThai(rs.getInt("TrangThai"));

        return model;
    }

    private List<PhieuNhapNB> selectMP(String sql, Object... args) {
        List<PhieuNhapNB> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuNhapNB model = readFromResultSetMP(rs);
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

    public List<PhieuNhapNB> selectMP() {
        String sql = "SELECT Top 1 mapn + 1 as MaSau from PhieuNhapNB order by mapn desc";
        return selectMP(sql);
    }

    private PhieuNhapNB readFromResultSetMP(ResultSet rs) throws SQLException {
        PhieuNhapNB model = new PhieuNhapNB();
        model.setMaPN(rs.getString("MaSau"));
        return model;
    }
     public void update(PhieuNhapNB model) {
        String sql = "update PhieuNhapNBCT set  Trangthai= 'False' where MaPN = ?";
        Connect.executeUpdate(sql,
                model.getMaPN());
    }
}
