/*
package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.FavoriteAd;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.FavoriteAdRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FavoriteAdServiceTest {

    @Mock
    private FavoriteAdRepository favoriteAdRepository;

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private FavoriteAdService favoriteAdService;

    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    private List<Ad> ads;

    private List<FavoriteAd> favoriteAds;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        securityContext = Mockito.mock(SecurityContext.class);
        authentication = Mockito.mock(Authentication.class);

        User user = User.builder()
                .id(123L)
                .email("e")
                .password("p")
                .build();

        favoriteAds = List.of(
                FavoriteAd.builder()
                        .user(user)
                        .ad(Ad.builder()
                                .id(123L)
                                .title("notebook")
                                .status(true)
                                .user(user)
                                .build())
                        .build()
        );
        ads = List.of(
                Ad.builder()
                        .id(123L)
                        .title("notebook")
                        .status(true)
                        .user(user)
                        .build(),
                Ad.builder()
                        .id(124L)
                        .title("keyboard")
                        .status(false)
                        .user(user)
                        .build(),
                Ad.builder()
                        .id(125L)
                        .title("notebook")
                        .status(false)
                        .user(user)
                        .build()
        );
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);

    }

    @Ignore
    @Test
    void getAll_ShouldReturnListOfAds() {
        Long userId = ((User) authentication.getPrincipal()).getId();
        when(favoriteAdRepository.findAdsByUserId(userId))
                .thenReturn(
                        favoriteAds.stream().filter(favoriteAd ->
                                favoriteAd.getUser().getId().equals(userId)
                        ).map(FavoriteAd::getAd).toList()
                );

        List<Ad> result = favoriteAdService.getAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Ignore
    @Test
    void getAdsId_ShouldReturnListOfAdIds() {
        // Arrange
        List<Long> expectedAdIds = new ArrayList<>();
        expectedAdIds.add(1L);
        expectedAdIds.add(2L);

        Long userId = 1L;
        when(favoriteAdRepository.findAdsIdByUserId(userId)).thenReturn(expectedAdIds);

        List<Long> actualAdIds = favoriteAdService.getAdsId();
        assertEquals(expectedAdIds, actualAdIds);
    }

    @Ignore
    @Test
    void addByAdId_ShouldIncrementFavoriteCountAndSaveFavoriteAd() {
        Long adId = 1L;

        User user = new User();
        user.setId(1L);

        Ad ad = new Ad();
        ad.setId(adId);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(adRepository.findById(adId)).thenReturn(Optional.of(ad));

        favoriteAdService.addByAdId(adId);
    }

    @Ignore
    @Test
    void addByAdId_ShouldThrowUserNotFoundExceptionWhenAdNotFound() {
        Long adId = 1L;
        User user = new User();
        user.setId(1L);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(adRepository.findById(adId)).thenReturn(Optional.empty());
    }

    @Test
    void deleteByAdId_ShouldDecrementFavoriteCountAndDeleteFavoriteAd() {
        Long adId = 1L;
        Long userId = 1L;
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new User());
        favoriteAdService.deleteByAdId(adId);
    }

    @Test
    void deleteFavorites_ShouldDeleteAllFavoritesByUserId() {
        Long userId = 1L;
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new User());
        favoriteAdService.deleteFavorites();
    }

    @Test
    void deleteInactiveFavorites_ShouldDeleteAllInactiveFavoritesByUserId() {
        Long userId = 1L;
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(new User());
        favoriteAdService.deleteInactiveFavorites();
    }
}
*/
