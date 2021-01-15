package com.rhb.movie.mapper;

import org.springframework.beans.BeanUtils;
import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.entity.Movie;

public class MovieMapper {

	public static MovieDto toDto(Movie entity) {
		return new MovieDto(entity);
	}
	
	public static Movie toEntity(MovieDto dto) {
		if (dto == null) {
			return null;
		} else {
			Movie entity = new Movie();
			BeanUtils.copyProperties(dto, entity);
			return entity;
		}
	}
}
