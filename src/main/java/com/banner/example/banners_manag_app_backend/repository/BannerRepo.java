package com.banner.example.banners_manag_app_backend.repository;

import com.banner.example.banners_manag_app_backend.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepo extends JpaRepository<Banner, Integer> {
    List<Banner> findAllByNameContainingIgnoreCase(String name);
    boolean existsBannerByName(String name);
    boolean existsBannerByNameAndIdNotLike(String name, int id);
}
