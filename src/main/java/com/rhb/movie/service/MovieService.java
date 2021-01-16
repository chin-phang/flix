package com.rhb.movie.service;

import java.util.List;

import com.rhb.movie.dto.CreateUpdateMovieRequest;
import com.rhb.movie.dto.MovieDto;

public interface MovieService {

	List<MovieDto> getMovies();
	
	MovieDto getMovieById(Long id);
	
	MovieDto createMovie(CreateUpdateMovieRequest createMovie);
	
	MovieDto updateMovie(Long id, CreateUpdateMovieRequest updateMovie);
	
	void deleteMovie(Long id);
}
