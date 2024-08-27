package com.sumerge.spring.controller;

import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingService ratingService;

    // Bean constructor
    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // Add rating --> POST mapping
    @PostMapping("/add")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO ratingDTO) throws ValidationException {
        try {
            RatingDTO createdRating = ratingService.addRating(ratingDTO);
            return new ResponseEntity<>(createdRating, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // View rating by id --> GET mapping
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingDTO> viewRating(@PathVariable int ratingId) {
        try {
            RatingDTO rating = ratingService.viewRating(ratingId);
            return new ResponseEntity<>(rating, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update rating --> PUT mapping
    @PutMapping("/update/{ratingId}")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<RatingDTO> updateRating(@PathVariable int ratingId, @RequestBody RatingDTO ratingDTO) {
        try {
            ratingDTO.setRatingId(ratingId);
            RatingDTO updatedRating = ratingService.updateRating(ratingDTO);
            return new ResponseEntity<>(updatedRating, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete rating by id --> DELETE mapping
    @DeleteMapping("/delete/{ratingId}")
    @PreAuthorize("@securityService.isAdmin()")
    public ResponseEntity<Void> deleteRating(@PathVariable int ratingId) {
        try {
            ratingService.deleteRating(ratingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
