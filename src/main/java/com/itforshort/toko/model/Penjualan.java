package com.itforshort.toko.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "penjualan")
public class Penjualan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="kode")
    public String kode;

    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "nama_pembeli")
    private String nama_pembeli;

    @Column(name = "no_hp_pembeli")
    private String no_hp_pembeli;

    @Column(name = "total_harga_penjualan")
    private double total_harga_penjualan;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "penjualan_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DetailPenjualan> detail;

    public Penjualan(Date tanggal, String kode, String nama_pembeli, String no_hp_pembeli, double total_harga_penjualan) {
        this.tanggal = tanggal;
        this.kode = kode;
        this.nama_pembeli = nama_pembeli;
        this.no_hp_pembeli = no_hp_pembeli;
        this.total_harga_penjualan = total_harga_penjualan;
    }

    public Penjualan() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(String nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }

    public String getNo_hp_pembeli() {
        return no_hp_pembeli;
    }

    public void setNo_hp_pembeli(String no_hp_pembeli) {
        this.no_hp_pembeli = no_hp_pembeli;
    }

    public double getTotal_harga_penjualan() {
        return total_harga_penjualan;
    }

    public void setTotal_harga_penjualan(double total_harga_penjualan) {
        this.total_harga_penjualan = total_harga_penjualan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public List<DetailPenjualan> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailPenjualan> detail) {
        this.detail = detail;
    }
}

