/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;


import com.helper.Connect;
import com.model.NhaMay;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NhaMayDAO {

    public void insert(NhaMay model) {
        String sql = "INSERT INTO NhaMay (TenNM, DiaChi, SDT,trangthai) VALUES (?,?,?,?)";
        Connect.executeUpdate(sql, model.getTenNM(), model.getDiaChi(), model.getSoDT(),model.getTrangThai());
    }

    public void update(NhaMay model) {
        String sql = "UPDATE NhaMay SET DiaChi=?, SDT=? WHERE TenNM=?";
        Connect.executeUpdate(sql, model.getDiaChi(), model.getSoDT(), model.getTenNM());

    }


    public void delete(NhaMay model) {
        String sql = "update NhaMay set  Trangthai= 'False' where TenNM = ?";
        Connect.executeUpdate(sql,
                model.getTenNM());
    }
    public List<NhaMay> select() {
        String sql = "SELECT * FROM NhaMay where trangthai=1";
        return select(sql);
    }

    public NhaMay findById(String TenNM) {
        String sql = "SELECT * FROM NhaMay WHERE TenNM=?";
        List<NhaMay> list = select(sql, TenNM);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhaMay> select(String sql, Object... args) {
        List<NhaMay> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = Connect.executeQuerry(sql, args);
                while (rs.next()) {
                    NhaMay model = readFromResultSet(rs);
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

    private NhaMay readFromResultSet(ResultSet rs) throws SQLException {
        NhaMay model = new NhaMay();
        model.setTenNM(rs.getString("TenNM"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setSoDT(rs.getString("SDT"));

        return model;
    }
       public List<NhaMay> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NhaMay WHERE TenNM LIKE ?";
        return select(sql, "%" + keyword + "%");
    }

}
