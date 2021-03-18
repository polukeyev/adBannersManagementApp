package com.banner.example.banners_manag_app_backend.repository;

import com.banner.example.banners_manag_app_backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Category findCategoryByReqName(String reqName);
    Category findCategoryByName(String name);
    List<Category> findAllByNameContaining(String name);

    boolean existsCategoryByName(String name);
    boolean existsCategoryByReqName(String reqName);
    boolean existsCategoryByNameAndIdNotLike(String name, int id);
    boolean existsCategoryByReqNameAndIdNotLike(String reqName, int id);
}
