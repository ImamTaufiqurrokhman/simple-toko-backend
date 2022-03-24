package com.itforshort.toko.repository;

import com.itforshort.toko.model.Penjualan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Repository
public interface PenjualanRepository extends JpaRepository<Penjualan, Long> {
    Page<Penjualan> findAllByTanggalBetween(Date tanggalStart, Date tanggalEnd, Pageable pageable);

    Optional<Penjualan> findPenjualanByKode(String kode);
//    Optional<Penjualan> findByKode(String kode);

    @Transactional
    void deletePenjualanByKode(String kode);

    boolean existsByKode(String kode);

    @Override
    Page<Penjualan> findAll(Pageable pageable);
}
