package com.itforshort.toko.repository;

import com.itforshort.toko.model.DetailPenjualan;
import com.itforshort.toko.model.Penjualan;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface DetailPenjualanRepository extends JpaRepository<DetailPenjualan, Long> {

    @Transactional
    void deleteByPenjualan(Penjualan penjualan);
}
