package com.rhb.movie.service;

import java.util.List;

import com.rhb.movie.dto.MovieDto;

public interface MovieService {

	List<MovieDto> getMovies();
	
	MovieDto getMovieById(Long id);
	
	MovieDto createMovie(MovieDto movieDto);
	
	MovieDto updateMovie(Long id, MovieDto movieDto);
	
	void deleteMovie(Long id);
}
