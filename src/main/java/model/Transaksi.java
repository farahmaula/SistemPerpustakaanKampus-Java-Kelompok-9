/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Model data satu transaksi (PINJAM atau KEMBALI). Objek ini disimpan di dalam Stack.
 */
public class Transaksi {
    private String jenis;
    private Buku buku;
    private String mahasiswa;
    private int hari;          // indeks hari (0-6)
    private String namaHari;

    public Transaksi(String jenis, Buku buku, String mahasiswa, int hari, String namaHari) {
        this.jenis = jenis;
        this.buku = buku;
        this.mahasiswa = mahasiswa;
        this.hari = hari;
        this.namaHari = namaHari;
    }

    public String getJenis() { return jenis; }
    public Buku getBuku() { return buku; }
    public String getMahasiswa() { return mahasiswa; }
    public int getHari() { return hari; }

    @Override
    public String toString() {
        return jenis + " | " + buku.getKode() + " - " + buku.getJudul()
                + " | " + mahasiswa + " | " + namaHari;
    }
}