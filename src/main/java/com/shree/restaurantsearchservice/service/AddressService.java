package com.shree.restaurantsearchservice.service;

import com.shree.restaurantsearchservice.model.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import liquibase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressService {
    @Autowired
    EntityManager entityManager;

    public List<Long> getListOfAddressId(String city, String state, String area, Long pinCode, String freeSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);
        criteriaQuery.distinct(true);
        List<Predicate> predicateList = new ArrayList<>();
        if (!StringUtil.isEmpty(city))
            predicateList.add(criteriaBuilder.equal(addressRoot.get("city"), city));
        if (!StringUtil.isEmpty(state))
            predicateList.add(criteriaBuilder.equal(addressRoot.get("state"), state));
        if (Objects.nonNull(pinCode))
            predicateList.add(criteriaBuilder.equal(addressRoot.get("pinCode"), pinCode));
        if (!StringUtil.isEmpty(area)) {
            predicateList.add(criteriaBuilder.like(addressRoot.get("street1"), "%" + area + "%"));
            predicateList.add(criteriaBuilder.like(addressRoot.get("street2"), "%" + area + "%"));
        }
        criteriaQuery.select(addressRoot.get("id")).where(predicateList.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
