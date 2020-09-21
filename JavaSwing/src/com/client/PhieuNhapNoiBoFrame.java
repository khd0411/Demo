/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import com.dao.CuaHangDAO;
import com.dao.NhaMayDAO;
import com.dao.PhieuNhapHangTuNhaMayDAO;
import com.dao.PhieuNhapNBDAO;
import com.dao.SanPhamDAO;
import com.helper.Connect;
import com.helper.DateHelper;
import com.helper.DialogHelper;
import com.helper.EmployeeHelper;
import com.model.CuaHang;
import com.model.PhieuNhapNB;
import com.model.SanPham;
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
public class PhieuNhapNoiBoFrame extends javax.swing.JInternalFrame {

    String head[] = {"Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Tên Cửa Hàng", "Mã NV", "Ngày Nhập",};
    DefaultTableModel model = new DefaultTableModel(head, 0);
    PhieuNhapHangTuNhaMayDAO dao2 = new PhieuNhapHangTuNhaMayDAO();
    PhieuNhapNBDAO dao6 = new PhieuNhapNBDAO();
    /**
     * Creates new form CuaHangJInternalFrame
     */
    SanPhamDAO dao3 = new SanPhamDAO();
    NhaMayDAO dao4 = new NhaMayDAO();
    CuaHangDAO dao5 = new CuaHangDAO();

    public PhieuNhapNoiBoFrame() {
        initComponents();
        LoadCH();
        LoadTenSp();
        load();
        AutoCompleteDecorator.decorate(cbo_TenSp);
        AutoCompleteDecorator.decorate(cbo_TenCH);
        loadmanvngay();
        LoadPhieuNB();
    }

    private void LoadPhieuNB() {
        try {
            List<PhieuNhapNB> list = dao6.selectMP();
            for (PhieuNhapNB d : list) {
                txt_MaPhieu.setText(d.getMaPN());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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

    void load() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        model.setRowCount(0);
        try {
            List<PhieuNhapNB> list = dao6.select();
            for (PhieuNhapNB nv : list) {
                Object[] row = {
                    nv.getMaPN(),
                    nv.getTenSP(),
                    nv.getSoLuong(),
                    nv.getDonVi(),
                    nv.getGia(),
                    nv.getTongTien(),
                    nv.getMaNV(),
                    nv.getNgayNhap(),
                    nv.getTenCH(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void filter() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        String ft = txt_TimKiem.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tbl_ThongTinPhieu.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    void reff() {
        txt_SoLuong.setText("");
        txt_Gia.setText("");
        txt_TongTien.setText("");
        txt_DonVi.setText("");
        cbo_TenSp.setSelectedIndex(0);
        cbo_TenCH.setSelectedIndex(0);
        model.setRowCount(0);
    }

    void loadSP() {
        try {
            List<SanPham> list = dao3.loadcombo(cbo_TenSp.getSelectedItem().toString());
            for (SanPham sp : list) {
                sp.getGiaNhap();
                txt_Gia.setText(String.valueOf(sp.getGiaBan()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void loadDonVi() {
        try {
            List<SanPham> list = dao3.loadcombo(cbo_TenSp.getSelectedItem().toString());
            for (SanPham sp : list) {
                sp.getDonVi();
                txt_DonVi.setText(String.valueOf(sp.getDonVi()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    private void LoadCH() {
        try {
            List<CuaHang> list = dao5.select();
            for (CuaHang d : list) {
                cbo_TenCH.addItem(d.getTenCH());
            }
        } catch (Exception e) {
            System.out.println(e);
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
            row.add(cbo_TenCH.getSelectedItem());
            row.add(txt_MaNV.getText());
            row.add(txt_NgayNhap.getText());
            model.addRow(row);
            jTable1.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
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

    PhieuNhapNB getModel() {
        int rows = jTable1.getRowCount();
        PhieuNhapNB model = new PhieuNhapNB();

        for (int row = 0; row < rows; row++) {
            model.setMaPN((String) jTable1.getValueAt(row, 0));
            model.setTenSP((String) jTable1.getValueAt(row, 1));
            model.setSoLuong((String) jTable1.getValueAt(row, 2));
            model.setDonVi((String) jTable1.getValueAt(row, 3));
            model.setGia((String) jTable1.getValueAt(row, 4));
            model.setTongTien((String) jTable1.getValueAt(row, 5));
            model.setMaNV((String) jTable1.getValueAt(row, 7));
            model.setNgayNhap((String) jTable1.getValueAt(row, 8));
            model.setTenCH((String) jTable1.getValueAt(row, 6));
        }
        return model;
    }

    void insert() {
        PhieuNhapNB model = new PhieuNhapNB();
        int rows = jTable1.getRowCount();
        try {
            for (int row = 0; row < rows; row++) {
                model.setMaPN((String) jTable1.getValueAt(row, 0));
                model.setTenSP((String) jTable1.getValueAt(row, 1));
                model.setSoLuong((String) jTable1.getValueAt(row, 2));
                model.setDonVi((String) jTable1.getValueAt(row, 3));
                model.setGia((String) jTable1.getValueAt(row, 4));
                model.setTongTien((String) jTable1.getValueAt(row, 5));
                model.setMaNV((String) jTable1.getValueAt(row, 7));
                model.setNgayNhap((String) jTable1.getValueAt(row, 8));
                model.setTenCH((String) jTable1.getValueAt(row, 6));
                dao6.insert(model);
            }
            JOptionPane.showMessageDialog(this, "Success !!!");
            load();
            reff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PhieuNhapNB getModelTT() {
        int rows = tbl_ThongTinPhieu.getRowCount();
        PhieuNhapNB model = new PhieuNhapNB();

        for (int row = 0; row < rows; row++) {
            model.setMaPN((String) tbl_ThongTinPhieu.getValueAt(row, 0));
        }
        return model;
    }

    void update() {
        PhieuNhapNB model = getModelTT();
        try {
            dao6.update(model);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        jButtonLogin2 = new javax.swing.JButton();
        jButtonLogin4 = new javax.swing.JButton();
        txt_SoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_Gia = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonLogin5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_MaPhieu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_NgayNhap = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbo_TenSp = new javax.swing.JComboBox<>();
        cbo_TenCH = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txt_TK = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        txt_DonVi = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_TongTien = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ThongTinPhieu = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jButtonLogin6 = new javax.swing.JButton();
        txt_TimKiem = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(236, 157, 121));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Quản Lý Phiếu Nhập Hàng Nội Bộ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(206, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(188, 188, 188))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 204, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(436, 260));

        jButtonLogin.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin.setText("Làm Mới");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
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

        jButtonLogin4.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin4.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin4.setText("Xóa");
        jButtonLogin4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin4ActionPerformed(evt);
            }
        });

        txt_SoLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Số lượng:");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Giá:");

        txt_Gia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_Gia.setEnabled(false);
        txt_Gia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_GiaActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Tên Cửa Hàng", "Mã NV", "Ngày Nhập"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButtonLogin5.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin5.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin5.setText("Thêm");
        jButtonLogin5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin5ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tạo Phiếu", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Mã Phiếu");

        txt_MaPhieu.setEditable(false);
        txt_MaPhieu.setEnabled(false);

        jLabel3.setText("MaNV");

        txt_MaNV.setEditable(false);
        txt_MaNV.setEnabled(false);

        jLabel4.setText("Ngày Nhập");

        txt_NgayNhap.setEditable(false);
        txt_NgayNhap.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_NgayNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_MaPhieu, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_MaPhieu, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_MaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_NgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Tổng Tiền");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tìm kiếm:");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tên SP:");

        cbo_TenSp.setEditable(true);
        cbo_TenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TenSpActionPerformed(evt);
            }
        });

        cbo_TenCH.setEditable(true);
        cbo_TenCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TenCHActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tên CH:");

        jLabel97.setBackground(new java.awt.Color(255, 255, 255));
        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Đơn Vị:");

        txt_DonVi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_DonVi.setEnabled(false);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Tổng tiền:");

        txt_TongTien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_TongTien.setEnabled(false);
        txt_TongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TongTienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel97)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbo_TenCH, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cbo_TenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(jLabel7))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_Gia, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txt_SoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txt_TongTien))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(83, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 673, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(81, 81, 81))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(142, 142, 142))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jButtonLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78)
                .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbo_TenSp)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbo_TenCH)
                            .addComponent(jLabel8))
                        .addGap(56, 56, 56))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TK, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102))
        );

        jTabbedPane1.addTab("Tạo Phiếu", jPanel2);

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));

        tbl_ThongTinPhieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Mã NV", "Ngày Nhập", "Tên Cửa Hàng"
            }
        ));
        jScrollPane2.setViewportView(tbl_ThongTinPhieu);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tìm kiếm:");

        jButtonLogin6.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin6.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin6.setText("Xóa");
        jButtonLogin6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin6ActionPerformed(evt);
            }
        });

        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jButtonLogin6, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogin6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(234, 234, 234))
        );

        jTabbedPane1.addTab("Thông Tin Phiếu", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 736, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        reff();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin2ActionPerformed
        // TODO add your handling code here:
        if (check2()) {
            insert();

        }
    }//GEN-LAST:event_jButtonLogin2ActionPerformed

    private void jButtonLogin5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin5ActionPerformed
        // TODO add your handling code here:
        if (check()) {
            LoadToTable();
        }
    }//GEN-LAST:event_jButtonLogin5ActionPerformed

    private void cbo_TenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TenSpActionPerformed
        // TODO add your handling code here:
        loadSP();
        loadDonVi();
    }//GEN-LAST:event_cbo_TenSpActionPerformed

    private void cbo_TenCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TenCHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_TenCHActionPerformed

    private void txt_GiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_GiaActionPerformed
        // TODO add your handling code here:
        loadSP();
    }//GEN-LAST:event_txt_GiaActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void jButtonLogin6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin6ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_jButtonLogin6ActionPerformed

    private void jButtonLogin4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin4ActionPerformed
        // TODO add your handling code here:
        deletetbl();
    }//GEN-LAST:event_jButtonLogin4ActionPerformed

    private void txt_TongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TongTienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TongTienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbo_TenCH;
    private javax.swing.JComboBox<String> cbo_TenSp;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonLogin2;
    private javax.swing.JButton jButtonLogin4;
    private javax.swing.JButton jButtonLogin5;
    private javax.swing.JButton jButtonLogin6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tbl_ThongTinPhieu;
    private javax.swing.JTextField txt_DonVi;
    private javax.swing.JTextField txt_Gia;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_MaPhieu;
    private javax.swing.JTextField txt_NgayNhap;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_TK;
    private javax.swing.JTextField txt_TimKiem;
    private javax.swing.JTextField txt_TongTien;
    // End of variables declaration//GEN-END:variables
}
