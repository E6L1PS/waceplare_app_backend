package com.itacademy.waceplare.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    private String title;

    @Column(length = 3000)
    private String description;

    private Boolean status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date_of_created")
    private LocalDate dateOfCreated;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TypeAd type;

    @Enumerated(EnumType.STRING)
    private StateAd state;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ad", orphanRemoval = true)
    private List<Comment> comments;

    @JsonIgnore()
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ad", orphanRemoval = true)
    private List<FavoriteAd> favoriteAds;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ad", orphanRemoval = true)
    private List<AdImage> images;
/*
    @Transient
    private List<MultipartFile> images;*/

    public Ad(Integer price, String title, String description, TypeAd type, StateAd state, User user) {
        this.price = price;
        this.title = title;
        this.description = description;
        this.type = type;
        this.state = state;
        this.user = user;
    }

    @PrePersist
    private void init() {
        dateOfCreated = LocalDate.now();
        status = true;
        views = 0;
    }


}