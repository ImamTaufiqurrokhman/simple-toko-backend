package com.itforshort.toko.controller;

import com.itforshort.toko.model.Barang;
import com.itforshort.toko.model.DetailPenjualan;
import com.itforshort.toko.model.Penjualan;
import com.itforshort.toko.payload.request.PenjualanRequest;
import com.itforshort.toko.payload.response.MessageResponse;
import com.itforshort.toko.repository.BarangRepository;
import com.itforshort.toko.repository.DetailPenjualanRepository;
import com.itforshort.toko.repository.PenjualanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api")
public class PenjualanController extends Controller {
    private final PenjualanRepository penjualanRepository;
    private final BarangRepository barangRepository;
    private final DetailPenjualanRepository detailPenjualanRepository;

    public PenjualanController(PenjualanRepository penjualanRepository, BarangRepository barangRepository, DetailPenjualanRepository detailPenjualanRepository) {
        this.penjualanRepository = penjualanRepository;
        this.barangRepository = barangRepository;
        this.detailPenjualanRepository = detailPenjualanRepository;
    }
    
    @GetMapping("/penjualan")
    public ResponseEntity<?> getAllBarang(
            @RequestParam(required = false) String tanggal_mulai,
            @RequestParam(required = false) String tanggal_selesai,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "tanggal") String sort_by,
            @RequestParam(required = false, defaultValue = "desc") String sort_type
    ) {
        try {
            Pageable paging = PageRequest.of(page-1, size, getSortDirection(sort_by, sort_type));
            Page<Penjualan> pagePenjualan;
            
            if (tanggal_mulai == null || tanggal_selesai == null) {
                pagePenjualan = penjualanRepository.findAll(paging);
            } else {
                Date tanggalMulai = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tanggal_mulai));
                Date tanggalSelesai = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(tanggal_selesai));
                pagePenjualan = penjualanRepository.findAllByTanggalBetween(tanggalMulai, tanggalSelesai, paging);
            }
            return paginationResponseEntity(pagePenjualan, sort_by, sort_type);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/penjualan/{kode}")
    public ResponseEntity<Penjualan> getBarangById(@PathVariable("kode") String kode) {
        Optional<Penjualan> _penjualan = penjualanRepository.findPenjualanByKode(kode);
        return _penjualan.map(penjualan -> new ResponseEntity<>(penjualan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/penjualan")
    public ResponseEntity<?> storeBarang(@RequestBody PenjualanRequest request) {
        Date tanggal = new Date();
        String kode = String.valueOf(tanggal.getTime());
        AtomicReference<Double> totalBelanja = new AtomicReference<>((double) 0);
        List<DetailPenjualan> detailPenjualanList = new ArrayList<>();
        try {
            request.getDetail().forEach(detailPenjualan -> {
                 Optional<Barang> dataBarang = barangRepository.findById(detailPenjualan.getBarang_id());
                 if(dataBarang.isPresent()) {
                     double hargaBarang = dataBarang.get().getHarga() * detailPenjualan.getJumlah();
                     detailPenjualanList.add(new DetailPenjualan(detailPenjualan.getJumlah(), hargaBarang, dataBarang.get()));
                     totalBelanja.set(totalBelanja.get() + hargaBarang);
                 }
            });
            Penjualan _penjualan = penjualanRepository.save(new Penjualan(tanggal, kode, request.getNama_pembeli(), request.getNo_hp_pembeli(), totalBelanja.get()));
            detailPenjualanList.forEach(detailPenjualan -> {
                detailPenjualan.setPenjualan(_penjualan);
                detailPenjualanRepository.save(detailPenjualan);
            });
            return new ResponseEntity<>(_penjualan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/penjualan/{kode}")
    public ResponseEntity<?> deleteBarang(@PathVariable("kode") String kode) {
        Optional<Penjualan> _penjualan = penjualanRepository.findPenjualanByKode(kode);
        if (_penjualan.isPresent()) {
            try {
                detailPenjualanRepository.deleteByPenjualan(_penjualan.get());
                penjualanRepository.deletePenjualanByKode(kode);
                return new ResponseEntity<>(new MessageResponse("Data telah dihapus"), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Data tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }



}
