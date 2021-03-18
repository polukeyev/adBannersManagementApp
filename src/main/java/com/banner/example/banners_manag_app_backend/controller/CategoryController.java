package com.banner.example.banners_manag_app_backend.controller;

import com.banner.example.banners_manag_app_backend.repository.CategoryRepo;
import com.banner.example.banners_manag_app_backend.model.Banner;
import com.banner.example.banners_manag_app_backend.model.Category;
import com.banner.example.banners_manag_app_backend.validation.ValidationErrorResponse;
import com.banner.example.banners_manag_app_backend.validation.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class CategoryController {
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> showAllCategories(@RequestParam(required = false) String name) {
        try {
            List<Category> categories = new ArrayList<>();

            if (name == null)
                categoryRepo.findAll().stream().filter(category -> !category.isDeleted()).forEach(categories::add);
            else
                categoryRepo.findAllByNameContaining(name)
                        .stream()
                        .filter(category -> !category.isDeleted())
                        .forEach(categories::add);

            if (categories.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") int id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);

        if(categoryOptional.isPresent())
            return new ResponseEntity<>(categoryOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/categories")
    public ResponseEntity<?> newCategory(@RequestBody @Valid Category category) {
        String categoryExistWithName = String.format("Category with name [%s] already exist", category.getName());
        String categoryExistWithReqName =
                String.format("Category with request name [%s] already exist", category.getReqName());
        boolean isCatExistWName = categoryRepo.existsCategoryByName(category.getName());
        boolean isCatExistWReqName = categoryRepo.existsCategoryByReqName(category.getReqName());

        try{
            if (isCatExistWName) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("name", categoryExistWithName));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            } else if (isCatExistWReqName) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("reqName", categoryExistWithReqName));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }  else {
                Category _category = categoryRepo.save(new Category(category.getName(), category.getReqName()));
                return new ResponseEntity<>(_category, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody @Valid Category category) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        String categoryExistWithName = String.format("Category with name [%s] already exist", category.getName());
        String categoryExistWithReqName =
                String.format("Category with request name [%s] already exist", category.getReqName());
        boolean isCatExistWName = categoryRepo.existsCategoryByNameAndIdNotLike(category.getName(), id);
        boolean isCatExistWReqName = categoryRepo.existsCategoryByReqNameAndIdNotLike(category.getReqName(), id);

        if (categoryOptional.isPresent()) {
            if (isCatExistWName) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("name", categoryExistWithName));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            } else if (isCatExistWReqName) {
                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("reqName", categoryExistWithReqName));

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            } else {
                Category _category = categoryOptional.get();
                _category.setName(category.getName());
                _category.setReqName(category.getReqName());
                return new ResponseEntity<>(categoryRepo.save(_category), HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Banner> banners = new ArrayList<>();
            category.getBanners().stream().filter(banner -> !banner.isDeleted()).forEach(banners::add);
            if (banners.isEmpty()) {
                category.setDeleted(true);
                return new ResponseEntity<>(categoryRepo.save(category), HttpStatus.OK);
            } else {
                StringBuilder builder = new StringBuilder();
                for (Banner b : banners) {
                    builder.append("[" + b.getId() + "] ");
                }
                String er = String.format("Banner(s) with id(s): %s is not deleted, you cannot delete this category", builder);

                ValidationErrorResponse error = new ValidationErrorResponse();
                error.getViolations().add(new Violation("id", er));

                return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
            }
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
