package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "hazarddata")
public class HazardData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hid;

    @Column(name = "hazardtype", nullable = false)
    private String hazardType;

    @Column(name = "사진")
    private String photo;

    @Column(name = "gps")
    private String gps;

    @Column(name = "state")
    private String state;

    @Column(name = "dates", updatable = false)
    private LocalDateTime dates;

    @PrePersist
    protected void onCreate() {
        this.dates = LocalDateTime.now();
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public Long getId() {
        return hid;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public void setDates(LocalDateTime dates) {
        this.dates = dates;
    }

    public void setHazardType(String hazardType) {
        this.hazardType = hazardType;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
