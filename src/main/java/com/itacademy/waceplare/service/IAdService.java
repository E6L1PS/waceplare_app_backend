package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;

import java.util.List;

public interface IAdService {
    List<Ad> getAll();

    Ad getOne(String name);

    void add(Ad ad);

    void delete(Ad ad);
}
