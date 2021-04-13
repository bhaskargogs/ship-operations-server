package com.operations.ship.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Ship {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private double length;
    private double width;
    private String code;

    public Ship(String name, double length, double width, String code) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.code = code;
    }
}
