/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.model.NhomHang;
import com.helper.Connect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhomHangDAO {
        public void insert(NhomHang model) {
        String sql = "insert into NhomHang (tennh) values (?)";
        Connect.executeUpdate(sql,
                model.getTenNH());
    }

    public void update(NhomHang model) {
        String sql = "update NhomHang set tennh=? where stt = ?";
        Connect.executeUpdate(sql,
                model.getTenNH(),
                model.getMaNH());
    }

    public void delete(String MaNH) {
        String sql = "delete from NhomHang where stt = ?";
        Connect.executeUpdate(sql, MaNH);
    }


        public List<NhomHang> Search(String manh) {
        String sql = "SELECT * FROM NhomHang WHERE stt LIKE ?";
        return select(sql, "%" + manh + "%");
    }

    public List<NhomHang> select() {
        String sql = "select * from NhomHang";
        return select(sql);
    }

    private List<NhomHang> select(String sql, Object... args) {
        List<NhomHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    NhomHang model = readFromResultSet(rs);
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

    private NhomHang readFromResultSet(ResultSet rs) throws SQLException {
        NhomHang model = new NhomHang();
        model.setMaNH(rs.getString("stt"));
        model.setTenNH(rs.getString("tennh"));
        return model;
    }
}
