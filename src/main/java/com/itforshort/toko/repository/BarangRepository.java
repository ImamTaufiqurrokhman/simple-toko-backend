package com.itforshort.toko.repository;

import com.itforshort.toko.model.Barang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarangRepository extends JpaRepository<Barang, Long> {
    Page<Barang> findByNamaContaining(String nama, Pageable pageable);
}
