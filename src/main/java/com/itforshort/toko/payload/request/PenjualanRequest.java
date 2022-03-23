package com.itforshort.toko.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class PenjualanRequest {
    @NotNull
    @Size(min = 3, max = 50)
    private String nama_pembeli;

    @NotNull
    @Size(min = 6, max = 15)
    private String no_hp_pembeli;

    @NotNull
    private List<DetailPenjualanRequest> detail;

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

    public List<DetailPenjualanRequest> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailPenjualanRequest> detail) {
        this.detail = detail;
    }
}
