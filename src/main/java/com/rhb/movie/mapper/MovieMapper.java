package com.rhb.movie.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.entity.Category;
import com.rhb.movie.entity.Movie;

@Service
public class MovieMapper {

	public static MovieDto toDto(Movie entity) {
		return new MovieDto(entity);
	}
	
	public static Movie toEntity(MovieDto dto) {
		if (dto == null) {
			return null;
		} else {
			Movie entity = new Movie();
			entity.setId(dto.getId());
			entity.setTitle(dto.getTitle());
			entity.setRating(dto.getRating());
			Set<Category> categories = categoriesFromStrings(dto.getCategories());
			entity.setCategories(categories);
			return entity;
		}
	}
	
	private static Set<Category> categoriesFromStrings(Set<String> categoriesAsString) {
		Set<Category> categories = new HashSet<>();
		
		if (categoriesAsString != null) {
			categories = categoriesAsString.stream().map(string -> {
				Category cat = new Category();
				cat.setName(string);
				return cat;
			}).collect(Collectors.toSet());
		}
		
		return categories;
	}
}
