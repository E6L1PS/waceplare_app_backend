package com.itacademy.waceplare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_favorite_ad")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id")
    private Ad ad;

    public FavoriteAd(User user, Ad ad) {
        this.user = user;
        this.ad = ad;
    }
}