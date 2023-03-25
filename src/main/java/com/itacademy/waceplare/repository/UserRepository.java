package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
