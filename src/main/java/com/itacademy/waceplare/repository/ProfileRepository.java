package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
