package com.sumerge.spring.service;

import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring.exception.ResourceNotFoundException;
import com.sumerge.spring.mapper.RatingMapper;
import com.sumerge.spring.repository.RatingRepository;
import com.sumerge.spring3.classes.Rating;
import com.sumerge.spring.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class RatingServiceTest {
    // mocks
    @Mock
    private RatingMapper ratingMapper;
    @Mock
    private RatingRepository ratingRepository;
    @InjectMocks
    private RatingService ratingService;

    // initialize
    private Rating rating;
    private Rating savedRating;
    private RatingDTO ratingDTO;
    private int ratingId = 1;
    private int ratingNumber = 1;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ratingDTO = new RatingDTO(ratingId,ratingNumber);
        rating = new Rating();
        savedRating = new Rating();
        // assign id
        savedRating.setRatingId(ratingId);
    }

    @Test
    void testAddRating_Success() throws ValidationException {
        // testing
        when(ratingRepository.findById(ratingDTO.getRatingId())).thenReturn(Optional.empty());
        when(ratingMapper.mapToRating(ratingDTO)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(savedRating);
        when(ratingMapper.mapToRatingDTO(savedRating)).thenReturn(ratingDTO);

        // check results
        RatingDTO result = ratingService.addRating(ratingDTO);

        assertNotNull(result,"The result should not be null");
        assertEquals(ratingDTO.getRatingId(),result.getRatingId());
        assertEquals(ratingDTO.getNumber(),result.getNumber());
    }

    @Test
    void testAddRating_ValidationException(){

        when(ratingRepository.findById(ratingDTO.getRatingId())).thenReturn(Optional.of(new Rating()));

        // exception
        ValidationException exception = assertThrows(ValidationException.class, () -> ratingService.addRating(ratingDTO));

        // check results
        assertEquals("Rating with ID 1 already exists.",exception.getMessage());
    }

    @Test
    void testViewRating_Success() throws ResourceNotFoundException {
        //initialize
        rating.setRatingId(ratingId);
        rating.setNumber(ratingNumber);

        // arrange
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        // mapping
        when(ratingMapper.mapToRatingDTO(rating)).thenReturn(ratingDTO);

        // check results
        RatingDTO result = ratingService.viewRating(ratingId);

        assertNotNull(result,"The result should not be null");
        assertEquals(ratingId,result.getRatingId());
        assertEquals(ratingNumber, result.getNumber());
    }

    @Test
    void testViewRating_ResourseNotFoundException(){

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ratingService.viewRating(ratingId));

        assertEquals("Rating not found with ID: 1",exception.getMessage());
    }

    @Test
    void testUpdateRating_Success() throws ResourceNotFoundException{
        // Initialize
        rating.setRatingId(ratingId);
        rating.setNumber(ratingNumber);
        ratingDTO.setNumber(2);

        // Mocking
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.of(rating));
        when(ratingMapper.mapToRating(ratingDTO)).thenReturn(rating);  // Ensure this returns a non-null Rating
        when(ratingRepository.save(rating)).thenReturn(rating);  // Ensure this returns a non-null Rating
        when(ratingMapper.mapToRatingDTO(rating)).thenReturn(ratingDTO);  // Ensure this returns a non-null RatingDTO

        // Execution
        RatingDTO result = ratingService.updateRating(ratingDTO);

        // Check results
        assertNotNull(result, "The result should not be null");
        assertEquals(ratingId, result.getRatingId());
        assertEquals(2, result.getNumber());
    }

    @Test
    void testUpdateRating_ResourceNotFoundException(){

        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ratingService.updateRating(ratingDTO));

        assertEquals("Rating not found with ID 1",exception.getMessage());
    }

    @Test
    void testDeleteRating_Success() throws ResourceNotFoundException{

        when(ratingRepository.existsById(ratingId)).thenReturn(true);

        // Act
        ratingService.deleteRating(ratingId);

        // Assert
        verify(ratingRepository, times(1)).deleteById(ratingId);
    }

    @Test
    void testDeleteRating_ResourceNotFoundException(){
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ratingService.deleteRating(ratingId));

        assertEquals("Rating not found with ID 1",exception.getMessage());
    }
}
