package com.banner.example.banners_manag_app_backend.repository;

import com.banner.example.banners_manag_app_backend.model.Banner;
import com.banner.example.banners_manag_app_backend.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepo extends JpaRepository<Request, Integer> {
    Request findFirstByBannerAndUserAgentAndIpAddressOrderByIdDesc(Banner banner, String userAgent, String ip);
}
