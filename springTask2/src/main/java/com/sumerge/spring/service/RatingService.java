package com.sumerge.spring.service;

import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.RatingMapper;
import com.sumerge.spring.repository.RatingRepository;
import com.sumerge.spring3.classes.Course;
import com.sumerge.spring3.classes.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sumerge.spring3.*;

import javax.validation.ValidationException;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    @Autowired
    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    public RatingDTO addRating(RatingDTO ratingDTO) throws ValidationException {
        int ratingId = ratingDTO.getRatingId();

        // Check if a rating already exists for the given courseId and ratingId
        if (ratingRepository.findById(ratingId).isPresent()) {
            throw new ValidationException("Rating with ID " + ratingId + " already exists.");
        }

        Rating rating = ratingMapper.mapToRating(ratingDTO);
        Rating savedRating = ratingRepository.save(rating);
        return ratingMapper.mapToRatingDTO(savedRating);
    }

    public RatingDTO updateRating(RatingDTO ratingDTO) throws ResourceNotFoundException{
        int ratingId = ratingDTO.getRatingId();
        if (!ratingRepository.findById(ratingId).isPresent()) {
            throw new ResourceNotFoundException("Rating not found with ID " + ratingId);
        }

        Rating rating = ratingMapper.mapToRating(ratingDTO);
        Rating updatedRating = ratingRepository.save(rating);
        return ratingMapper.mapToRatingDTO(updatedRating);
    }

    // Delete rating
    public void deleteRating(int ratingId) throws ResourceNotFoundException {
        if (!ratingRepository.existsById(ratingId)) {
            throw new ResourceNotFoundException("Rating not found with ID " + ratingId);
        }
        ratingRepository.deleteById(ratingId);
    }


    // View rating by ID
    public RatingDTO viewRating(int ratingId) throws ResourceNotFoundException {
        Rating rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found with ID: " + ratingId));
        return ratingMapper.mapToRatingDTO(rating);
    }

}
