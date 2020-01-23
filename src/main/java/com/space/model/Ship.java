package com.space.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;


@Entity
@Table(name = "ships")
public class Ship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "PLANET")
    private String planet;
    @Enumerated(EnumType.STRING)
    @Column(name = "SHIP_TYPE")
    private ShipType shipType;
    @Temporal(TemporalType.DATE)
    @Column(name = "PROD_DATE")
    private Date prodDate;
    @Column(name = "IS_USED")
    private Boolean isUsed;
    @Column(name = "CREW_SIZE")
    private Integer crewSize;
    @Column(name = "RATING")
    private Double rating;
    @Column(name = "SPEED")
    private Double speed;

//    public Ship(Long id, String name, String planet, ShipType shipType, Long prodDate, Boolean isUsed, Double speed, Integer crewSize, Double rating) {
//        this.id = id;
//        this.name = name;
//        this.planet = planet;
//        this.shipType = shipType;
//        this.prodDate = prodDate;
//        this.isUsed = isUsed;
//        this.speed = speed;
//        this.crewSize = crewSize;
//        this.rating = rating;
//    }
//
//
//    public Ship(Long id, String name, String planet, ShipType shipType, Long prodDate, Double speed, Integer crewSize, Double rating) {
//        this.id = id;
//        this.name = name;
//        this.planet = planet;
//        this.shipType = shipType;
//        this.prodDate = prodDate;
//        this.isUsed = false;
//        this.speed = speed;
//        this.crewSize = crewSize;
//        this.rating = rating;
//    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
    }

    @Override
    public String toString() {
        return String.format("Department - Id: %d, Name: %s", id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Double getRating() {
//        double k = getUsed() ? 0.5 : 1.0;
//        double r = (80 * getSpeed() * k) / (-getProdDate() + 1);
//        return Math.round(r * 100.0) / 100.0;
        return rating;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }
}



