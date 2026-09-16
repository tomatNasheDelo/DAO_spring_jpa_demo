package com.example;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Entity
public class Item {



   // @Id  
 //   @GeneratedValue(generator = "ID_GENERATOR")
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;



    @NotNull 
    @Size( 
        min = 2,
        max = 255, 
        message = "Name is required, maximum 255 characters."
    )
    private String name;


    @Transient
    private Set<Bid> bids = new HashSet<>();


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Set<Bid> getBids() {
        return  Collections.unmodifiableSet(bids);
    }


    public void addBid(Bid bid) {
        bids.add(bid);
    }


    
    
}
