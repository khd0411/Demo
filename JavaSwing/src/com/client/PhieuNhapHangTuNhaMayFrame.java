/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import com.dao.NhaMayDAO;
import com.dao.PhieuNhapHangTuNhaMayDAO;
import com.dao.SanPhamDAO;
import com.helper.DateHelper;
import com.helper.DialogHelper;
import com.helper.EmployeeHelper;
import com.model.NhaMay;
import com.model.PhieuNhapNM;
import com.model.SanPham;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Administrator
 */
public class PhieuNhapHangTuNhaMayFrame extends javax.swing.JInternalFrame {

    String head[] = {"Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Mã NV", "Ngày Nhập","Tên NM"};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    PhieuNhapHangTuNhaMayDAO dao2 = new PhieuNhapHangTuNhaMayDAO();
    /**
     * Creates new form CuaHangJInternalFrame
     */
    SanPhamDAO dao3 = new SanPhamDAO();
    NhaMayDAO dao4 = new NhaMayDAO();

    public PhieuNhapHangTuNhaMayFrame() {
        initComponents();
        AutoCompleteDecorator.decorate(cbo_TenSp);
        AutoCompleteDecorator.decorate(cbo_Tennm);
        LoadTenSp();
        LoadTenNM();
        load();
        loadmanvngay();
        LoadPhieuNM();
    }

    void loadmanvngay() {
        txt_MaNV.setText(EmployeeHelper.USER.getMaNV());
        txt_NgayNhap.setText(DateHelper.toString(DateHelper.now()));
    }

    boolean check() {
        String slmau = "\\d+";
        if (txt_SoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống số lượng");
            return false;
        } else if (!txt_SoLuong.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số");
            return false;
        } else if (txt_TongTien.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tổng tiền trống, vui lòng nhấn vào nút tổng tiền !");
            return false;
        } else {
            return true;
        }

    }

    boolean check2() {
        if (jTable1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm để lưu");
            return false;
        } else {
            return true;
        }
    }

    void filter() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        String ft = txt_TimKiem.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tbl_ThongTinPhieu.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhapNM> list = dao2.select();
            for (PhieuNhapNM nv : list) {
                Object[] row = {
                    nv.getMaPhieuNM(),
                    nv.getTenSP(),
                    nv.getSoLuong(),
                    nv.getDonVi(),
                    nv.getGia(),
                    nv.getTongTien(),
                    nv.getMaNV(),
                    nv.getNgayNhap(),
                    nv.getTenNM(),
                    nv.getTrangThai()};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadSP() {
        try {
            List<SanPham> list = dao3.loadcombo(cbo_TenSp.getSelectedItem().toString());
            for (SanPham sp : list) {
                sp.getGiaNhap();
                txt_Gia.setText(String.valueOf(sp.getGiaNhap()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadDonVi() {
        try {
            List<SanPham> list = dao3.loadcombo(cbo_TenSp.getSelectedItem().toString());
            for (SanPham sp : list) {
                sp.getGiaNhap();
                sp.getTenNM();
                txt_DonVi.setText(sp.getDonVi());
                cbo_Tennm.setSelectedItem(sp.getTenNM());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void LoadToTable() {
        try {
            Vector row = new Vector();
            row.add(txt_MaPhieu.getText());
            row.add(cbo_TenSp.getSelectedItem());
            row.add(txt_SoLuong.getText());
            row.add(txt_DonVi.getText());
            row.add(txt_Gia.getText());
            row.add(txt_TongTien.getText());
            row.add(txt_MaNV.getText());
            row.add(txt_NgayNhap.getText());
            row.add(cbo_Tennm.getSelectedItem());
            model.addRow(row);
            jTable1.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void reff() {
        txt_SoLuong.setText("");
        txt_Gia.setText("");
        txt_TongTien.setText("");
        txt_DonVi.setText("");
        cbo_TenSp.setSelectedIndex(0);
        cbo_Tennm.setSelectedIndex(0);
        model.setRowCount(0);
    }

    PhieuNhapNM getModel() {
        int rows = jTable1.getRowCount();
        PhieuNhapNM model = new PhieuNhapNM();

        for (int row = 0; row < rows; row++) {
            model.setMaPhieuNM((String) jTable1.getValueAt(row, 0));
            model.setTenSP((String) jTable1.getValueAt(row, 1));
            model.setSoLuong((String) jTable1.getValueAt(row, 2));
            model.setDonVi((String) jTable1.getValueAt(row, 3));
            model.setGia((String) jTable1.getValueAt(row, 4));
            model.setTongTien((String) jTable1.getValueAt(row, 5));
            model.setMaNV((String) jTable1.getValueAt(row, 6));
            model.setNgayNhap((String) jTable1.getValueAt(row, 7));
            model.setTenNM((String) jTable1.getValueAt(row, 8));
        }
        return model;
    }

    PhieuNhapNM getModelTT() {
        int rows = tbl_ThongTinPhieu.getRowCount();
        PhieuNhapNM model = new PhieuNhapNM();

        for (int row = 0; row < rows; row++) {
            model.setMaPhieuNM((String) tbl_ThongTinPhieu.getValueAt(row, 0));
        }
        return model;
    }

    void insert() {
        PhieuNhapNM model = new PhieuNhapNM();
        int rows = jTable1.getRowCount();
        try {
            for (int row = 0; row < rows; row++) {
                model.setMaPhieuNM((String) jTable1.getValueAt(row, 0));
                model.setTenSP((String) jTable1.getValueAt(row, 1));
                model.setSoLuong((String) jTable1.getValueAt(row, 2));
                model.setDonVi((String) jTable1.getValueAt(row, 3));
                model.setGia((String) jTable1.getValueAt(row, 4));
                model.setTongTien((String) jTable1.getValueAt(row, 5));
                model.setMaNV((String) jTable1.getValueAt(row, 6));
                model.setNgayNhap((String) jTable1.getValueAt(row, 7));
                model.setTenNM((String) jTable1.getValueAt(row, 8));
                dao2.insert(model);
            }
            JOptionPane.showMessageDialog(this, "Success !!!");
            load();
            reff();
            LoadPhieuNM();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadTenSp() {
        try {
            List<SanPham> list = dao3.select();
            for (SanPham d : list) {
                cbo_TenSp.addItem(d.getTenSP());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void LoadTenNM() {
        try {
            List<NhaMay> list = dao4.select();
            for (NhaMay d : list) {
                cbo_Tennm.addItem(d.getTenNM());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void LoadPhieuNM() {
        try {
            List<PhieuNhapNM> list = dao2.selectMP();
            for (PhieuNhapNM d : list) {
                txt_MaPhieu.setText(d.getMaPhieuNM());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void update() {
        PhieuNhapNM model = getModelTT();
        try {
            dao2.update(model);
            load();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deletetbl() {
        int i = jTable1.getSelectedRow();
        model.removeRow(i);
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
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel27 = new javax.swing.JPanel();
        btn_reff12 = new javax.swing.JButton();
        btn_save12 = new javax.swing.JButton();
        btn_add12 = new javax.swing.JButton();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        txt_SoLuong = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        txt_Gia = new javax.swing.JTextField();
        txt_TongTien = new javax.swing.JTextField();
        btn_update12 = new javax.swing.JButton();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel92 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        txt_MaPhieu = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        txt_NgayNhap = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbo_TenSp = new javax.swing.JComboBox<>();
        cbo_Tennm = new javax.swing.JComboBox<>();
        jLabel97 = new javax.swing.JLabel();
        txt_DonVi = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tbl_ThongTinPhieu = new javax.swing.JTable();
        jLabel96 = new javax.swing.JLabel();
        txt_TimKiem = new javax.swing.JTextField();
        btn_del12 = new javax.swing.JButton();

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tổng Tiền:");

        jButton1.setText("jButton1");

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(236, 157, 121));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Quản Lý Nhập Hàng Từ Nhà Máy");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(180, 20, 400, 40);

        jPanel27.setBackground(new java.awt.Color(153, 204, 255));
        jPanel27.setMinimumSize(new java.awt.Dimension(436, 260));

        btn_reff12.setBackground(new java.awt.Color(255, 102, 102));
        btn_reff12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_reff12.setForeground(new java.awt.Color(255, 255, 255));
        btn_reff12.setText("Làm Mới");
        btn_reff12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reffActionPerformed(evt);
            }
        });

        btn_save12.setBackground(new java.awt.Color(255, 102, 102));
        btn_save12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_save12.setForeground(new java.awt.Color(255, 255, 255));
        btn_save12.setText("Lưu");
        btn_save12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_add12.setBackground(new java.awt.Color(255, 102, 102));
        btn_add12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_add12.setForeground(new java.awt.Color(255, 255, 255));
        btn_add12.setText("Thêm");
        btn_add12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel89.setBackground(new java.awt.Color(255, 255, 255));
        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("Giá:");

        jLabel90.setBackground(new java.awt.Color(255, 255, 255));
        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(255, 255, 255));
        jLabel90.setText("Số Lượng:");

        txt_SoLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel91.setBackground(new java.awt.Color(255, 255, 255));
        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(255, 255, 255));
        jLabel91.setText("Tên SP:");

        txt_Gia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_Gia.setEnabled(false);
        txt_Gia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_GiaActionPerformed(evt);
            }
        });

        txt_TongTien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_TongTien.setEnabled(false);

        btn_update12.setBackground(new java.awt.Color(255, 102, 102));
        btn_update12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_update12.setForeground(new java.awt.Color(255, 255, 255));
        btn_update12.setText("Xóa");
        btn_update12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_update12ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Mã NV", "Ngày Nhập", "Tên Nhà Máy"
            }
        ));
        jScrollPane13.setViewportView(jTable1);

        jLabel92.setBackground(new java.awt.Color(255, 255, 255));
        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 255, 255));
        jLabel92.setText("Tên NM:");

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo Phiếu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel26.setForeground(new java.awt.Color(255, 255, 255));

        jLabel93.setText("Mã Phiếu");

        txt_MaPhieu.setEnabled(false);

        jLabel94.setText("MaNV");

        txt_MaNV.setEnabled(false);

        jLabel95.setText("Ngày Nhập");

        txt_NgayNhap.setEnabled(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel95)
                    .addComponent(jLabel94)
                    .addComponent(jLabel93))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_NgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_MaPhieu, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93)
                    .addComponent(txt_MaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel95)))
        );

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Tổng Tiền:");

        jButton2.setText("Tổng Tiền");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tìm kiếm:");

        cbo_TenSp.setEditable(true);
        cbo_TenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TenSpActionPerformed(evt);
            }
        });

        cbo_Tennm.setEditable(true);
        cbo_Tennm.setEnabled(false);
        cbo_Tennm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TennmActionPerformed(evt);
            }
        });

        jLabel97.setBackground(new java.awt.Color(255, 255, 255));
        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Đơn Vị:");

        txt_DonVi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_DonVi.setEnabled(false);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel27Layout.createSequentialGroup()
                        .addComponent(jScrollPane13)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbo_Tennm, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel91)
                                .addGap(18, 18, 18)
                                .addComponent(cbo_TenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel97)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                        .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel90)
                                .addGap(18, 18, 18)
                                .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel27Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33))))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 164, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(90, 90, 90))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(48, 48, 48)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(199, 199, 199))))
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(btn_reff12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(btn_add12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76)
                .addComponent(btn_save12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(btn_update12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_TenSp)
                    .addComponent(jLabel91)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel90))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel97)
                    .addComponent(txt_Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel89))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbo_Tennm)
                    .addComponent(jLabel92))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(19, 19, 19)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_reff12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_add12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_update12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Tạo Phiếu", jPanel27);

        jPanel28.setBackground(new java.awt.Color(153, 204, 255));

        tbl_ThongTinPhieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Mã NV", "Ngày Nhập", "Tên Nhà Máy", "Trạng Thái"
            }
        ));
        jScrollPane14.setViewportView(tbl_ThongTinPhieu);

        jLabel96.setBackground(new java.awt.Color(255, 255, 255));
        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Tìm kiếm:");

        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        btn_del12.setBackground(new java.awt.Color(255, 102, 102));
        btn_del12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_del12.setForeground(new java.awt.Color(255, 255, 255));
        btn_del12.setText("Xóa");
        btn_del12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_del12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 755, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel96)
                .addGap(33, 33, 33)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(145, 145, 145))
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(btn_del12, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_del12)
                .addContainerGap(248, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông Tin Phiếu", jPanel28);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbo_TennmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TennmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_TennmActionPerformed

    private void cbo_TenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TenSpActionPerformed
        // TODO add your handling code here:
        loadSP();
        loadDonVi();
    }//GEN-LAST:event_cbo_TenSpActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String slmau = "\\d+";

        if (txt_SoLuong.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống số lượng");
        } else if (!txt_SoLuong.getText().matches(slmau)) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số");
        } else {
            int a = Integer.parseInt(txt_Gia.getText());
            int b = Integer.parseInt(txt_SoLuong.getText());
            String c = String.valueOf(a * b);
            txt_TongTien.setText(c);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_GiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_GiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_GiaActionPerformed

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (check()) {
            LoadToTable();
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //9 TODO add your handling code here:
        if (check2()) {
            insert();
        }

    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_reffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reffActionPerformed
        // TODO add your handling code here:
        reff();
    }//GEN-LAST:event_btn_reffActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void btn_del12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_del12ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btn_del12ActionPerformed

    private void btn_update12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_update12ActionPerformed
        // TODO add your handling code here:
        deletetbl();
    }//GEN-LAST:event_btn_update12ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add12;
    private javax.swing.JButton btn_del12;
    private javax.swing.JButton btn_reff12;
    private javax.swing.JButton btn_save12;
    private javax.swing.JButton btn_update12;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbo_TenSp;
    private javax.swing.JComboBox<String> cbo_Tennm;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbl_ThongTinPhieu;
    private javax.swing.JTextField txt_DonVi;
    private javax.swing.JTextField txt_Gia;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_MaPhieu;
    private javax.swing.JTextField txt_NgayNhap;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_TimKiem;
    private javax.swing.JTextField txt_TongTien;
    // End of variables declaration//GEN-END:variables

}
