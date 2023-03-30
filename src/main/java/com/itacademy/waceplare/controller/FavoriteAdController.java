package com.itacademy.waceplare.controller;

import com.itacademy.waceplare.model.Ad;
import com.itacademy.waceplare.security.JwtService;
import com.itacademy.waceplare.service.IFavoriteAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteAdController {

    private final IFavoriteAdService favoriteAdService;
    private final JwtService jwtService;

    @GetMapping
    public List<Ad> getAds() {
        // String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return favoriteAdService.getAll("danil@mail.ru");

   /*     String email = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            }
        }
        if (email != null) {
            return favoriteAdService.getAll(email);
        } else {
            throw new UserNotFoundException("User not authorized");
        }*/
    }

    @GetMapping("/title")
    public List<Ad> getAdsByTitle(@RequestParam("title") String title, Authentication authentication) {
        return favoriteAdService.getAll(title);
    }

    @PostMapping("{adId}")
    public void addFavoriteAd(@PathVariable Long adId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

        favoriteAdService.addByAdId(adId, username);
    }

    @DeleteMapping("{adId}")
    public void deleteFavoriteAd(@PathVariable Long adId) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        favoriteAdService.deleteByAdId(adId, username);
    }

}
