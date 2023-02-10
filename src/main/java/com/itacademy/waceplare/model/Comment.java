package com.itacademy.waceplare.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private Profile author;

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false, updatable = false)
    private Ad ad;

}
