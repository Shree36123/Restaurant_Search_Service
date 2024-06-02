package com.shree.restaurantsearchservice.service;

import com.shree.restaurantsearchservice.model.Address;
import com.shree.restaurantsearchservice.model.Restaurant;
import com.shree.restaurantsearchservice.repository.RestaurantRepo;
import jakarta.persistence.criteria.Predicate;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepo restaurantRepo;
    @Autowired
    AddressService addressService;

    Page<Restaurant> getAllRestaurant(Pageable pageing) {
        return restaurantRepo.findAll(pageing);
    }

    Page<Restaurant> getAllRestaurantBasedOnRestaurantName(String restaurantName, Pageable pagging) {
        return restaurantRepo.findAll((root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtil.isEmpty(restaurantName)) {
                predicate = criteriaBuilder.equal(root.get("name"), "%" + restaurantName + "%");
            }
            query.where(predicate);
            return query.getRestriction();
        }, pagging);
    }

    Restaurant addOrUpdateRestaurantDetails(Restaurant restaurant) {
        return restaurantRepo.save(restaurant);
    }

    @Transactional
    List<Restaurant> addListOfRestaurant(List<Restaurant> restaurants) {
        return restaurantRepo.saveAll(restaurants);
    }

    @Transactional
    void deleteRestaurant(Restaurant restaurant) {
        restaurantRepo.delete(restaurant);
    }

    List<Restaurant> getListOfRestaurantBasedOnAddress(
            String city, String state, String area, Long pinCode, String freeSearch
    ) {
        return restaurantRepo.findByAddress_IdIn(
                addressService.getListOfAddressId(city, state, area, pinCode, freeSearch));
    }
}
