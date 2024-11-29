/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package programutama;
import methods.ConnectToDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author brand
 */
public class HalMenuPelanggan extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    private Connection varConnection;
    private DefaultTableModel tm;
    private PreparedStatement ps;
    private ResultSet rs;
    
    private int IdObat, StokObat, StokObatBaru;
    
    public HalMenuPelanggan() {
        initComponents();
        InsertComboVal();
        refreshTable();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    // method untuk mengisi combo box dengan nama obat
    private void InsertComboVal() {
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement("SELECT Nama_obat FROM obat");
            ResultSet Hasil = ps.executeQuery();
            while(Hasil.next()) {
                ComboNamaObat.addItem(Hasil.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Eror, koneksi gagal: " + e);
        }
    }
    
    // method untuk mengonversi nama obat ke id obat
    private int konversiNamaObatKeId(String NamaObat){
        int IdObat = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                    "SELECT Id_obat "
                    + "FROM obat "
                    + "WHERE Nama_obat = ?");
            ps.setString(1, NamaObat);
            rs = ps.executeQuery();
            rs.next();
            IdObat = rs.getInt(1);
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return IdObat;
    }
    
    // method untuk menghitung total bayar berdasarkan obat yang dibeli
    private int hitungTotal(int IdObat, int jumlah){
        int HargaObat = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                    "SELECT Harga_obat "
                    + "FROM obat "
                    + "WHERE Id_obat = ?");
            ps.setInt(1, IdObat);
            rs = ps.executeQuery();
            rs.next();
            HargaObat = rs.getInt(1);
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return HargaObat * jumlah;
    }
    
    // method untuk mengambil stok obat
    private int getStok(int IdObat){
        int StokObat = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // koneksi dengan database
            ps = varConnection.prepareStatement(
                    "SELECT Stok_obat "
                    + "FROM obat "
                    + "WHERE Id_obat = ?");
            ps.setInt(1, IdObat);
            rs = ps.executeQuery();
            rs.next();
            StokObat = rs.getInt(1);
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return StokObat;
    }
    
    // method untuk mengambil jumlah beli yang sudah tersimpan di database
    private int getJumlahBeli(int IdTransaksi){
        int JumlahBeli = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // koneksi dengan database
            ps = varConnection.prepareStatement(
                    "SELECT Jumlah_obat "
                    + "FROM transaksi "
                    + "WHERE Id_transaksi = ?");
            ps.setInt(1, IdTransaksi);
            rs = ps.executeQuery();
            rs.next();
            JumlahBeli = rs.getInt(1);
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return JumlahBeli;
    }
    
    // method untuk menampilkan data transaksi pengguna tertentu
    private void refreshTable(){
        String[] Kolom = {"ID", "Tanggal", "Nama Obat", "Jumlah", "Total"};
        tm = new DefaultTableModel(null, Kolom);
        DataTransaksi.setModel(tm);
        tm.getDataVector().removeAllElements();
        
        try {
            varConnection = ConnectToDB.tryConnect(); // koneksi dengan database
            ps = varConnection.prepareStatement(
                    "SELECT transaksi.Id_transaksi, transaksi.Tanggal_transaksi, "
                    + "obat.Nama_obat, transaksi.Jumlah_obat, transaksi.Total_harga "
                    + "FROM transaksi "
                    + "JOIN obat "
                    + "ON transaksi.Id_obat = obat.Id_obat "
                    + "WHERE transaksi.Id_user = " + HalLogin.IdPelangganLogin);
            rs = ps.executeQuery();
            while(rs.next()) {
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
                };
                tm.addRow(data);
            }
        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
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
        BtnPerbarui = new javax.swing.JButton();
        BtnHapus = new javax.swing.JButton();
        BtnBeli = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        BtnLogout = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        ComboNamaObat = new javax.swing.JComboBox<>();
        InputJumlah = new javax.swing.JSpinner();
        TampilTotal = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        DataTransaksi = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(1, 38, 117));

        BtnPerbarui.setBackground(new java.awt.Color(252, 205, 42));
        BtnPerbarui.setFont(new java.awt.Font("STXihei", 0, 24)); // NOI18N
        BtnPerbarui.setText("Perbarui");
        BtnPerbarui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerbaruiActionPerformed(evt);
            }
        });

        BtnHapus.setBackground(new java.awt.Color(252, 0, 0));
        BtnHapus.setFont(new java.awt.Font("STXihei", 0, 24)); // NOI18N
        BtnHapus.setForeground(new java.awt.Color(255, 255, 255));
        BtnHapus.setText("Hapus");
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });

        BtnBeli.setBackground(new java.awt.Color(114, 191, 120));
        BtnBeli.setFont(new java.awt.Font("STXihei", 0, 24)); // NOI18N
        BtnBeli.setText("Beli");
        BtnBeli.setToolTipText("");
        BtnBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBeliActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("STXihei", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("KAMI MENJUAL BARANG MURNI DI SINI");

        BtnLogout.setBackground(new java.awt.Color(252, 0, 0));
        BtnLogout.setFont(new java.awt.Font("STXihei", 0, 24)); // NOI18N
        BtnLogout.setForeground(new java.awt.Color(255, 255, 255));
        BtnLogout.setText("Logout");
        BtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogoutActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(80, 188, 255));

        ComboNamaObat.setFont(new java.awt.Font("STXihei", 0, 16)); // NOI18N
        ComboNamaObat.setToolTipText("Pilih");
        ComboNamaObat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboNamaObatItemStateChanged(evt);
            }
        });

        InputJumlah.setFont(new java.awt.Font("STXihei", 0, 16)); // NOI18N
        InputJumlah.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        InputJumlah.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                InputJumlahStateChanged(evt);
            }
        });

        TampilTotal.setFont(new java.awt.Font("STXihei", 0, 16)); // NOI18N
        TampilTotal.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(ComboNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(InputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TampilTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TampilTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(BtnPerbarui, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtnBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(491, 491, 491))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(261, 261, 261))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnLogout))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnBeli)
                            .addComponent(BtnPerbarui)
                            .addComponent(BtnHapus))))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        DataTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        DataTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Transaksi", "Tanggal", "Obat", "Jumlah", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DataTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DataTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(DataTransaksi);
        if (DataTransaksi.getColumnModel().getColumnCount() > 0) {
            DataTransaksi.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("STXihei", 3, 160)); // NOI18N
        jLabel6.setText("INI");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("STXihei", 3, 60)); // NOI18N
        jLabel7.setText("TRANSAKSIMU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 614, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DataTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataTransaksiMouseClicked
        // mengisi input dengan data dari tabel yang dipilih pengguna 
        ComboNamaObat.setSelectedItem(tm.getValueAt(DataTransaksi.getSelectedRow(), 2));
        InputJumlah.setValue(Integer.valueOf(tm.getValueAt(DataTransaksi.getSelectedRow(), 3).toString()));
        TampilTotal.setText(tm.getValueAt(DataTransaksi.getSelectedRow(), 4).toString());
    }//GEN-LAST:event_DataTransaksiMouseClicked

    private void ComboNamaObatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboNamaObatItemStateChanged
        // mengubah total bayar jika nama obat diubah
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        TampilTotal.setText(String.valueOf(hitungTotal(IdObat, (Integer)InputJumlah.getValue())));
    }//GEN-LAST:event_ComboNamaObatItemStateChanged

    private void InputJumlahStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_InputJumlahStateChanged
        // mengubah total bayar jika jumlah diubah
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        TampilTotal.setText(String.valueOf(hitungTotal(IdObat, (Integer)InputJumlah.getValue())));
    }//GEN-LAST:event_InputJumlahStateChanged

    private void BtnBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBeliActionPerformed
        // mengubah nama obat menjadi id karena tabel transaksi hanya memiliki kolom id obat sebagai penghubung ke tabel obat
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        // mengambil stok obat lama
        StokObat = getStok(IdObat);
        // menghitung stok obat yang baru
        StokObatBaru = StokObat - (Integer)InputJumlah.getValue();
        
        // jika jumlah yang akan dibeli melebihi stok obat, beri pesan
        if((Integer)InputJumlah.getValue() > StokObat){
            JOptionPane.showMessageDialog(null, "Stok tidak mencukupi.\nSilakan kurangi jumlah.\nStok: " + StokObat);
        // jika tidak
        } else {
            try {
                // memasukkan data transaksi ke database
                ps = varConnection.prepareStatement("INSERT INTO transaksi VALUES (null, ?, ?, ?, ?, ?)");
                ps.setString(1, java.time.LocalDate.now().toString());
                ps.setInt(2, HalLogin.IdPelangganLogin);
                ps.setInt(3, IdObat);
                ps.setInt(4, (Integer)InputJumlah.getValue());
                ps.setInt(5, Integer.parseInt(TampilTotal.getText()));
                ps.executeUpdate();

                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();

                // menampilkan data transaksi dan reset input
                refreshTable();
                ComboNamaObat.resetKeyboardActions();
                TampilTotal.setText("");
                InputJumlah.setValue(0);

            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnBeliActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        // meminta konfirmasi dari pengguna
        // jika yakin menghapus
        if (
            JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menghapus Data Ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.YES_OPTION) {
            // mengubah nama obat menjadi id karena tabel transaksi hanya memiliki kolom id obat sebagai penghubung ke tabel obat
            IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
            // mengambil stok obat lama
            StokObat = getStok(IdObat);
            // menghitung stok obat yang baru
            StokObatBaru = StokObat + (Integer)InputJumlah.getValue();
            
            try {
                // menghapus data yang dipilih
                ps = varConnection.prepareStatement("DELETE FROM transaksi WHERE Id_transaksi = ?");
                ps.setInt(1, Integer.parseInt(tm.getValueAt(DataTransaksi.getSelectedRow(),0).toString()));
                ps.executeUpdate();
                
                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();

                // menampilkan data transaksi dan reset input
                refreshTable();
                ComboNamaObat.resetKeyboardActions();
                TampilTotal.setText("");
                InputJumlah.setValue(0);
            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnPerbaruiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerbaruiActionPerformed
        // mengubah nama obat menjadi id karena tabel transaksi hanya memiliki kolom id obat sebagai penghubung ke tabel obat
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        // mengambil stok obat lama
        StokObat = getStok(IdObat);
        // mengambil jumlah beli yang sudah tersimpan di database
        int JumlahBeliLama = getJumlahBeli(Integer.parseInt(tm.getValueAt(DataTransaksi.getSelectedRow(), 0).toString()));
        // menghitung stok obat yang baru
        StokObatBaru = StokObat + (JumlahBeliLama - (Integer)InputJumlah.getValue());
        
        // jika jumlah beli baru melebihi stok obat, beri pesan
        if((StokObat - ((Integer)InputJumlah.getValue() - JumlahBeliLama)) < 0){
            JOptionPane.showMessageDialog(null, "Stok tidak mencukupi.\nSilakan kurangi jumlah.\nStok: " + StokObat);
        // jika tidak
        } else {
            try {
                // memperbarui data transaksi
                ps = varConnection.prepareStatement(
                    "UPDATE transaksi SET Id_obat = ?, Jumlah_obat = ?, Total_harga = ? "
                    + "WHERE Id_transaksi = ?");
                ps.setInt(1, IdObat);
                ps.setInt(2, (Integer)InputJumlah.getValue());
                ps.setInt(3, Integer.parseInt(TampilTotal.getText()));
                ps.setInt(4, Integer.parseInt(tm.getValueAt(DataTransaksi.getSelectedRow(), 0).toString()));
                ps.executeUpdate();

                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();
                
                // menampilkan data transaksi dan reset input
                refreshTable();
                ComboNamaObat.resetKeyboardActions();
                TampilTotal.setText("");
                InputJumlah.setValue(0);
            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnPerbaruiActionPerformed

    private void BtnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogoutActionPerformed
        // memindahkan pengguna ke halaman login
        JOptionPane.showMessageDialog(null, "Berhasil logout.");
        dispose();
        HalLogin HalamanLogin = new HalLogin();
        HalamanLogin.show();
    }//GEN-LAST:event_BtnLogoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HalMenuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HalMenuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HalMenuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HalMenuPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HalMenuPelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBeli;
    private javax.swing.JButton BtnHapus;
    private javax.swing.JButton BtnLogout;
    private javax.swing.JButton BtnPerbarui;
    private javax.swing.JComboBox<String> ComboNamaObat;
    private javax.swing.JTable DataTransaksi;
    private javax.swing.JSpinner InputJumlah;
    private javax.swing.JTextField TampilTotal;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
