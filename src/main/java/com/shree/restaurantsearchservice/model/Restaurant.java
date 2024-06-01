package com.shree.restaurantsearchservice.model;

import jakarta.persistence.*;

@Entity(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "restaurant_name", unique = true)
    String name;
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    Address address;
}
