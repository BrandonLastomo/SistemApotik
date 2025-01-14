/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package programutama;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import methods.ConnectToDB;

/**
 *
 * @author brand
 */
public class HalRegister extends javax.swing.JFrame {

    /**
     * Creates new form RegisterPage
     */
    private Connection varConnection;
    public HalRegister() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    // method untuk mengganti warna ketika fokus didapatkan
    private void gantiWarnaInput(JTextField Input){
        Input.setText("");
        Input.setBackground(Color.WHITE);
        Input.setForeground(Color.BLACK);
    }
    
    // method untuk mengganti warna ketika fokus didapatkan
    private void gantiWarnaInput(String Placeholder, JTextField Input){
        if(Placeholder.equals(Input.getText())){
            Input.setText("");
            Input.setBackground(Color.WHITE);
            Input.setForeground(Color.BLACK);
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
        jLabel1 = new javax.swing.JLabel();
        InputNama = new javax.swing.JTextField();
        InputUsername = new javax.swing.JTextField();
        InputPass = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        BtnPindahLogin = new javax.swing.JLabel();
        BtnRegister = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(80, 188, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(227, 227, 227)));

        jLabel1.setFont(new java.awt.Font("STXihei", 1, 24)); // NOI18N
        jLabel1.setText("Halo!");

        InputNama.setBackground(new java.awt.Color(250, 250, 250));
        InputNama.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        InputNama.setForeground(new java.awt.Color(170, 170, 170));
        InputNama.setText("Nama");
        InputNama.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputNamaFocusGained(evt);
            }
        });

        InputUsername.setBackground(new java.awt.Color(250, 250, 250));
        InputUsername.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        InputUsername.setForeground(new java.awt.Color(170, 170, 170));
        InputUsername.setText("Username");
        InputUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputUsernameFocusGained(evt);
            }
        });

        InputPass.setBackground(new java.awt.Color(250, 250, 250));
        InputPass.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        InputPass.setForeground(new java.awt.Color(170, 170, 170));
        InputPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                InputPassFocusGained(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        jLabel5.setText("Sudah punya akun?");

        BtnPindahLogin.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnPindahLogin.setText("Masuk");
        BtnPindahLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnPindahLoginMouseClicked(evt);
            }
        });

        BtnRegister.setBackground(new java.awt.Color(0, 149, 246));
        BtnRegister.setFont(new java.awt.Font("STXihei", 0, 12)); // NOI18N
        BtnRegister.setForeground(new java.awt.Color(255, 255, 255));
        BtnRegister.setText("Daftar");
        BtnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(InputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InputUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InputPass, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(BtnRegister)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnPindahLogin)
                        .addGap(49, 49, 49))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(InputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(InputPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnRegister)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(BtnPindahLogin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(1, 38, 117));
        jPanel2.setForeground(new java.awt.Color(1, 38, 117));

        jLabel7.setFont(new java.awt.Font("STXihei", 3, 120)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("APO");

        jLabel8.setFont(new java.awt.Font("STXihei", 3, 120)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("TEK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPindahLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnPindahLoginMouseClicked
        // memindahkan pengguna ke halaman register
        dispose();
        HalMenuPelanggan MainMenu = new HalMenuPelanggan();
        MainMenu.show();
    }//GEN-LAST:event_BtnPindahLoginMouseClicked

    private void BtnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegisterActionPerformed
        try {
            varConnection = ConnectToDB.tryConnect(); // melakukan koneksi dengan database
            // memasukkan data yang diinputkan pengguna ke database
            PreparedStatement ps = varConnection.prepareStatement("INSERT INTO users VALUES (null, ?, ?, ?, ?, null, null, null)");
            ps.setString(1, InputNama.getText());
            ps.setString(2, InputUsername.getText());
            ps.setString(3, InputPass.getText());
            ps.setString(4, "pelanggan");
            ps.executeUpdate();
            
            // menampilkan pesan bahwa data berhasil dimasukkan dan 
            // langsung memindahkan user ke halaman login
            JOptionPane.showMessageDialog(null, "Berhasil daftar!\nSilakan masuk, yaa");
            dispose();
            HalLogin HalamanLogin = new HalLogin();
            HalamanLogin.show();
        } catch(Exception e) {
            System.out.print("Eror, koneksi gagal:\n" + e + "\n\n");
        }
    }//GEN-LAST:event_BtnRegisterActionPerformed

    private void InputNamaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputNamaFocusGained
        // memanggil method untuk mengganti warna
        gantiWarnaInput("Nama", InputNama);
    }//GEN-LAST:event_InputNamaFocusGained

    private void InputUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputUsernameFocusGained
        // memanggil method untuk mengganti warna
        gantiWarnaInput("Username", InputUsername);
    }//GEN-LAST:event_InputUsernameFocusGained

    private void InputPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_InputPassFocusGained
        // memanggil method untuk mengganti warna
        gantiWarnaInput(InputPass);
    }//GEN-LAST:event_InputPassFocusGained

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
            java.util.logging.Logger.getLogger(HalRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HalRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HalRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HalRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HalRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BtnPindahLogin;
    private javax.swing.JButton BtnRegister;
    private javax.swing.JTextField InputNama;
    private javax.swing.JPasswordField InputPass;
    private javax.swing.JTextField InputUsername;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
