package com.banner.example.banners_manag_app_backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="user_agent", length = 2147483647)
    private String userAgent;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="date")
    private LocalDateTime date;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "banner_id")
    private Banner banner;

    public Request() {
    }

    public Request(String userAgent, String ipAddress, LocalDateTime date, Banner banner) {
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.date = date;
        this.banner = banner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", userAgent='" + userAgent + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", date=" + date +
                ", banner=" + banner +
                '}';
    }
}
