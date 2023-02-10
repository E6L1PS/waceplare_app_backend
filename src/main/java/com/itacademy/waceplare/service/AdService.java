package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.repository.AdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class AdService implements IAdService {

    @Autowired
    private AdRepository adRepository;

    @Override
    public List<Ad> getAll() {
        return adRepository.findAll();
    }

    @Override
    public Ad getOne(String name) {
        return adRepository.findByName(name);
    }

    @Override
    @Transactional
    public void add(Ad ad) {
        log.info("saving new ad {}", ad);
        adRepository.save(ad);
    }

    @Override
    @Transactional
    public void delete(Ad ad) {
        log.info("saving new ad {}", ad);
        adRepository.delete(ad);
    }

}
