/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;


import com.helper.Connect;
import com.model.CuaHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class CuaHangDAO {

    public void insert(CuaHang model) {
        String sql = "INSERT INTO CuaHang (TenCH, DiaChi, SDT,Trangthai) VALUES ( ?, ?,?,?)";
        Connect.executeUpdate(sql, model.getTenCH(), model.getDiaChi(), model.getSoDT(),model.getTrangThai());
    }

    public void update(CuaHang model) {
        String sql = "UPDATE CuaHang SET  DiaChi=?, SDT=? WHERE TenCH=?";
        Connect.executeUpdate(sql,  model.getDiaChi(), model.getSoDT(),model.getTenCH());

    }

    public void delete(CuaHang model) {
        String sql = "update CuaHang set  Trangthai= 'False' where TenCH = ?";
        Connect.executeUpdate(sql,
                model.getTenCH());
    }
    public List<CuaHang> select() {
        String sql = "SELECT * FROM CuaHang";
        return select(sql);
    }

    public CuaHang findById(String MaCH) {
        String sql = "SELECT * FROM CuaHang WHERE TenCH=?";
        List<CuaHang> list = select(sql, MaCH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<CuaHang> select(String sql, Object... args) {
        List<CuaHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    CuaHang model = readFromResultSet(rs);
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

    private CuaHang readFromResultSet(ResultSet rs) throws SQLException {
        CuaHang model = new CuaHang();

        model.setTenCH(rs.getString("TenCH"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setSoDT(rs.getString("SDT"));

        return model;
    }

    public List<CuaHang> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM CuaHang WHERE TenCH LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

}
