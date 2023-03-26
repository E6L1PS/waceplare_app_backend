package com.itacademy.waceplare.service;

import com.itacademy.waceplare.dto.AdDTO;
import com.itacademy.waceplare.model.Ad;

import java.util.List;

public interface IAdService {
    List<Ad> getAll();

    List<Ad> getAllByTitle(String name);

    void add(AdDTO adDTO);

    void delete(Ad ad);
}
