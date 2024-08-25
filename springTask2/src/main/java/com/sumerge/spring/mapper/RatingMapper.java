package com.sumerge.spring.mapper;

import com.sumerge.spring.dto.CourseDTO;
import com.sumerge.spring.dto.RatingDTO;
import com.sumerge.spring3.classes.Course;
import com.sumerge.spring3.classes.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RatingMapper {

    RatingMapper INSTANCE = Mappers.getMapper(RatingMapper.class);

    Rating mapToRating(RatingDTO ratingDTO);
    RatingDTO mapToRatingDTO(Rating rating); // Fix the parameter typ
}
