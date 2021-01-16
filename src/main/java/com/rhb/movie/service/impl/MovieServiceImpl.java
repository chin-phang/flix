package com.rhb.movie.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhb.movie.constant.ExceptionMessage;
import com.rhb.movie.dto.CreateUpdateMovieRequest;
import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.entity.Category;
import com.rhb.movie.entity.Movie;
import com.rhb.movie.mapper.MovieMapper;
import com.rhb.movie.repository.CategoryRepository;
import com.rhb.movie.repository.MovieRepository;
import com.rhb.movie.service.MovieService;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepository;
	
	private final CategoryRepository categoryRepository; 
	
	public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository) {
		this.movieRepository = movieRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieDto> getMovies() {
		List<MovieDto> list = movieRepository.findAll()
				.stream()
				.filter(Objects::nonNull)
				.map(MovieMapper::toDto)
				.collect(Collectors.toList());
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public MovieDto getMovieById(Long id) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND));
		
		return MovieMapper.toDto(entity);
	}
	
	@Override
	public MovieDto createMovie(CreateUpdateMovieRequest createMovie) {
		if (createMovie.getRating() == null) {
			throw new IllegalArgumentException(ExceptionMessage.RATING_NULL_ERROR);
		}
		
		if (createMovie.getTitle() == null || createMovie.getTitle().isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessage.TITLE_NULL_ERROR);
		}
		
		if (createMovie.getCategories() == null || createMovie.getCategories().isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessage.CATEGORY_NULL_ERROR);
		}
		
		// Check if rating within range and categories exist
		validateRating(createMovie.getRating());
		validateCategory(createMovie.getCategories());
		
		Movie entity = new Movie();
		entity.setTitle(createMovie.getTitle());
		entity.setRating(createMovie.getRating());
		entity.setCreatedBy("Anonymous creater");
		// Add categories which are present in the table to this movie
		Set<Category> categories = createMovie.getCategories().stream()
				.map(categoryRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toSet());
		entity.setCategories(categories);
		
		return MovieMapper.toDto(movieRepository.save(entity));
	}
	
	@Override
	public MovieDto updateMovie(Long id, CreateUpdateMovieRequest updateMovie) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND));
		
		// When update, rating can be null
		if (updateMovie.getRating() != null) {
			validateRating(updateMovie.getRating());
			entity.setRating(updateMovie.getRating());
		}
		// When update, title can be null
		if (updateMovie.getTitle() != null) {
			entity.setTitle(updateMovie.getTitle());
		}
		// When update, categories can be null
		if (updateMovie.getCategories() != null) {
			// Categories cannot be empty
			if (updateMovie.getCategories().isEmpty()) {
				throw new EntityNotFoundException(ExceptionMessage.CATEGORY_NULL_ERROR);
			}
			validateCategory(updateMovie.getCategories());
			// Clear existing ones then update categories which are present in the table
			Set<Category> categories = entity.getCategories();
			categories.clear(); 
			updateMovie.getCategories().stream()
				.map(categoryRepository::findById)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(categories::add);
			entity.setCategories(categories);
		}
		
		entity.setLastModifiedBy("Anonymous modifier");
		
		return MovieMapper.toDto(movieRepository.save(entity));
	}

	@Override
	public void deleteMovie(Long id) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.MOVIE_NOT_FOUND));
		
		movieRepository.delete(entity);
	}
	
	private void validateRating(Double rating) {
		if (rating < 0.5) {
			throw new IllegalArgumentException(ExceptionMessage.RATING_MIN_ERROR);
		} else if (rating > 5) {
			throw new IllegalArgumentException(ExceptionMessage.RATING_MAX_ERROR);
		}
	}
	
	private void validateCategory(Set<String> categories) {
		categories.stream()
			.forEach(string -> {
				categoryRepository.findById(string)
					.orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.CATEGORY_NOT_EXIST));
			});
	}
}
