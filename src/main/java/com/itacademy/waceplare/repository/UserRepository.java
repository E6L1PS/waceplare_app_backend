package com.itacademy.waceplare.repository;

import com.itacademy.waceplare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select u.rating from User u where u.id = :id")
    Double findRatingById(Long id);

    @Modifying
    @Query("update User u set u.rating = :rating where u.id = :id")
    void updateRatingById(Long id, Double rating);

    Optional<User> findByEmail(String email);

}
