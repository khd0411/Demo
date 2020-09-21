/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import com.admin.MainFrom;
import com.dao.NhanVienDAO;
import com.helper.Connect;
import com.helper.DialogHelper;
import com.helper.EmployeeHelper;
import com.model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Administrator
 */
public class NhanVienFrame extends javax.swing.JInternalFrame {

    /**
     * Creates new form CuaHangJInternalFrame
     */
    public NhanVienFrame() {
        initComponents();
        this.load();
    }
    NhanVienDAO dao = new NhanVienDAO();



    void load() {
        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NhanVien> list = dao.selectByKeyword(keyword);
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getMatKhau(),
                    nv.getHoTen(),
                    nv.getVaiTro() ? "Nhân Viên" : "Trưởng kho",
                    nv.getDiaChi()};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void newform() {
        txtMaNV.setText("");
        txtHoten.setText("");
        txtDiaChi.setText("");
        txtMatKhau.setText("");
        txtTimKiem.setText("");
        buttonGroup1.clearSelection();
    }

    void insert() {
        NhanVien model = getModel();

        try {
            dao.insert(model);
            this.load();

            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Mã cửa hàng đã tồn tại! thao tác thêm thất bại!");
        }

    }

    void update() {
        NhanVien model = getModel();
        try {
            dao.update(model);
            this.load();
            this.newform();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
        }

    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String manv = txtMaNV.getText();
            try {
                dao.delete(manv);
                this.load();
                this.newform();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void filter() {
        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        String ft = txtTimKiem.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tblGridView.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    boolean check1() throws SQLException {
        String hoten = txtHoten.getText();
        String so = "\\D+";
        String sql = "select MaNV from NhanVien";
        List<NhanVien> list = dao.select();
        for (NhanVien nv : list) {
            Object[] row = {
                nv.getMaNV()};
            if (txtMaNV.getText().matches(nv.getMaNV())) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại");
                return false;
            }
        }
        if (txtMaNV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống mã nhân viên");
            return false;
        }
        if (txtDiaChi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống địa chỉ");
            return false;
        }
        if (txtHoten.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống họ tên");
            return false;
        }
        if (!hoten.matches(so)) {
            JOptionPane.showMessageDialog(this, "Họ tên không được là số");
            return false;
        }
        if (txtMatKhau.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống mật khẩu");
            return false;
        }
        if (!rboNhanVien.isSelected() && !rboTruongKho.isSelected()) {
            JOptionPane.showMessageDialog(this, "Bắt buộc phải chọn vai trò");
            return false;
        }
        return true;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        jButtonLogin3 = new javax.swing.JButton();
        jButtonLogin4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtHoten = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        rboNhanVien = new javax.swing.JRadioButton();
        rboTruongKho = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        jButtonLogin2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jLabel2.setText("jLabel2");

        setClosable(true);

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(436, 260));

        jButtonLogin.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin.setText("Làm mới");
        jButtonLogin.setToolTipText("");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jButtonLogin3.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin3.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin3.setText("Sửa");
        jButtonLogin3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin3ActionPerformed(evt);
            }
        });

        jButtonLogin4.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin4.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin4.setText("Xóa");
        jButtonLogin4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin4ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Mã Nhân Viên");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Họ Và Tên");

        tblGridView.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Mật khẩu", "Họ và tên", "Vai trò", "Đại chỉ"
            }
        ));
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridView);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Địa Chỉ");

        jPanel1.setBackground(new java.awt.Color(236, 157, 121));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("NHÂN VIÊN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(328, 328, 328)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        txtHoten.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Vai Trò");

        buttonGroup1.add(rboNhanVien);
        rboNhanVien.setText("Nhân Viên");

        buttonGroup1.add(rboTruongKho);
        rboTruongKho.setText("Trưởng Kho");

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mật Khẩu");

        txtMatKhau.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtTimKiem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jButtonLogin2.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin2.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin2.setText("Lưu");
        jButtonLogin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin2ActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tìm kiếm:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButtonLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                .addComponent(jButtonLogin3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98)
                .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel13)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel9)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rboNhanVien)
                                .addGap(31, 31, 31)
                                .addComponent(rboTruongKho)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtHoten, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                        .addComponent(txtMaNV))
                                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoten, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(rboNhanVien)
                            .addComponent(rboTruongKho))
                        .addGap(18, 18, 18)
                        .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(234, 234, 234))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLogin3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(174, 174, 174))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        int r = tblGridView.getSelectedRow();
        if (r < 0) {
            return;

        }
        txtMaNV.setText(tblGridView.getValueAt(r, 0).toString());
        txtMatKhau.setText(tblGridView.getValueAt(r, 1).toString());
        txtHoten.setText(tblGridView.getValueAt(r, 2).toString());

        if ((tblGridView.getValueAt(r, 3)).equals("Nhân Viên")) {
            rboNhanVien.setSelected(true);
        } else {
            rboTruongKho.setSelected(true);
        }
        txtDiaChi.setText(tblGridView.getValueAt(r, 4).toString());
    }//GEN-LAST:event_tblGridViewMouseClicked

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        this.newform();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin2ActionPerformed
        try {
            // TODO add your handling code here:
            if (check1()) {
                insert();
            }
        } catch (SQLException ex) {
        }

    }//GEN-LAST:event_jButtonLogin2ActionPerformed

    private void jButtonLogin3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin3ActionPerformed
//        if (check2()) {
//            update();
//        }
        update();
    }//GEN-LAST:event_jButtonLogin3ActionPerformed

    private void jButtonLogin4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin4ActionPerformed
        this.delete();
    }//GEN-LAST:event_jButtonLogin4ActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        this.filter();
    }//GEN-LAST:event_txtTimKiemKeyReleased
    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setMatKhau(txtMatKhau.getText());
        model.setHoTen(txtHoten.getText());
        if (rboTruongKho.isSelected()) {
            model.setVaiTro(true);
        } else {
            model.setVaiTro(false);
        }

        model.setDiaChi(txtDiaChi.getText());

        return model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLogin2;
    private javax.swing.JButton jButtonLogin3;
    private javax.swing.JButton jButtonLogin4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rboNhanVien;
    private javax.swing.JRadioButton rboTruongKho;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHoten;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
