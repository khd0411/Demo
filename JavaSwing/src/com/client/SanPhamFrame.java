/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import com.dao.NhaMayDAO;
import com.dao.NhomHangDAO;
import com.dao.SanPhamDAO;
import com.model.SanPham;
import com.helper.Connect;
import com.model.NhaMay;
import com.model.NhomHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Administrator
 */
public class SanPhamFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form CuaHangJInternalFrame
     */
    SanPhamDAO dao = new SanPhamDAO();
    NhomHangDAO dao2 = new NhomHangDAO();
    NhaMayDAO dao3 = new NhaMayDAO();
    DefaultTableModel model;

    public SanPhamFrame() {
        initComponents();
        load();
        LoadMaNH();
        LoadMaNM();
    }

    private void LoadMaNH() {
        try {
            List<NhomHang> list = dao2.select();
            for (NhomHang d : list) {
                cbo_MaNH.addItem(d.getTenNH());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadMaNM() {
        try {
            List<NhaMay> list = dao3.select();
            for (NhaMay d : list) {
                cbo_MaNM.addItem(d.getTenNM());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    SanPham getModel() {
        SanPham model = new SanPham();
        model.setTenSP(String.valueOf(txtTenSP.getText()));
        model.setDonVi(String.valueOf(txtDonVi.getText()));
        model.setGiaNhap(Integer.valueOf(txtGiaNhap.getText()));
        model.setGiaBan(Integer.valueOf(txtGiaBan.getText()));
        model.setTenNH(String.valueOf(cbo_MaNH.getSelectedItem()));
        model.setTenNM(String.valueOf(cbo_MaNM.getSelectedItem()));
        return model;
    }

    boolean check() throws SQLException {

        String tenspmau = "\\D+";
        String slmau = "\\d+";

        List<SanPham> list = dao.select();
        for (SanPham nv : list) {
            Object[] row = {
                nv.getTenNM()};
            if (txtTenSP.getText().matches(nv.getTenSP())) {
                JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại");
                return false;
            }
        }
        if (txtTenSP.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống tên sản phẩm");
            return false;
        } else if (txtGiaNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống giá nhập");
            return false;
        } else if (txtGiaBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống giá bán");
            return false;
        } else if (!txtGiaNhap.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số");
            return false;
        } else if (!txtGiaBan.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là số");
            return false;
        } else if (!txtDonVi.getText().matches(tenspmau)) {
            JOptionPane.showMessageDialog(this, "Đơn vị không được là số");
            return false;
        }
        return true;

    }

    boolean check2() {

        String tenspmau = "\\D+";
        String slmau = "\\d+";

        if (txtGiaNhap.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống giá nhập");
            return false;
        } else if (txtGiaBan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống giá bán");
            return false;
        } else if (!txtGiaNhap.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Giá nhập phải là số");
            return false;
        } else if (!txtGiaBan.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Giá bán phải là số");
            return false;
        } else if (!txtDonVi.getText().matches(tenspmau)) {
            JOptionPane.showMessageDialog(this, "Đơn vị không được là số");
            return false;
        }
        return true;
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        model.setRowCount(0);
        try {
            String masp = txtTimKiem.getText();
            List<SanPham> list = dao.select();
            for (SanPham d : list) {
                Object[] row = {
                    d.getTenSP(),
                    d.getGiaNhap(),
                    d.getGiaBan(),
                    d.getDonVi(),
                    d.getTenNH(),
                    d.getTenNM(),
                    d.getHangTon(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void tableOnClick() {
        int i = tblBang.getSelectedRow();

        txtTenSP.setText(tblBang.getValueAt(i, 0).toString());
        txtGiaNhap.setText(tblBang.getValueAt(i, 1).toString());
        txtGiaBan.setText(tblBang.getValueAt(i, 2).toString());
        txtDonVi.setText(tblBang.getValueAt(i, 3).toString());
        cbo_MaNH.setSelectedItem(tblBang.getValueAt(i, 4).toString());
        cbo_MaNM.setSelectedItem(tblBang.getValueAt(i, 5).toString());
    }

    void insert() {
        SanPham model = getModel();
        try {
            dao.insert(model);
            load();
            clear();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delete() {
        int i = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm này không ?");
        if (i == 0) {
            String a = txtTenSP.getText();
            SanPham obj = new SanPham(a);
            try {
                dao.delete(obj);
                load();
                clear();
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void update() {
        SanPham model = getModel();
        try {
            dao.update(model);
            load();
            clear();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clear() {
        txtTenSP.setText("");
        txtGiaBan.setText("");
        txtGiaNhap.setText("");
        txtDonVi.setText("");
        cbo_MaNH.setSelectedIndex(0);
        cbo_MaNM.setSelectedIndex(0);
    }

    void filter() {
        DefaultTableModel model = (DefaultTableModel) tblBang.getModel();
        String ft = txtTimKiem.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblBang.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblMaNH = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        lblGia = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        lblTimKiem = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBang = new javax.swing.JTable();
        cbo_MaNH = new javax.swing.JComboBox<>();
        lblGia1 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        lblMaNH1 = new javax.swing.JLabel();
        cbo_MaNM = new javax.swing.JComboBox<>();
        lblGia2 = new javax.swing.JLabel();
        txtDonVi = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(236, 157, 121));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Quản Lý Sản Phẩm");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(180, 20, 240, 40);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(436, 260));

        btnNew.setBackground(new java.awt.Color(255, 102, 102));
        btnNew.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Làm Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(255, 102, 102));
        btnSave.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("Lưu");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(255, 102, 102));
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Sửa");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(255, 102, 102));
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setText("Xóa");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblMaNH.setBackground(new java.awt.Color(255, 255, 255));
        lblMaNH.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMaNH.setForeground(new java.awt.Color(255, 255, 255));
        lblMaNH.setText("Tên Nhóm Hàng");

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTimKiem.setToolTipText("Phải viết hoa chữ đầu");
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        lblGia.setBackground(new java.awt.Color(255, 255, 255));
        lblGia.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGia.setForeground(new java.awt.Color(255, 255, 255));
        lblGia.setText("Giá Nhập");

        lblTenSP.setBackground(new java.awt.Color(255, 255, 255));
        lblTenSP.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTenSP.setForeground(new java.awt.Color(255, 255, 255));
        lblTenSP.setText("Tên Sản Phẩm");

        txtTenSP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtGiaNhap.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        lblTimKiem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        lblTimKiem.setText("Tìm kiếm:");

        tblBang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Sản Phẩm", "Giá Nhập", "Giá Bán", "Đơn Vị", "Mã NH", "Tên NM", "Hàng Tồn"
            }
        ));
        tblBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBang);

        cbo_MaNH.setEditable(true);
        cbo_MaNH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_MaNHActionPerformed(evt);
            }
        });

        lblGia1.setBackground(new java.awt.Color(255, 255, 255));
        lblGia1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGia1.setForeground(new java.awt.Color(255, 255, 255));
        lblGia1.setText("Giá Bán");

        txtGiaBan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        lblMaNH1.setBackground(new java.awt.Color(255, 255, 255));
        lblMaNH1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblMaNH1.setForeground(new java.awt.Color(255, 255, 255));
        lblMaNH1.setText("Tên Nhà Máy");

        cbo_MaNM.setEditable(true);

        lblGia2.setBackground(new java.awt.Color(255, 255, 255));
        lblGia2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblGia2.setForeground(new java.awt.Color(255, 255, 255));
        lblGia2.setText("Đơn Vị");

        txtDonVi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblGia2)
                            .addComponent(lblTenSP)
                            .addComponent(lblMaNH)
                            .addComponent(lblGia)
                            .addComponent(lblGia1)
                            .addComponent(lblMaNH1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenSP)
                            .addComponent(txtDonVi)
                            .addComponent(cbo_MaNH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtGiaNhap)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cbo_MaNM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(372, 372, 372))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 682, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenSP))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGia2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_MaNH, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaNH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGia))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGia1))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_MaNM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaNH1))
                .addGap(86, 86, 86)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimKiem))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void tblBangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBangMouseClicked
        // TODO add your handling code here:
        tableOnClick();
    }//GEN-LAST:event_tblBangMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            // TODO add your handling code here:
            if (check()) {
                insert();
            }
        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:

        // TODO add your handling code here:
        if (check2()) {
            update();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void cbo_MaNHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_MaNHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_MaNHActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbo_MaNH;
    private javax.swing.JComboBox<String> cbo_MaNM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblGia1;
    private javax.swing.JLabel lblGia2;
    private javax.swing.JLabel lblMaNH;
    private javax.swing.JLabel lblMaNH1;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JTable tblBang;
    private javax.swing.JTextField txtDonVi;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
