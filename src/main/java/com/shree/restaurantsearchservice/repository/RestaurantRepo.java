package com.shree.restaurantsearchservice.repository;

import com.shree.restaurantsearchservice.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long>, JpaSpecificationExecutor<Restaurant> {

}
