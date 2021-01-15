package com.rhb.movie.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhb.movie.constant.Constant;
import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.entity.Movie;
import com.rhb.movie.mapper.MovieMapper;
import com.rhb.movie.repository.MovieRepository;
import com.rhb.movie.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieRepository movieRepository;
	
	@Override
	public List<MovieDto> getMovies() {
		List<MovieDto> list = movieRepository.findAll()
				.stream()
				.filter(Objects::nonNull)
				.map(MovieMapper::toDto)
				.collect(Collectors.toList());
		return list;
	}

	@Override
	public MovieDto getMovieById(Long id) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Constant.MOVIE_NOT_FOUND));
		
		return MovieMapper.toDto(entity);
	}
	
	@Override
	public MovieDto createMovie(MovieDto movieDto) {
		validateRating(movieDto.getRating());
		
		Movie entity = movieRepository.save(MovieMapper.toEntity(movieDto));
		
		return MovieMapper.toDto(entity);
	}
	
	@Override
	public MovieDto updateMovie(Long id, MovieDto movieDto) {
		validateRating(movieDto.getRating());
		
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Constant.MOVIE_NOT_FOUND));
		
		entity.setCategory(movieDto.getCategory());
		entity.setTitle(movieDto.getTitle());
		entity.setRating(movieDto.getRating());
		
		return MovieMapper.toDto(movieRepository.save(entity));
	}

	@Override
	public void deleteMovie(Long id) {
		Movie entity = movieRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Constant.MOVIE_NOT_FOUND));
		
		movieRepository.delete(entity);
	}
	
	private void validateRating(Double rating) {
		if (rating < 0.5) {
			throw new IllegalArgumentException(Constant.RATING_MIN_ERROR);
		} else if (rating > 5) {
			throw new IllegalArgumentException(Constant.RATING_MAX_ERROR);
		}
	}
}
