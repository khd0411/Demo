/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client;

import com.dao.CuaHangDAO;
import com.dao.NhaMayDAO;

import com.dao.PhieuNhapHangTuNhaMayDAO;
import com.dao.PhieuXuatDAO;
import com.dao.SanPhamDAO;
import com.helper.Connect;
import com.helper.DateHelper;
import com.helper.DialogHelper;
import com.helper.EmployeeHelper;
import com.model.CuaHang;
import com.model.PhieuNhapNB;
import com.model.PhieuNhapNM;
import com.model.PhieuXuat;
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
public class PhieuXuatNoiBoFrame extends javax.swing.JInternalFrame {

    String head[] = {"Mã Phiếu", "Tên SP", "Số Lượng", "Giá", "Đơn Vị", "Tổng Tiền", "Tên Cửa Hàng", "Mã NV", "Ngày Xuất"};
    DefaultTableModel model = new DefaultTableModel(head, 0);

    PhieuNhapHangTuNhaMayDAO dao2 = new PhieuNhapHangTuNhaMayDAO();
    /**
     * Creates new form CuaHangJInternalFrame
     */
    SanPhamDAO dao3 = new SanPhamDAO();
    NhaMayDAO dao4 = new NhaMayDAO();
    CuaHangDAO dao5 = new CuaHangDAO();
    PhieuXuatDAO dao6 = new PhieuXuatDAO();

    public PhieuXuatNoiBoFrame() {
        initComponents();
        LoadCH();
        load();
        LoadTenSp();
        AutoCompleteDecorator.decorate(cbo_TenSp);
        AutoCompleteDecorator.decorate(cbo_TenCH);
        loadmanvngay();
        LoadPhieuX();
    }

    void loadmanvngay() {
        txt_MaNV.setText(EmployeeHelper.USER.getMaNV());
        txt_NgayXuat.setText(DateHelper.toString(DateHelper.now()));
    }

    private void LoadPhieuX() {
        try {
            List<PhieuXuat> list = dao6.selectMP();
            for (PhieuXuat d : list) {
                txt_MaPhieu.setText(d.getMaPhieu());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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


    void reff() {
        txt_SoLuong.setText("");
        txt_Gia.setText("");
        txt_TongTien.setText("");
        txt_DonVi.setText("");
        cbo_TenCH.setSelectedIndex(0);
        cbo_TenSp.setSelectedIndex(0);
        model.setRowCount(0);
    }

    void load() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        model.setRowCount(0);
        try {
            List<PhieuXuat> list = dao6.select();
            for (PhieuXuat nv : list) {
                Object[] row = {
                    nv.getMaPhieu(),
                    nv.getTenSP(),
                    nv.getSoLuong(),
                    nv.getDonVi(),
                    nv.getGia(),
                    nv.getTongTien(),
                    nv.getMaNV(),
                    nv.getNgayXuat(),
                    nv.getTenCH(),};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void filter() {
        DefaultTableModel model = (DefaultTableModel) tbl_ThongTinPhieu.getModel();
        String ft = txt_TimKiem.getText();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        tbl_ThongTinPhieu.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(ft));
    }

    void loadSP() {
        try {
            List<SanPham> list = dao3.loadcombo(cbo_TenSp.getSelectedItem().toString());
            for (SanPham sp : list) {
                sp.getGiaBan();
                txt_Gia.setText(String.valueOf(sp.getGiaBan()));
            }
        } catch (Exception e) {
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
            row.add(txt_NgayXuat.getText());
            model.addRow(row);
            jTable1.setModel(model);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void LoadTenSp() {
        try {
            List<SanPham> list = dao3.select2();
            for (SanPham d : list) {
                cbo_TenSp.addItem(d.getTenSP());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    PhieuXuat getModel() {
        int rows = jTable1.getRowCount();
        PhieuXuat model = new PhieuXuat();
        for (int row = 0; row < rows; row++) {
            model.setMaPhieu((String) jTable1.getValueAt(row, 0));
            model.setTenSP((String) jTable1.getValueAt(row, 1));
            model.setSoLuong((String) jTable1.getValueAt(row, 2));
            model.setDonVi((String) jTable1.getValueAt(row, 3));
            model.setGia((String) jTable1.getValueAt(row, 4));
            model.setTongTien((String) jTable1.getValueAt(row, 5));
            model.setTenCH((String) jTable1.getValueAt(row, 6));
            model.setMaNV((String) jTable1.getValueAt(row, 7));
            model.setNgayXuat((String) jTable1.getValueAt(row, 8));
        }
        return model;
    }

    void insert() {
        PhieuXuat model = new PhieuXuat();
        int rows = jTable1.getRowCount();
        try {
            for (int row = 0; row < rows; row++) {
                model.setMaPhieu((String) jTable1.getValueAt(row, 0));
                model.setTenSP((String) jTable1.getValueAt(row, 1));
                model.setSoLuong((String) jTable1.getValueAt(row, 2));
                model.setDonVi((String) jTable1.getValueAt(row, 3));
                model.setGia((String) jTable1.getValueAt(row, 4));
                model.setTongTien((String) jTable1.getValueAt(row, 5));
                model.setTenCH((String) jTable1.getValueAt(row, 6));
                model.setMaNV((String) jTable1.getValueAt(row, 7));
                model.setNgayXuat((String) jTable1.getValueAt(row, 8));
                dao6.insert(model);
            }
            JOptionPane.showMessageDialog(this, "Success !!!");
            load();
            reff();
            cbo_TenSp.removeAllItems();
            LoadTenSp();
            cbo_TenSp.setSelectedIndex(0);
            loadSP();
            LoadPhieuX();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PhieuXuat getModelTT() {
        int rows = tbl_ThongTinPhieu.getRowCount();
        PhieuXuat model = new PhieuXuat();

        for (int row = 0; row < rows; row++) {
            model.setMaPhieu((String) tbl_ThongTinPhieu.getValueAt(row, 0));
        }
        return model;
    }

    void update() {
        PhieuXuat model = getModelTT();
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
        jPanel4 = new javax.swing.JPanel();
        txt_TimKiem = new javax.swing.JTextField();
        jButtonLogin6 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_ThongTinPhieu = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonLogin = new javax.swing.JButton();
        jButtonLogin2 = new javax.swing.JButton();
        jButtonLogin4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txt_SoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        txt_Gia = new javax.swing.JTextField();
        txt_TongTien = new javax.swing.JTextField();
        jButtonLogin5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_MaPhieu = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_MaNV = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_NgayXuat = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbo_TenCH = new javax.swing.JComboBox<>();
        cbo_TenSp = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_DonVi = new javax.swing.JTextField();
        jLabel97 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(236, 157, 121));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 255));
        jLabel1.setText("Quản Lý Phiếu Xuất Hàng");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(231, 231, 231)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));

        txt_TimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TimKiemActionPerformed(evt);
            }
        });

        jButtonLogin6.setBackground(new java.awt.Color(255, 102, 102));
        jButtonLogin6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonLogin6.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLogin6.setText("Xóa");
        jButtonLogin6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogin6ActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Tìm kiếm:");

        tbl_ThongTinPhieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Mã NV", "Ngày Xuất", "Tên Cửa Hàng"
            }
        ));
        jScrollPane2.setViewportView(tbl_ThongTinPhieu);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jButtonLogin6, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(136, 136, 136))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel14))
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLogin6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(135, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông Tin Phiếu", jPanel4);

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

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tổng Tiền:");

        txt_SoLuong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Số lượng:");

        jPasswordField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Giá:");

        txt_Gia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_Gia.setEnabled(false);

        txt_TongTien.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_TongTien.setEnabled(false);

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

        jLabel4.setText("Ngày Xuất");

        txt_NgayXuat.setEditable(false);
        txt_NgayXuat.setEnabled(false);

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
                    .addComponent(txt_NgayXuat, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
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
                    .addComponent(txt_NgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel12.setText("Kiểm Tra:");

        cbo_TenCH.setEditable(true);
        cbo_TenCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TenCHActionPerformed(evt);
            }
        });

        cbo_TenSp.setEditable(true);
        cbo_TenSp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_TenSpActionPerformed(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tên SP:");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Tên CH:");

        txt_DonVi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_DonVi.setEnabled(false);
        txt_DonVi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DonViActionPerformed(evt);
            }
        });

        jLabel97.setBackground(new java.awt.Color(255, 255, 255));
        jLabel97.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Đơn Vị:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu", "Tên SP", "Số Lượng", "Đơn Vị", "Giá", "Tổng Tiền", "Tên Cửa Hàng", "Mã NV", "Ngày Xuất"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jButtonLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 61, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbo_TenSp, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addGap(32, 32, 32)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(153, 153, 153))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel97))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txt_Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cbo_TenCH, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(52, 52, 52))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cbo_TenSp)
                            .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(jLabel7)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_DonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel97)
                        .addComponent(txt_Gia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbo_TenCH))
                        .addGap(59, 59, 59))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonLogin5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonLogin2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonLogin4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(74, 74, 74))
        );

        jTabbedPane1.addTab("Tạo Phiếu", jPanel2);

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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
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

    private void jButtonLogin5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin5ActionPerformed
        if (check()) {
            LoadToTable();
        }
    }//GEN-LAST:event_jButtonLogin5ActionPerformed

    private void jButtonLogin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin2ActionPerformed
        // TODO add your handling code here:
        if (check2()) {
            insert();

        }
    }//GEN-LAST:event_jButtonLogin2ActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        // TODO add your handling code here:
        reff();
    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void cbo_TenCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TenCHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_TenCHActionPerformed

    private void cbo_TenSpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_TenSpActionPerformed
        // TODO add your handling code here:
        loadSP();
        loadDonVi();
    }//GEN-LAST:event_cbo_TenSpActionPerformed

    private void txt_TimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TimKiemActionPerformed
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_txt_TimKiemActionPerformed

    private void txt_DonViActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DonViActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_DonViActionPerformed

    private void jButtonLogin6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin6ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_jButtonLogin6ActionPerformed

    private void jButtonLogin4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogin4ActionPerformed
        // TODO add your handling code here:
        deletetbl();
    }//GEN-LAST:event_jButtonLogin4ActionPerformed


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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tbl_ThongTinPhieu;
    private javax.swing.JTextField txt_DonVi;
    private javax.swing.JTextField txt_Gia;
    private javax.swing.JTextField txt_MaNV;
    private javax.swing.JTextField txt_MaPhieu;
    private javax.swing.JTextField txt_NgayXuat;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JTextField txt_TimKiem;
    private javax.swing.JTextField txt_TongTien;
    // End of variables declaration//GEN-END:variables

}
