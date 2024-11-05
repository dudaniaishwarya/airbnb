package com.airbnb.controller;

import com.airbnb.dto.ReviewDto;
import com.airbnb.dto.entity.Property;
import com.airbnb.dto.entity.Review;
import com.airbnb.entity.PropertyUser;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")

public class ReviewController {
    private ReviewRepository reviewRepository;

    private PropertyRepository propertyRepository;

    public ReviewController(ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(
            @PathVariable long propertyId,
            @RequestBody Review review,
            @AuthenticationPrincipal PropertyUser user) {
        //  Review r = reviewRepository.findReviewByUserIdAndPropertyId(propertyId, user.getId());
        //return new ResponseEntity<>("you have already added a review for this property",HttpStatus.BAD_REQUEST);
        //}(r!=nul

        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        Review r = reviewRepository.findReviewByUser(property, user);
        if (r != null) {
            return new ResponseEntity<>("you have already added a review for this property", HttpStatus.BAD_REQUEST);
        }

        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }
    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getUserReviews(@AuthenticationPrincipal PropertyUser user){
        List<Review> reviews = reviewRepository.findBypropertyUser(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);

    }
}
