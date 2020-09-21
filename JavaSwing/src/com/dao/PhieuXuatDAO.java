/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.helper.Connect;
import com.model.PhieuNhapNB;
import com.model.PhieuXuat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class PhieuXuatDAO {

    public void insert(PhieuXuat model) {
         String sql1 = "Insert into PhieuXuat(MaPhieu, manv, ngayxuat) values (?,?,?)";
        Connect.executeUpdate(sql1,
                model.getMaPhieu(),
                model.getMaNV(),
                model.getNgayXuat());
        String sql = "Insert into PhieuXuatCT(maphieu,tensp, soluong,donvi, gia, tongtien,tench, manv, ngayxuat, trangthai) values (?,?,?,?,?,?,?,?,?,?)";
        Connect.executeUpdate(sql,
                model.getMaPhieu(),
                model.getTenSP(),
                model.getSoLuong(),
                model.getDonVi(),
                model.getGia(),
                model.getTongTien(),
                model.getTenCH(),
                model.getMaNV(),
                model.getNgayXuat(),
                model.getTrangThai());
        String sql2 = "update sanpham set hangton = hangton - ? where tensp = ?";
        Connect.executeUpdate(sql2,
                model.getSoLuong(),
                model.getTenSP());
    }

    public List<PhieuXuat> select() {
        String sql = "SELECT * FROM PhieuXuatCT where trangthai=1";
        return select(sql);
    }

    private List<PhieuXuat> select(String sql, Object... args) {
        List<PhieuXuat> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuXuat model = readFromResultSet(rs);
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

    private PhieuXuat readFromResultSet(ResultSet rs) throws SQLException {
        PhieuXuat model = new PhieuXuat();
        model.setMaPhieu(rs.getString("MaPhieu"));
        model.setTenSP(rs.getString("TenSP"));
        model.setSoLuong(rs.getString("Soluong"));
        model.setDonVi(rs.getString("DonVi"));
        model.setGia(rs.getString("Gia"));
        model.setTongTien(rs.getString("TongTien"));
        model.setTenCH(rs.getString("TenCH"));
        model.setMaNV(rs.getString("Manv"));
        model.setNgayXuat(rs.getString("NgayXuat"));
        model.setTrangThai(rs.getInt("TrangThai"));
        return model;
    }

    private List<PhieuXuat> selectX(String sql, Object... args) {
        List<PhieuXuat> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    PhieuXuat model = readFromResultSetMP(rs);
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

    public List<PhieuXuat> selectMP() {
        String sql = "SELECT Top 1 maphieu + 1 as MaSau from PhieuXuat order by maphieu desc";
        return selectX(sql);
    }

    private PhieuXuat readFromResultSetMP(ResultSet rs) throws SQLException {
        PhieuXuat model = new PhieuXuat();
        model.setMaPhieu(rs.getString("MaSau"));
        return model;
    }
    public void update(PhieuXuat model) {
        String sql = "update PhieuXuatCT set  Trangthai= 'False' where MaPhieu = ?";
        Connect.executeUpdate(sql,
                model.getMaPhieu());
    }
}
