/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package programutama;
import java.awt.Color;
import methods.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author brand
 */
public class HalMenuPegawai extends javax.swing.JFrame implements MethodsBlueprints {

    /**
     * Creates new form HalMenuPegawai
     */
    private Connection varConnection;
    private DefaultTableModel tmObat;
    private DefaultTableModel tmTransaksi;
    DateFormat formatTgl = new SimpleDateFormat("yyyy-MM-dd");
    private PreparedStatement ps;
    private ResultSet rs;
    
    int IdObat, StokObat, StokObatBaru;
    
    public HalMenuPegawai() {
        initComponents();
        InsertComboVal();
        refreshTableObat();
        refreshTableTransaksi();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    // method untuk mengisi combo box nama obat dan pelanggan
    @Override
    public void InsertComboVal() {
        try {
            ResultSet rs;
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement("SELECT Nama_obat from obat");
            rs = ps.executeQuery();
            while(rs.next()) {
                ComboNamaObat.addItem(rs.getString(1));
            }
            ps = varConnection.prepareStatement("SELECT Nama FROM users");
            rs = ps.executeQuery();
            while(rs.next()) {
                ComboNamaPelanggan.addItem(rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Eror, koneksi gagal: " + e);
        }
    }
    
    // method untuk mengonversi nama obat ke id obat
    @Override
    public int konversiNamaObatKeId(String NamaObat){
        int IdObat = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                    "SELECT Id_obat "
                    + "FROM obat "
                    + "WHERE Nama_obat = ?");
            ps.setString(1, NamaObat);
            rs = ps.executeQuery();
            if(rs.next()){
                IdObat = rs.getInt(1);
            } else {
                IdObat = 0;
            }
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return IdObat;
    }
    
    // method untuk mengonversi nama pelanggan ke id pelanggan
    @Override
    public int konversiNamaPelangganKeId(String NamaPelanggan){
        int IdPelanggan = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                    "SELECT Id_user "
                    + "FROM users "
                    + "WHERE Nama = ?");
            ps.setString(1, NamaPelanggan);
            rs = ps.executeQuery();
            if(rs.next()){
                IdPelanggan = rs.getInt(1);
            } else {
                IdPelanggan = 0;
            }
        } catch (Exception e){
            System.out.println("Eror, koneksi gagal: " + e);
        }
        return IdPelanggan;
    }
    
    // method untuk menghitung total bayar berdasarkan obat yang dibeli
    @Override
    public int hitungTotal(int IdObat, int jumlah){
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
    @Override
    public int getStok(int IdObat){
        int StokObat = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
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
    @Override
    public int getJumlahBeli(int IdTransaksi){
        int JumlahBeli = 0;
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
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
    
    // method untuk menampilkan data obat
    @Override
    public void refreshTableObat(){
        String[] Kolom = {"ID", "Nama Obat", "Jenis", "Harga", "Stok", "Tanggal Kedaluwarsa"};
        tmObat = new DefaultTableModel(null, Kolom);
        DataObat.setModel(tmObat);
        tmObat.getDataVector().removeAllElements();
        
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement("SELECT * FROM obat");
            rs = ps.executeQuery();
            while(rs.next()) {
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                };
                tmObat.addRow(data);
            }
        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }
    
    // method untuk menampilkan semua data transaksi
    @Override
    public void refreshTableTransaksi(){
        String[] Kolom = {"ID", "Tanggal Transaksi", "Nama Pelanggan", "Nama Obat", "Jumlah", "Total"};
        tmTransaksi = new DefaultTableModel(null, Kolom);
        DataTransaksi.setModel(tmTransaksi);
        tmTransaksi.getDataVector().removeAllElements();
        
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                    "SELECT transaksi.Id_transaksi, transaksi.Tanggal_transaksi, "
                    + "users.Nama, obat.Nama_obat, transaksi.Jumlah_obat, "
                    + "transaksi.Total_harga "
                    + "FROM transaksi "
                    + "JOIN users "
                    + "ON transaksi.Id_user = users.Id_user "
                    + "JOIN obat "
                    + "ON transaksi.Id_obat = obat.Id_obat");
            rs = ps.executeQuery();
            while(rs.next()) {
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                };
                tmTransaksi.addRow(data);
            }
        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }
    
    // method untuk menampilkan data obat berdasarkan input pengguna
    @Override
    public void cariObat (String KataKunci) {
        String[] Kolom = {"ID", "Nama Obat", "Jenis", "Harga", "Stok", "Tanggal Kedaluwarsa"};
        tmObat = new DefaultTableModel(null, Kolom);
        DataObat.setModel(tmObat);
        tmObat.getDataVector().removeAllElements();
        
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                "SELECT * FROM obat "
                + "WHERE CONCAT ("
                + "Id_obat, Nama_obat, Jenis_obat, Harga_obat, Stok_obat, Tanggal_kedaluwarsa"
                + ") LIKE '%" + KataKunci + "%'");
            rs = ps.executeQuery();
            while(rs.next()) {
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                };
                tmObat.addRow(data);
            }
        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }
    
    // method untuk menampilkan data transaksi berdasarkan input pengguna
    @Override
    public void cariTransaksi (String KataKunci) {
        String[] Kolom = {"ID", "Tanggal Transaksi", "Nama Pelanggan", "Nama Obat", "Jumlah", "Total"};
        tmTransaksi = new DefaultTableModel(null, Kolom);
        DataTransaksi.setModel(tmTransaksi);
        tmTransaksi.getDataVector().removeAllElements();
        
        try {
            varConnection = ConnectToDB.tryConnect(); // connecting with database
            ps = varConnection.prepareStatement(
                "SELECT transaksi.Id_transaksi, transaksi.Tanggal_transaksi, "
                + "users.Nama, obat.Nama_obat, transaksi.Jumlah_obat, "
                + "transaksi.Total_harga "
                + "FROM transaksi "
                + "JOIN users "
                + "ON transaksi.Id_user = users.Id_user "
                + "JOIN obat "
                + "ON transaksi.Id_obat = obat.Id_obat "
                + "WHERE CONCAT ("
                + "transaksi.Id_transaksi, transaksi.Tanggal_transaksi, "
                + "users.Nama, obat.Nama_obat, transaksi.Jumlah_obat, "
                + "transaksi.Total_harga"
                + ") LIKE '%" + KataKunci + "%'");
            rs = ps.executeQuery();
            while(rs.next()) {
                Object[] data = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                };
                tmTransaksi.addRow(data);
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        InputTanggalExp = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        ComboJenisObat = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        InputHargaObat = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        InputStokObat = new javax.swing.JSpinner();
        BtnTambahObat = new javax.swing.JButton();
        BtnPerbaruiObat = new javax.swing.JButton();
        BtnHapusObat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        DataObat = new javax.swing.JTable();
        InputNamaObat = new javax.swing.JTextField();
        InputCariObat = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        InputJumlah = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        ComboNamaObat = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ComboNamaPelanggan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        DataTransaksi = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        InputTanggalTransaksi = new com.toedter.calendar.JDateChooser();
        TampilTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        BtnTambahTransaksi = new javax.swing.JButton();
        BtnHapusTransaksi = new javax.swing.JButton();
        BtnPerbaruiTransaksi = new javax.swing.JButton();
        InputCariTransaksi = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        BtnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(1, 38, 117));

        jPanel1.setBackground(new java.awt.Color(1, 38, 117));

        jPanel2.setBackground(new java.awt.Color(80, 188, 255));

        jLabel1.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel1.setText("Nama");

        jLabel2.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel2.setText("Tanggal Kedaluwarsa");

        jLabel3.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel3.setText("Jenis");

        ComboJenisObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        ComboJenisObat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "tablet", "bungkus", "pil", "kapsul", "larutan" }));

        jLabel4.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel4.setText("Harga (Rp)");

        jLabel5.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel5.setText("Stok");

        BtnTambahObat.setBackground(new java.awt.Color(114, 191, 120));
        BtnTambahObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnTambahObat.setText("Tambah");
        BtnTambahObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahObatActionPerformed(evt);
            }
        });

        BtnPerbaruiObat.setBackground(new java.awt.Color(252, 205, 42));
        BtnPerbaruiObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnPerbaruiObat.setText("Perbarui");
        BtnPerbaruiObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerbaruiObatActionPerformed(evt);
            }
        });

        BtnHapusObat.setBackground(new java.awt.Color(252, 0, 0));
        BtnHapusObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnHapusObat.setForeground(new java.awt.Color(255, 255, 255));
        BtnHapusObat.setText("Hapus");
        BtnHapusObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusObatActionPerformed(evt);
            }
        });

        DataObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        DataObat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Jenis", "Harga", "Stok", "Tanggal Kedaluwarsa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        DataObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DataObatMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(DataObat);

        InputNamaObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N

        InputCariObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        InputCariObat.setForeground(new java.awt.Color(170, 170, 170));
        InputCariObat.setText("Cari obat...");
        InputCariObat.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputCariObatFocusGained(evt);
            }
        });
        InputCariObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                InputCariObatKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(InputCariObat)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(InputHargaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboJenisObat, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(InputNamaObat))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(InputTanggalExp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(InputStokObat)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnTambahObat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnPerbaruiObat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnHapusObat)))))
                .addGap(24, 24, 24))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InputNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ComboJenisObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(InputStokObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(InputTanggalExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(InputHargaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BtnTambahObat)
                        .addComponent(BtnPerbaruiObat)
                        .addComponent(BtnHapusObat)))
                .addGap(18, 18, 18)
                .addComponent(InputCariObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(80, 188, 255));
        jPanel4.setForeground(new java.awt.Color(80, 188, 255));

        jLabel11.setFont(new java.awt.Font("STXihei", 3, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("MANAJEMEN OBAT");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel11))
        );

        jPanel5.setBackground(new java.awt.Color(80, 188, 255));

        InputJumlah.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        InputJumlah.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                InputJumlahStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel8.setText("Jumlah");

        ComboNamaObat.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        ComboNamaObat.setToolTipText("Pilih");
        ComboNamaObat.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboNamaObatItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel9.setText("Nama Obat");

        jLabel10.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel10.setText("Nama Pelanggan");

        ComboNamaPelanggan.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        ComboNamaPelanggan.setToolTipText("Pilih");

        DataTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        DataTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tanggal Transaksi", "Nama Pelanggan", "Nama Obat", "Jumlah", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        DataTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DataTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(DataTransaksi);

        jLabel6.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel6.setText("Tanggal Transaksi");

        TampilTotal.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        TampilTotal.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("STXihei", 1, 12)); // NOI18N
        jLabel7.setText("Total");

        BtnTambahTransaksi.setBackground(new java.awt.Color(114, 191, 120));
        BtnTambahTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnTambahTransaksi.setText("Tambah");
        BtnTambahTransaksi.setToolTipText("");
        BtnTambahTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTransaksiActionPerformed(evt);
            }
        });

        BtnHapusTransaksi.setBackground(new java.awt.Color(252, 0, 0));
        BtnHapusTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnHapusTransaksi.setForeground(new java.awt.Color(255, 255, 255));
        BtnHapusTransaksi.setText("Hapus");
        BtnHapusTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusTransaksiActionPerformed(evt);
            }
        });

        BtnPerbaruiTransaksi.setBackground(new java.awt.Color(252, 205, 42));
        BtnPerbaruiTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnPerbaruiTransaksi.setText("Perbarui");
        BtnPerbaruiTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerbaruiTransaksiActionPerformed(evt);
            }
        });

        InputCariTransaksi.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        InputCariTransaksi.setForeground(new java.awt.Color(170, 170, 170));
        InputCariTransaksi.setText("Cari transaksi...");
        InputCariTransaksi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputCariTransaksiFocusGained(evt);
            }
        });
        InputCariTransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                InputCariTransaksiKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addGap(60, 60, 60)
                                .addComponent(TampilTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(45, 45, 45)
                                .addComponent(InputJumlah))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(ComboNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BtnHapusTransaksi)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(InputTanggalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ComboNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(BtnTambahTransaksi)
                                        .addGap(41, 41, 41)
                                        .addComponent(BtnPerbaruiTransaksi)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(InputCariTransaksi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ComboNamaObat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ComboNamaPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(InputJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(InputTanggalTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TampilTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(BtnHapusTransaksi)
                    .addComponent(BtnPerbaruiTransaksi)
                    .addComponent(BtnTambahTransaksi))
                .addGap(24, 24, 24)
                .addComponent(InputCariTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(80, 188, 255));
        jPanel6.setForeground(new java.awt.Color(80, 188, 255));

        jLabel12.setFont(new java.awt.Font("STXihei", 3, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("MANAJEMEN TRANSAKSI");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12))
        );

        BtnLogout.setBackground(new java.awt.Color(252, 0, 0));
        BtnLogout.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnLogout.setForeground(new java.awt.Color(255, 255, 255));
        BtnLogout.setText("Logout");
        BtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnLogout))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1242, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 656, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnTambahObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahObatActionPerformed
        try {
            // memasukkan data yang diinputkan pengguna ke database
            ps = varConnection.prepareStatement("INSERT INTO obat VALUES (null, ?, ?, ?, ?, ?)");
            ps.setString(1, InputNamaObat.getText());
            ps.setString(2, ComboJenisObat.getSelectedItem().toString());
            ps.setInt(3, (Integer)InputHargaObat.getValue());
            ps.setInt(4, (Integer)InputStokObat.getValue());
            ps.setString(5, formatTgl.format(InputTanggalExp.getDate()));
            ps.executeUpdate();

            // menampilkan data transaksi dan obat dan reset input
            refreshTableObat();
            InputNamaObat.setText("");
            ComboJenisObat.resetKeyboardActions();
            InputHargaObat.setValue(0);
            InputStokObat.setValue(0);

        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }//GEN-LAST:event_BtnTambahObatActionPerformed

    private void BtnPerbaruiObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerbaruiObatActionPerformed
        try {
            // memperbarui data obat
            ps = varConnection.prepareStatement(
                    "UPDATE obat SET Nama_obat = ?, Jenis_obat = ?, Harga_obat = ?, "
                    + "Stok_obat = ?, Tanggal_kedaluwarsa = ? "
                    + "WHERE Id_obat = ?");
            ps.setString(1, InputNamaObat.getText());
            ps.setString(2, ComboJenisObat.getSelectedItem().toString());
            ps.setInt(3, (Integer)InputHargaObat.getValue());
            ps.setInt(4, (Integer)InputStokObat.getValue());
            ps.setString(5, formatTgl.format(InputTanggalExp.getDate()));
            ps.setInt(6, Integer.parseInt(tmObat.getValueAt(DataObat.getSelectedRow(), 0).toString()));
            ps.executeUpdate();

            // menampilkan data transaksi dan obat dan reset input
            refreshTableTransaksi();
            refreshTableObat();
            InputNamaObat.setText("");
            ComboJenisObat.resetKeyboardActions();
            InputHargaObat.setValue(0);
            InputStokObat.setValue(0);

        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }//GEN-LAST:event_BtnPerbaruiObatActionPerformed

    private void BtnHapusObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusObatActionPerformed
        // meminta konfirmasi dari pengguna
        // jika yakin menghapus
        if (
            JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Menghapus Data Ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.YES_OPTION) {
            try {
                // menghapus data yang dipilih
                ps = varConnection.prepareStatement("DELETE FROM obat WHERE Id_obat = ?");
                ps.setInt(1, Integer.parseInt(tmObat.getValueAt(DataObat.getSelectedRow(),0).toString()));
                ps.executeUpdate();

            // menampilkan data transaksi dan obat dan reset input
            refreshTableTransaksi();
            refreshTableObat();
            InputNamaObat.setText("");
            ComboJenisObat.resetKeyboardActions();
            InputHargaObat.setValue(0);
            InputStokObat.setValue(0);
            
            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnHapusObatActionPerformed

    private void BtnPerbaruiTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerbaruiTransaksiActionPerformed
        // mengubah nama obat menjadi id karena tabel transaksi hanya memiliki kolom id obat sebagai penghubung ke tabel obat
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        // mengubah nama pelanggan menjadi id karena tabel transaksi hanya memiliki kolom id user sebagai penghubung ke tabel users
        int IdPelanggan = konversiNamaPelangganKeId(ComboNamaPelanggan.getSelectedItem().toString());
        // mengambil stok obat lama
        StokObat = getStok(IdObat); 
        // mengambil jumlah beli yang sudah tersimpan di database
        int JumlahBeliLama = getJumlahBeli(Integer.parseInt(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 0).toString()));
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
                        "UPDATE transaksi SET Tanggal_transaksi = ?, Id_user = ?, "
                        + "Id_obat = ?, Jumlah_obat = ?, Total_harga = ? "
                        + "WHERE Id_transaksi = ?");
                ps.setString(1, formatTgl.format(InputTanggalTransaksi.getDate()));
                ps.setInt(2, IdPelanggan);
                ps.setInt(3, IdObat);
                ps.setInt(4, (Integer)InputJumlah.getValue());
                ps.setInt(5, Integer.parseInt(TampilTotal.getText()));
                ps.setInt(6, Integer.parseInt(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 0).toString()));
                ps.executeUpdate();

                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();
                
                // menampilkan data transaksi dan obat dan reset input
                refreshTableTransaksi();
                refreshTableObat();
                ComboNamaObat.resetKeyboardActions();
                ComboNamaPelanggan.resetKeyboardActions();
                InputJumlah.setValue(0);
                TampilTotal.setText("");
                
            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnPerbaruiTransaksiActionPerformed

    private void BtnHapusTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusTransaksiActionPerformed
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
                ps.setInt(1, Integer.parseInt(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(),0).toString()));
                ps.executeUpdate();

                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();
                
                // menampilkan data transaksi dan obat dan reset input
                refreshTableTransaksi();
                refreshTableObat();
                ComboNamaObat.resetKeyboardActions();
                ComboNamaPelanggan.resetKeyboardActions();
                InputJumlah.setValue(0);
                TampilTotal.setText("");
                
            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnHapusTransaksiActionPerformed

    private void BtnTambahTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTransaksiActionPerformed
        // mengubah nama obat menjadi id karena tabel transaksi hanya memiliki kolom id obat sebagai penghubung ke tabel obat
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        // mengubah nama pelanggan menjadi id karena tabel transaksi hanya memiliki kolom id user sebagai penghubung ke tabel users
        int IdPelanggan = konversiNamaPelangganKeId(ComboNamaPelanggan.getSelectedItem().toString());
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
                ps.setString(1, formatTgl.format(InputTanggalTransaksi.getDate()));
                ps.setInt(2, IdPelanggan);
                ps.setInt(3, IdObat);
                ps.setInt(4, (Integer)InputJumlah.getValue());
                ps.setInt(5, Integer.parseInt(TampilTotal.getText()));
                ps.executeUpdate();

                // memperbarui stok obat
                ps = varConnection.prepareStatement("UPDATE obat SET Stok_obat = ? WHERE Id_obat = ?");
                ps.setInt(1, StokObatBaru);
                ps.setInt(2, IdObat);
                ps.executeUpdate();

                // menampilkan data transaksi dan obat dan reset input
                refreshTableTransaksi();
                refreshTableObat();
                ComboNamaObat.resetKeyboardActions();
                ComboNamaPelanggan.resetKeyboardActions();
                InputJumlah.setValue(0);
                TampilTotal.setText("");

            } catch(Exception e) {
                System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
            }
        }
    }//GEN-LAST:event_BtnTambahTransaksiActionPerformed

    private void InputJumlahStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_InputJumlahStateChanged
        // mengubah total bayar jika jumlah diubah
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        TampilTotal.setText(String.valueOf(hitungTotal(IdObat, (Integer)InputJumlah.getValue())));
    }//GEN-LAST:event_InputJumlahStateChanged

    private void DataObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataObatMouseClicked
        // mengisi input dengan data dari tabel yang dipilih pengguna 
        InputNamaObat.setText(tmObat.getValueAt(DataObat.getSelectedRow(), 1).toString());
        ComboJenisObat.setSelectedItem(tmObat.getValueAt(DataObat.getSelectedRow(), 2));
        InputHargaObat.setValue(Integer.valueOf(tmObat.getValueAt(DataObat.getSelectedRow(), 3).toString()));
        InputStokObat.setValue(Integer.valueOf(tmObat.getValueAt(DataObat.getSelectedRow(), 4).toString()));
        
        try {
            Date tglSewa = new SimpleDateFormat("yyyy-MM-dd").parse(
                    tmObat.getValueAt(DataObat.getSelectedRow(), 5).toString());
            InputTanggalExp.setDate(tglSewa);
            
        } catch (ParseException ex) {
            Logger.getLogger(HalMenuPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DataObatMouseClicked

    private void DataTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DataTransaksiMouseClicked
        // mengisi input dengan data dari tabel yang dipilih pengguna 
        try {
            Date tglSewa = new SimpleDateFormat("yyyy-MM-dd").parse(
                    tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 1).toString());
            InputTanggalTransaksi.setDate(tglSewa);
            
        } catch (ParseException ex) {
            Logger.getLogger(HalMenuPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ComboNamaPelanggan.setSelectedItem(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 2));
        ComboNamaObat.setSelectedItem(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 3));
        InputJumlah.setValue(Integer.valueOf(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 4).toString()));
        TampilTotal.setText(tmTransaksi.getValueAt(DataTransaksi.getSelectedRow(), 5).toString());
    }//GEN-LAST:event_DataTransaksiMouseClicked

    private void ComboNamaObatItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboNamaObatItemStateChanged
        // mengubah total bayar jika nama obat diubah
        IdObat = konversiNamaObatKeId(ComboNamaObat.getSelectedItem().toString());
        TampilTotal.setText(String.valueOf(hitungTotal(IdObat, (Integer)InputJumlah.getValue())));
    }//GEN-LAST:event_ComboNamaObatItemStateChanged

    private void BtnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogoutActionPerformed
        // memindahkan pengguna ke halaman login
        JOptionPane.showMessageDialog(null, "Berhasil logout.");
        dispose();
        HalLogin HalamanLogin = new HalLogin();
        HalamanLogin.show();
    }//GEN-LAST:event_BtnLogoutActionPerformed

    private void InputCariObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCariObatKeyReleased
       // memanggil method untuk menampilkan data obat berdasarkan input pengguna
       cariObat(InputCariObat.getText());
    }//GEN-LAST:event_InputCariObatKeyReleased

    private void InputCariTransaksiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_InputCariTransaksiKeyReleased
       // memanggil method untuk menampilkan data transaksi berdasarkan input pengguna
       cariTransaksi(InputCariTransaksi.getText());
    }//GEN-LAST:event_InputCariTransaksiKeyReleased

    private void InputCariTransaksiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputCariTransaksiFocusGained
        // mengganti warna ketika fokus didapatkan
        if("Cari transaksi...".equals(InputCariTransaksi.getText())){
            InputCariTransaksi.setText("");
            InputCariTransaksi.setBackground(Color.WHITE);
            InputCariTransaksi.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_InputCariTransaksiFocusGained

    private void InputCariObatFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputCariObatFocusGained
        // mengganti warna ketika fokus didapatkan
        if("Cari obat...".equals(InputCariObat.getText())){
            InputCariObat.setText("");
            InputCariObat.setBackground(Color.WHITE);
            InputCariObat.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_InputCariObatFocusGained

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
            java.util.logging.Logger.getLogger(HalMenuPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HalMenuPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HalMenuPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HalMenuPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HalMenuPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnHapusObat;
    private javax.swing.JButton BtnHapusTransaksi;
    private javax.swing.JButton BtnLogout;
    private javax.swing.JButton BtnPerbaruiObat;
    private javax.swing.JButton BtnPerbaruiTransaksi;
    private javax.swing.JButton BtnTambahObat;
    private javax.swing.JButton BtnTambahTransaksi;
    private javax.swing.JComboBox<String> ComboJenisObat;
    private javax.swing.JComboBox<String> ComboNamaObat;
    private javax.swing.JComboBox<String> ComboNamaPelanggan;
    private javax.swing.JTable DataObat;
    private javax.swing.JTable DataTransaksi;
    private javax.swing.JTextField InputCariObat;
    private javax.swing.JTextField InputCariTransaksi;
    private javax.swing.JSpinner InputHargaObat;
    private javax.swing.JSpinner InputJumlah;
    private javax.swing.JTextField InputNamaObat;
    private javax.swing.JSpinner InputStokObat;
    private com.toedter.calendar.JDateChooser InputTanggalExp;
    private com.toedter.calendar.JDateChooser InputTanggalTransaksi;
    private javax.swing.JTextField TampilTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
