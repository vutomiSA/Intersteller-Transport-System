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
 * @author Vee
 */
@Entity
public class Result implements Serializable{
    @Id
    private String id;
    @OneToOne
    @JoinColumn(name = "source")
    private Planet source;
    @OneToOne
    @JoinColumn(name = "destination")
    private Planet destination;

    public Result() {
    }

    public Result(String id) {
        this.id = id;
    }

    public Result(String id, Planet source, Planet destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
