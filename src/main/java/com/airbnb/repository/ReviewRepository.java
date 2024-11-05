package com.airbnb.repository;

import com.airbnb.dto.entity.Country;
import com.airbnb.dto.entity.Property;
import com.airbnb.dto.entity.Review;
import com.airbnb.entity.PropertyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // @Query("select r from Review r where r property = propertyId and r.propertyUser=:")
    //  Review findReviewByU
    //
    //serIdAndPropertyId(@Param("propertyId")long userId, @Param("userId") long propertyId);

    @Query("select r from Review r where r.property=:property and r.propertyUser=:user")
    Review findReviewByUser(@Param("property") Property property, @Param("user") PropertyUser user);

    List<Review> findBypropertyUser(PropertyUser user);

    interface CountryRepository extends JpaRepository<Country, Long> {
    }
}