package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring3.classes.Rating;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingMapperTest {

    private final RatingMapper ratingMapper = RatingMapper.INSTANCE;

    @Test
    public void testMapToRatingDTO() {

        Rating rating = new Rating(1, 5);

        RatingDTO ratingDTO = ratingMapper.mapToRatingDTO(rating);

        assertEquals(rating.getRatingId(), ratingDTO.getRatingId());
        assertEquals(rating.getNumber(), ratingDTO.getNumber());
    }

    @Test
    public void testMapToRating() {

        RatingDTO ratingDTO = new RatingDTO(1, 5);

        Rating rating = ratingMapper.mapToRating(ratingDTO);

        assertEquals(ratingDTO.getRatingId(), rating.getRatingId());
        assertEquals(ratingDTO.getNumber(), rating.getNumber());
    }
}
