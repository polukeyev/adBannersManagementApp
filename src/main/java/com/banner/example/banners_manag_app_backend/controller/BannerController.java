package com.banner.example.banners_manag_app_backend.controller;

import com.banner.example.banners_manag_app_backend.repository.BannerRepo;
import com.banner.example.banners_manag_app_backend.repository.CategoryRepo;
import com.banner.example.banners_manag_app_backend.repository.RequestRepo;
import com.banner.example.banners_manag_app_backend.model.Banner;
import com.banner.example.banners_manag_app_backend.model.Category;
import com.banner.example.banners_manag_app_backend.model.Request;
import com.banner.example.banners_manag_app_backend.validation.ValidationErrorResponse;
import com.banner.example.banners_manag_app_backend.validation.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BannerController {

    @Autowired
    private BannerRepo bannerRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private RequestRepo requestRepo;

    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> showAllBanners(@RequestParam(required = false) String name) {
        try{
            List<Banner> banners = new ArrayList<>();

            if(name == null)
                bannerRepo.findAll().stream().filter(banner -> !banner.isDeleted()).forEach(banners::add);
            else
                bannerRepo.findAllByNameContainingIgnoreCase(name)
                        .stream()
                        .filter(banner -> !banner.isDeleted())
                        .forEach(banners::add);

            if(banners.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(banners, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/banners/{id}")
    public ResponseEntity<Banner> getBannerById(@PathVariable("id") int id) {
        Optional<Banner> bannerOptional = bannerRepo.findById(id);

        if(bannerOptional.isPresent())
            return new ResponseEntity<>(bannerOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/banners")
    public ResponseEntity<?> newBanner(@RequestBody @Valid Banner banner) {
        Category category = categoryRepo.findCategoryByName(banner.getCategory().getName());
        String bannerExist = String.format("Banner with name [%s] already exist", banner.getName());

        try {
            if (bannerRepo.existsBannerByName(banner.getName())) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("name", bannerExist));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            } else {
                Banner _banner = bannerRepo.save(new Banner(
                        banner.getName()
                        , banner.getPrice()
                        , banner.getContent()
                        , category));
                return new ResponseEntity<>(_banner, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/banners/{id}")
    public ResponseEntity<?> updateBanner(@PathVariable("id") int id, @RequestBody @Valid Banner banner) {
        Optional<Banner> bannerOptional = bannerRepo.findById(id);
        Category category = categoryRepo.findCategoryByName(banner.getCategory().getName());
        String bannerExist = String.format("Banner with name [%s] already exist", banner.getName());

        if (bannerOptional.isPresent()) {
            if(bannerRepo.existsBannerByNameAndIdNotLike(banner.getName(), banner.getId())) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("name", bannerExist));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            } else {
                Banner _banner = bannerOptional.get();
                _banner.setName(banner.getName());
                _banner.setPrice(banner.getPrice());
                _banner.setCategory(category);
                _banner.setContent(banner.getContent());
                return new ResponseEntity<>(bannerRepo.save(_banner), HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/banners/{id}")
    public ResponseEntity<Banner> deleteBanner(@PathVariable int id) {
        Optional<Banner> bannerOptional = bannerRepo.findById(id);

        if (bannerOptional.isPresent()) {
            Banner _banner = bannerOptional.get();
            _banner.setDeleted(true);
            return new ResponseEntity<>(bannerRepo.save(_banner), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/bid")
    public ResponseEntity<?> showAdBanner(@RequestHeader("User-Agent") String userAgent
            , @RequestParam("category") String reqName, HttpServletRequest httpServletRequest) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        String ipAddress = httpServletRequest.getRemoteAddr();

        Category category = categoryRepo.findCategoryByReqName(reqName);
        List<Banner> banners = new ArrayList<>();
        category.getBanners().stream().filter(banner -> !banner.isDeleted()).forEach(banners::add);
        banners.sort((b1, b2) -> Float.compare(b2.getPrice(), b1.getPrice()));

        Banner banner;

        try {
            while (banners.size()>0) {
                banner = banners.get(0);
                Request request = requestRepo.findFirstByBannerAndUserAgentAndIpAddressOrderByIdDesc(
                        banner, userAgent, ipAddress
                );

                if(request == null) {
                    requestRepo.save(new Request(userAgent, ipAddress, currentDateTime, banner));
                    return new ResponseEntity<>(banner.getContent(), HttpStatus.OK);
                } else {

                    LocalDateTime dateTime = request.getDate();

                    if (ChronoUnit.DAYS.between(dateTime, currentDateTime) > 0) {
                        requestRepo.save(new Request(userAgent, ipAddress, currentDateTime, banner));
                        return new ResponseEntity<>(banner.getContent(), HttpStatus.OK);
                    } else {
                        banners.remove(0);
                    }
                }
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            banner = banners.get(0);
            requestRepo.save(new Request(userAgent, ipAddress, currentDateTime, banner));
            return new ResponseEntity<>(banner.getContent(), HttpStatus.OK);
        }
    }
}