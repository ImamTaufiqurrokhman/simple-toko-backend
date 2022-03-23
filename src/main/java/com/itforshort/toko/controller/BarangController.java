package com.itforshort.toko.controller;

import com.itforshort.toko.model.Barang;
import com.itforshort.toko.payload.response.MessageResponse;
import com.itforshort.toko.repository.BarangRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BarangController extends Controller{
    private final BarangRepository barangRepository;

    public BarangController(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }
    
    @GetMapping("/barang")
    public ResponseEntity<Map<String, Object>> getAllBarang(
            @RequestParam(required = false) String nama,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "nama") String sort_by,
            @RequestParam(required = false, defaultValue = "desc") String sort_type
    ) {
        try {
            Pageable paging = PageRequest.of(page-1, size, getSortDirection(sort_by, sort_type));
            Page<Barang> pageBarang;
            
            if (nama == null) {
                pageBarang = barangRepository.findAll(paging);
            } else {
                pageBarang = barangRepository.findByNamaContaining(nama, paging);
            }
            return paginationResponseEntity(pageBarang, sort_by, sort_type);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/barang/{id}")
    public ResponseEntity<Barang> getBarangById(@PathVariable("id") long id) {
        Optional<Barang> dataBarang = barangRepository.findById(id);
        return dataBarang.map(barang -> new ResponseEntity<>(barang, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/barang")
    public ResponseEntity<?> storeBarang(@RequestBody Barang barang) {
        try {
            Barang _barang = barangRepository.save(new Barang(barang.getNama(), barang.getHarga()));
            return new ResponseEntity<>(_barang, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/barang/{id}")
    public ResponseEntity<Barang> updateBarang(@PathVariable("id") long id, @RequestBody Barang barang) {
        Optional<Barang> dataBarang = barangRepository.findById(id);
        if (dataBarang.isPresent()) {
            Barang _barang = dataBarang.get();
            _barang.setNama(barang.getNama());
            _barang.setHarga(barang.getHarga());
            return new ResponseEntity<>(barangRepository.save(_barang), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/barang/{id}")
    public ResponseEntity<?> deleteBarang(@PathVariable("id") long id) {
        Optional<Barang> dataBarang = barangRepository.findById(id);
        if (dataBarang.isPresent()) {
            try {
                barangRepository.deleteById(id);
                return new ResponseEntity<>(new MessageResponse("Data telah dihapus"), HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new MessageResponse("Data tidak ditemukan"), HttpStatus.NOT_FOUND);
        }
    }



}
