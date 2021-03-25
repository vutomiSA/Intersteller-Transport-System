/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author Vutomi Ngobeni
 */
@Entity
public class Route implements Serializable{
    
    @Id
    private Short id;
    private double distance;
    @OneToOne
    @JoinColumn(name = "source")
    private Planet source;
    @OneToOne
    @JoinColumn(name = "destination")
    private Planet destination;

    public Route() {
    }

    public Route(Short id) {
        this.id = id;
    }

    public Route(Short id, Planet source, Planet destination, double distance) {
        this.id = id;
        this.distance = distance;
        this.source = source;
        this.destination = destination;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Planet getSource() {
        return source;
    }

    public void setSource(Planet source) {
        this.source = source;
    }

    public Planet getDestination() {
        return destination;
    }

    public void setDestination(Planet destination) {
        this.destination = destination;
    }
    
    
}
