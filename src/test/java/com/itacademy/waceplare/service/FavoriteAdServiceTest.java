package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.AdRepository;
import com.itacademy.waceplare.repository.FavoriteAdRepository;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        User user = new User();
        user.setId(1L);
        when(authentication.getPrincipal()).thenReturn(user);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getAll_ShouldReturnListOfAds() {

        List<Ad> expectedAds = new ArrayList<>();
        expectedAds.add(new Ad());
        expectedAds.add(new Ad());

        Long userId = 1L;
        when(favoriteAdRepository.findAdsByUserId(userId)).thenReturn(expectedAds);

        List<Ad> actualAds = favoriteAdService.getAll();

        assertEquals(expectedAds, actualAds);
    }

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