/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package methods;

/**
 *
 * @author brand
 */
public interface MethodsBlueprints {
    public void InsertComboVal();
    public int konversiNamaObatKeId(String NamaObat);
    public int konversiNamaPelangganKeId(String NamaPelanggan);
    public int hitungTotal(int IdObat, int jumlah);
    public int getStok(int IdObat);
    public int getJumlahBeli(int IdTransaksi);
    public void refreshTableObat();
    public void refreshTableTransaksi();
    public void cariObat(String KataKunci);
    public void cariTransaksi(String KataKunci);
}
