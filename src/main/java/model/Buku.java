/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */

public class Buku {
    private String kode;
    private String judul;
    private int kategori;           // indeks ke array 1D kategori
    private int lantai, rak, posisi; // indeks ke array 3D lokasi
    private boolean tersedia = true;
    private String peminjam;

    public Buku(String kode, String judul, int kategori) {
        this.kode = kode;
        this.judul = judul;
        this.kategori = kategori;
    }

    public String getKode() { return kode; }
    public String getJudul() { return judul; }
    public int getKategori() { return kategori; }
    public int getLantai() { return lantai; }
    public int getRak() { return rak; }
    public int getPosisi() { return posisi; }
    public boolean isTersedia() { return tersedia; }
    public String getPeminjam() { return peminjam; }

    public void setLokasi(int lantai, int rak, int posisi) {
        this.lantai = lantai;
        this.rak = rak;
        this.posisi = posisi;
    }

    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
    public void setPeminjam(String peminjam) { this.peminjam = peminjam; }
}
