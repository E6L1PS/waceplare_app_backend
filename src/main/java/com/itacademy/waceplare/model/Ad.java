package com.itacademy.waceplare.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ads")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer views;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ad")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ad")
    private List<Image> images;

    private Long previewImageId;

    private String description;

    private LocalDate dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDate.now();
    }

}
