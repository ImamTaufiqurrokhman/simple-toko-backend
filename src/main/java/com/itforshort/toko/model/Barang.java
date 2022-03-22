package com.itforshort.toko.model;

import javax.persistence.*;

@Entity
@Table(name = "barang")
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nama")
    private String nama;

    @Column(name = "harga")
    private double harga;

    public Barang(String nama, double harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public Barang() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

}
