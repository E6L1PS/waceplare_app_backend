package com.itacademy.waceplare.service;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.model.Role;
import com.itacademy.waceplare.model.User;
import com.itacademy.waceplare.repository.ImageRepository;
import com.itacademy.waceplare.repository.AdRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private AdServiceImpl adService;

    private List<Ad> ads;

    private User user;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;
    private PageRequest pageRequest;

    AdServiceTest() {
    }

    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10, Sort.by("id").descending());
        user = User.builder()
                .firstname("fsfs")
                .lastname("fsfs")
                .role(Role.USER)
                .password("fsfs")
                .email("fsfs")
                .build();
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

    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void getAll_ShouldReturnListOfAds() {
        List<Ad> adList = ads.stream().filter(Ad::getStatus).toList();
        Mockito.when(adRepository.findByStatusTrue(pageRequest)).thenReturn(
                new PageImpl<>(adList, pageRequest, adList.size())
        );

        List<Ad> result = adService.getAll(pageRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getAllByTitle_ShouldReturnListOfAdsByTitle() {
        String title = "notebook";
        List<Ad> adList = ads.stream().filter(ad -> ad.getTitle().contains(title) && ad.getStatus()).toList();
        Mockito.when(adRepository.findByStatusTrueAndTitle(title, pageRequest)).thenReturn(
                new PageImpl<>(adList, pageRequest, adList.size())
        );

        List<Ad> result = adService.getAllByTitle(title, pageRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getAdsByUser_ShouldReturnListOfAdsByUser() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
        List<Ad> adList = ads.stream().filter(ad -> ad.getUser() == authentication.getPrincipal()).toList();
        Mockito.when(adRepository.findByUser(user, pageRequest)).thenReturn(
                new PageImpl<>(adList, pageRequest, adList.size())
        );

        List<Ad> result = adService.getAdsByUser(pageRequest);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void getAdById_ShouldReturnAdById() {
        Long id = 125L;
        Mockito.when(adRepository.findById(id)).thenReturn(
                ads.stream().filter(ad -> ad.getId().equals(id)).findFirst()
        );

        Ad result = adService.getAdById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    void postAd_ShouldReturnAdId() {

        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);


        Ad newAd = Ad.builder()
                .id(123L)
                .user((User) authentication.getPrincipal())
                .build();

        Mockito.when(adRepository.save(newAd)).thenReturn(
                newAd
        );

        Long result = adService.postAd(newAd);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(123, result);
    }

    @Test
    void uploadImages() {
    }

    @Test
    void deleteAd() {
    }

    @Test
    void hideAd() {
    }

    @Test
    void showAd() {
    }


}