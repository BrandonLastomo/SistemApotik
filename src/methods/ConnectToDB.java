/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package methods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author brand
 */
public class ConnectToDB {
    private static Connection connect; // variabel untuk menyimpan info tentang koneksi dengan database
    
    // method untuk melakukan koneksi dengan database
    public static Connection tryConnect() throws SQLException, ClassNotFoundException{
        try {
            if (connect == null){
                Class.forName("com.mysql.cj.jdbc.Driver"); // memuat driver untuk melakukan koneksi dengan database
                connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/apotek", "root", ""); // menyimpan info tentang koneksi dengan database
            }
            return connect; // mengembalikan info
        } catch (SQLException e){
            System.err.println("Eror, koneksi gagal: " + e.getMessage());
            throw e;
        }
    }
}