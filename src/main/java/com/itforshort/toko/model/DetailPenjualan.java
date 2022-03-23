package com.itforshort.toko.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "detail_penjualan")
public class DetailPenjualan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "jumlah")
    private int jumlah;

    @Column(name = "total")
    private double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "penjualan_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Penjualan penjualan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "barang_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Barang barang;

    public DetailPenjualan(int jumlah, double total, Barang barang) {
        this.jumlah = jumlah;
        this.total = total;
        this.barang = barang;
    }

    public DetailPenjualan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public Penjualan getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(Penjualan penjualan) {
        this.penjualan = penjualan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
}
