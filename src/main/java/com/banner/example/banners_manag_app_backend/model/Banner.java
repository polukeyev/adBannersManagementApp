package com.banner.example.banners_manag_app_backend.model;

import com.banner.example.banners_manag_app_backend.validation.annotations.Price;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotBlank(message = "Banner's name couldn't be empty")
    @Column(name="name", unique = true)
    @Length(max=255, message = "Length of name should be less 255 chars")
    private String name;

    @Column(name = "price")
    @Positive(message = "Enter positive price")
    @Price(message = "Price should be between 0.01 and 999999.99")
    private float price;

    @Column(name="content")
    @NotBlank(message = "Content couldn't be empty in Banner")
    private String content;

    @Column(name="deleted")
    private boolean deleted;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @NotNull(message = "You should choose category")
    private Category category;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            mappedBy = "banner")
    @JsonIgnore
    private List<Request> requests;

    public Banner() {
    }

    public Banner(@NotBlank @Max(255) String name, float price, String content, Category category) {
        this.name = name;
        this.price = price;
        this.content = content;
        this.category = category;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", category=" + category +
                '}';
    }
}
