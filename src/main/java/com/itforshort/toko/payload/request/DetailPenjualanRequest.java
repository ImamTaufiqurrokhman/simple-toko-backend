package com.itforshort.toko.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DetailPenjualanRequest {
    @NotNull
    private Long barang_id;
    @NotNull
    @Size(min = 1)
    private int jumlah;

    public Long getBarang_id() {
        return barang_id;
    }

    public void setBarang_id(Long barang_id) {
        this.barang_id = barang_id;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
