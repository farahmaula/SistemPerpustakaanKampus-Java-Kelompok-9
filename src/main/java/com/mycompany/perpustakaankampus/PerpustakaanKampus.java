/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.perpustakaankampus;

/**
 *
 * @author Asus
 */
import model.Buku;
import model.Transaksi;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * Sistem Manajemen Perpustakaan Kampus
 * Mengintegrasikan: Array 1D, 2D, 3D, ArrayList, Stack, dan Queue.
 */
public class PerpustakaanKampus {

    // 1. Array 1D: daftar kategori buku
    static final String[] KATEGORI = {"Pemrograman", "Matematika", "Jaringan Komputer", "Basis Data"};
    static final String[] HARI = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};

    // 2. Array 2D: jumlah peminjaman [kategori][hari]
    static int[][] peminjaman = new int[KATEGORI.length][HARI.length];

    // 3. Array 3D: lokasi buku [lantai][rak][posisi] berisi kode buku (null = kosong)
    static final int LANTAI = 2, RAK = 3, POSISI = 4;
    static String[][][] lokasi = new String[LANTAI][RAK][POSISI];

    // 4. ArrayList: daftar buku dinamis
    static ArrayList<Buku> daftarBuku = new ArrayList<>();

    // 5. Stack (LIFO): riwayat transaksi
    static Stack<Transaksi> riwayat = new Stack<>();

    // 6. Queue (FIFO): antrean mahasiswa peminjam
    static Queue<String> antrean = new LinkedList<>();

    static Scanner sc = new Scanner(System.in);

    // ===== Helper input =====
    static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka!");
            }
        }
    }

    static String bacaString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static Buku cariBuku(String kode) {
        for (Buku b : daftarBuku) {
            if (b.getKode().equalsIgnoreCase(kode)) return b;
        }
        return null;
    }

    // ===== Menu utama =====
    public static void main(String[] args) {
        // Data awal
        tambahBukuInternal("B001", "Dasar Pemrograman Java", 0);
        tambahBukuInternal("B002", "Kalkulus Dasar", 1);
        tambahBukuInternal("B003", "Jaringan Komputer", 2);
        tambahBukuInternal("B004", "Sistem Basis Data", 3);

        int pilih;
        do {
            System.out.println("\n===== PERPUSTAKAAN KAMPUS =====");
            System.out.println("1. Kategori buku (Array 1D)");
            System.out.println("2. Statistik peminjaman (Array 2D)");
            System.out.println("3. Denah lokasi buku (Array 3D)");
            System.out.println("4. Kelola buku (ArrayList)");
            System.out.println("5. Antrean peminjam (Queue)");
            System.out.println("6. Kembalikan buku");
            System.out.println("7. Riwayat transaksi (Stack)");
            System.out.println("0. Keluar");
            pilih = bacaInt("Pilih menu: ");

            switch (pilih) {
                case 1: menuKategori(); break;
                case 2: tampilStatistik(); break;
                case 3: tampilLokasi(); break;
                case 4: menuBuku(); break;
                case 5: menuAntrean(); break;
                case 6: kembalikanBuku(); break;
                case 7: menuRiwayat(); break;
                case 0: System.out.println("Terima kasih!"); break;
                default: System.out.println("Menu tidak valid.");
            }
        } while (pilih != 0);
    }

    // ===== 1. Array 1D =====
    static void menuKategori() {
        System.out.println("\n-- Daftar Kategori --");
        for (int i = 0; i < KATEGORI.length; i++) {
            System.out.println((i + 1) + ". " + KATEGORI[i]);
        }
    }

    // ===== 2. Array 2D =====
    static void tampilStatistik() {
        System.out.println("\n-- Jumlah Peminjaman per Kategori per Hari --");
        System.out.printf("%-20s", "Kategori");
        for (String h : HARI) System.out.printf("%-8s", h);
        System.out.printf("%-6s%n", "Total");

        for (int i = 0; i < KATEGORI.length; i++) {
            System.out.printf("%-20s", KATEGORI[i]);
            int total = 0;
            for (int j = 0; j < HARI.length; j++) {
                System.out.printf("%-8d", peminjaman[i][j]);
                total += peminjaman[i][j];
            }
            System.out.printf("%-6d%n", total);
        }
    }

    // ===== 3. Array 3D =====
    static void tampilLokasi() {
        System.out.println("\n-- Denah Lokasi Buku (Lantai > Rak > Posisi) --");
        for (int l = 0; l < LANTAI; l++) {
            System.out.println("Lantai " + (l + 1));
            for (int r = 0; r < RAK; r++) {
                System.out.print("  Rak " + (r + 1) + ": ");
                for (int p = 0; p < POSISI; p++) {
                    String isi = (lokasi[l][r][p] == null) ? "-----" : lokasi[l][r][p];
                    System.out.print("[" + isi + "] ");
                }
                System.out.println();
            }
        }
    }

    // Mencari slot kosong pertama di array 3D, lalu menempatkan buku
    static boolean tempatkanBuku(Buku b) {
        for (int l = 0; l < LANTAI; l++) {
            for (int r = 0; r < RAK; r++) {
                for (int p = 0; p < POSISI; p++) {
                    if (lokasi[l][r][p] == null) {
                        lokasi[l][r][p] = b.getKode();
                        b.setLokasi(l, r, p);
                        return true;
                    }
                }
            }
        }
        return false; // rak penuh
    }

    // ===== 4. ArrayList =====
    static boolean tambahBukuInternal(String kode, String judul, int kategori) {
        Buku b = new Buku(kode, judul, kategori);
        if (!tempatkanBuku(b)) return false;
        daftarBuku.add(b);
        return true;
    }

    static void menuBuku() {
        System.out.println("\n-- Kelola Buku --");
        System.out.println("1. Tambah buku");
        System.out.println("2. Tampilkan semua buku");
        System.out.println("3. Hapus buku");
        int pilih = bacaInt("Pilih: ");

        switch (pilih) {
            case 1: tambahBuku(); break;
            case 2: tampilBuku(); break;
            case 3: hapusBuku(); break;
            default: System.out.println("Pilihan tidak valid.");
        }
    }

    static void tambahBuku() {
        String kode = bacaString("Kode buku: ");
        if (cariBuku(kode) != null) {
            System.out.println("Kode sudah digunakan!");
            return;
        }
        String judul = bacaString("Judul buku: ");
        menuKategori();
        int kat = bacaInt("Pilih kategori (nomor): ") - 1;
        if (kat < 0 || kat >= KATEGORI.length) {
            System.out.println("Kategori tidak valid.");
            return;
        }
        if (tambahBukuInternal(kode, judul, kat)) {
            Buku b = daftarBuku.get(daftarBuku.size() - 1);
            System.out.println("Buku ditambahkan di Lantai " + (b.getLantai() + 1)
                    + ", Rak " + (b.getRak() + 1) + ", Posisi " + (b.getPosisi() + 1));
        } else {
            System.out.println("Gagal: seluruh rak penuh.");
        }
    }

    static void tampilBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Belum ada buku.");
            return;
        }
        System.out.printf("%-6s %-26s %-18s %-12s %-10s%n", "Kode", "Judul", "Kategori", "Lokasi", "Status");
        for (Buku b : daftarBuku) {
            String lok = "L" + (b.getLantai() + 1) + "-R" + (b.getRak() + 1) + "-P" + (b.getPosisi() + 1);
            String status = b.isTersedia() ? "Tersedia" : "Dipinjam";
            System.out.printf("%-6s %-26s %-18s %-12s %-10s%n",
                    b.getKode(), b.getJudul(), KATEGORI[b.getKategori()], lok, status);
        }
    }

    static void hapusBuku() {
        Buku b = cariBuku(bacaString("Kode buku yang dihapus: "));
        if (b == null) {
            System.out.println("Buku tidak ditemukan.");
        } else if (!b.isTersedia()) {
            System.out.println("Buku sedang dipinjam, tidak dapat dihapus.");
        } else {
            lokasi[b.getLantai()][b.getRak()][b.getPosisi()] = null; // kosongkan slot di array 3D
            daftarBuku.remove(b);
            System.out.println("Buku berhasil dihapus.");
        }
    }

    // ===== 6. Queue =====
    static void menuAntrean() {
        System.out.println("\n-- Antrean Peminjam --");
        System.out.println("1. Tambah mahasiswa ke antrean");
        System.out.println("2. Lihat antrean");
        System.out.println("3. Layani mahasiswa terdepan (pinjam buku)");
        int pilih = bacaInt("Pilih: ");

        switch (pilih) {
            case 1:
                String nama = bacaString("Nama mahasiswa: ");
                antrean.offer(nama);
                System.out.println(nama + " masuk antrean. Posisi: " + antrean.size());
                break;
            case 2:
                if (antrean.isEmpty()) {
                    System.out.println("Antrean kosong.");
                } else {
                    int no = 1;
                    for (String m : antrean) System.out.println(no++ + ". " + m);
                }
                break;
            case 3:
                layaniAntrean();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    static void layaniAntrean() {
        if (antrean.isEmpty()) {
            System.out.println("Antrean kosong.");
            return;
        }
        String mhs = antrean.peek(); // lihat terdepan tanpa menghapus
        System.out.println("Melayani: " + mhs);
        tampilBuku();
        Buku b = cariBuku(bacaString("Kode buku yang dipinjam: "));
        if (b == null || !b.isTersedia()) {
            System.out.println("Buku tidak ditemukan / sedang dipinjam. Mahasiswa tetap di antrean.");
            return;
        }
        System.out.println("Hari: 1.Senin 2.Selasa 3.Rabu 4.Kamis 5.Jumat 6.Sabtu 7.Minggu");
        int hari = bacaInt("Pilih hari (1-7): ") - 1;
        if (hari < 0 || hari > 6) {
            System.out.println("Hari tidak valid.");
            return;
        }
        antrean.poll(); // keluarkan dari antrean
        b.setTersedia(false);
        b.setPeminjam(mhs);
        peminjaman[b.getKategori()][hari]++;                                  // update array 2D
        riwayat.push(new Transaksi("PINJAM", b, mhs, hari, HARI[hari]));      // catat ke stack
        System.out.println(mhs + " berhasil meminjam \"" + b.getJudul() + "\".");
    }

    // ===== Pengembalian =====
    static void kembalikanBuku() {
        Buku b = cariBuku(bacaString("Kode buku yang dikembalikan: "));
        if (b == null) {
            System.out.println("Buku tidak ditemukan.");
            return;
        }
        if (b.isTersedia()) {
            System.out.println("Buku ini tidak sedang dipinjam.");
            return;
        }
        int hari = bacaInt("Hari pengembalian (1-7): ") - 1;
        if (hari < 0 || hari > 6) {
            System.out.println("Hari tidak valid.");
            return;
        }
        riwayat.push(new Transaksi("KEMBALI", b, b.getPeminjam(), hari, HARI[hari]));
        b.setTersedia(true);
        b.setPeminjam(null);
        System.out.println("Buku berhasil dikembalikan.");
    }

    // ===== 5. Stack =====
    static void menuRiwayat() {
        System.out.println("\n-- Riwayat Transaksi --");
        System.out.println("1. Lihat transaksi terakhir");
        System.out.println("2. Tampilkan semua riwayat");
        System.out.println("3. Batalkan transaksi terakhir");
        int pilih = bacaInt("Pilih: ");

        if (riwayat.isEmpty()) {
            System.out.println("Belum ada transaksi.");
            return;
        }
        switch (pilih) {
            case 1:
                System.out.println("Terakhir: " + riwayat.peek());
                break;
            case 2:
                for (int i = riwayat.size() - 1; i >= 0; i--) { // dari terbaru ke terlama
                    System.out.println((riwayat.size() - i) + ". " + riwayat.get(i));
                }
                break;
            case 3:
                batalkanTransaksi();
                break;
            default:
                System.out.println("Pilihan tidak valid.");
        }
    }

    static void batalkanTransaksi() {
        Transaksi t = riwayat.pop(); // ambil & hapus transaksi paling atas
        Buku b = t.getBuku();
        if (t.getJenis().equals("PINJAM")) {
            b.setTersedia(true);
            b.setPeminjam(null);
            peminjaman[b.getKategori()][t.getHari()]--;
        } else {
            b.setTersedia(false);
            b.setPeminjam(t.getMahasiswa());
        }
        System.out.println("Transaksi dibatalkan: " + t);
    }
}
