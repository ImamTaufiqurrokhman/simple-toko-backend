package com.itforshort.toko.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    protected ResponseEntity<Map<String, Object>> paginationResponseEntity(Page<?> page, String sortBy, String sortType) {
        List<?> entity;
        entity = page.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", entity);
        response.put("currentPage", page.getNumber() + 1);
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        response.put("sortBy", sortBy);
        response.put("sortType", sortType);
        response.put("pageSize", page.getSize());
        response.put("totalPageItem", page.getNumberOfElements());
        if (entity.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected Sort getSortDirection(String sort_by, String sort_type) {
        if (sort_type.equals("asc")) {
            return Sort.by(sort_by).ascending();
        } else if (sort_type.equals("desc")) {
            return Sort.by(sort_by).descending();
        }
        return Sort.by(sort_by).ascending();
    }
}
