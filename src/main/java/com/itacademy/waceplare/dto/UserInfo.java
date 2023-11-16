package com.itacademy.waceplare.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserInfo {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String number;
    private Double rating;
    private LocalDate dateOfCreated;
}