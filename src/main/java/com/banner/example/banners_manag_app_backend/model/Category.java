package com.banner.example.banners_manag_app_backend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name="name", unique = true)
    @Length(max=255, message = "Length of name should be less then 255 chars")
    @NotBlank(message = "Category's name couldn't be empty")
    private String name;

    @NotBlank(message = "Category's request name couldn't be empty")
    @Length(min=3, max=255, message = "Length of request name should be between 3 and 255 chars")
    @Column(name="req_name", unique = true)
    private String reqName;

    @Column(name="deleted")
    private boolean deleted;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "category")
    @JsonIgnore
    private List<Banner> banners;

    public Category() {
    }

    public Category(@NotBlank @Max(255) String name, @NotBlank String reqName) {
        this.name = name;
        this.reqName = reqName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reqName='" + reqName + '\'' +
                '}';
    }
}
