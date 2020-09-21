/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.SanPham;
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
public class SanPhamDAO {

    public void insert(SanPham model) {
        String sql = "insert into SanPham (tensp,gianhap,giaban,donvi,tennh,tennm,hangton,trangthai) values (?,?,?,?,?,?,?,?)";
        Connect.executeUpdate(sql,
                model.getTenSP(),
                model.getGiaNhap(),
                model.getGiaBan(),
                model.getDonVi(),
                model.getTenNH(),
                model.getTenNM(),
                model.getHangTon(),
                model.getTrangThai());
    }

    public void update(SanPham model) {
        String sql = "update SanPham set  gianhap=?,giaban=?,donvi=?,tennh=?,tennm=?,hangton=? where tensp = ?";
        Connect.executeUpdate(sql,
                model.getGiaNhap(),
                model.getGiaBan(),
                model.getDonVi(),
                model.getTenNH(),
                model.getTenNM(),
                model.getHangTon(),
                model.getTenSP());
    }

    public void delete(SanPham model) {
        String sql = "update SanPham set  Trangthai= 'False' where TenSp = ?";
        Connect.executeUpdate(sql,
                model.getTenSP());
    }

    public List<SanPham> Search(String tensp) {
        String sql = "SELECT * FROM SanPham WHERE tensp LIKE ?";
        return select(sql, "%" + tensp + "%");
    }

    public List<SanPham> select2() {
        String sql = "select * from SanPham where trangthai=1 and hangton > 0";
        return select(sql);
    }
    public List<SanPham> select() {
        String sql = "select * from SanPham where trangthai=1";
        return select(sql);
    }

    private List<SanPham> select(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    SanPham model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private SanPham readFromResultSet(ResultSet rs) throws SQLException {
        SanPham model = new SanPham();
        model.setTenSP(rs.getString("tensp"));
        model.setGiaNhap(rs.getInt("gianhap"));
        model.setGiaBan(rs.getInt("giaban"));
        model.setDonVi(rs.getString("donvi"));
        model.setTenNH(rs.getString("tennh"));
        model.setTenNM(rs.getString("tennm"));
        model.setHangTon(rs.getInt("hangton"));
        model.setTrangThai(rs.getInt("trangthai"));
        return model;
    }

    public List<SanPham> loadcombo(String tensp) {
        String sql = "SELECT * FROM SanPham WHERE tensp = ?";
        return select(sql, tensp);
    }



}
