package com.rhb.movie.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rhb.movie.dto.CreateUpdateMovieRequest;
import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.entity.Category;
import com.rhb.movie.entity.Movie;
import com.rhb.movie.repository.CategoryRepository;
import com.rhb.movie.repository.MovieRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

	@InjectMocks
	MovieServiceImpl movieService;
	
	@Mock
	MovieRepository movieRepository;
	
	@Mock
	CategoryRepository categoryRepository;

	@Test
	void testGetMovies() {
		List<Movie> entityList = new ArrayList<>();
		entityList.add(fakeMovie(2L, "Movie 2"));
		entityList.add(fakeMovie(3L, "Movie 3"));
		
		when(movieRepository.findAll()).thenReturn(entityList);
		
		List<MovieDto> movieList = movieService.getMovies();
		
		List<MovieDto> dtoList = entityList.stream().map(MovieDto::new).collect(Collectors.toList());
		
		assertNotNull(movieList);
		assertThat(dtoList.equals(movieList));
	}

	@Test
	void testGetMovieById() {
		Movie movie = fakeMovie(1L, "A movie");
		
		when(movieRepository.findById(anyLong())).thenReturn(Optional.of(movie));
		
		MovieDto movieDto = movieService.getMovieById(1L);
		
		Set<String> categoriesAsString = new HashSet<>(Arrays.asList(new String[]{ "Action", "Science fiction" }));
		
		assertNotNull(movieDto);
		assertEquals(1L, movieDto.getId());
		assertEquals("A movie", movieDto.getTitle());
		assertEquals(0.5, movieDto.getRating());
		assertEquals(categoriesAsString, movieDto.getCategories());
	}
	
	@Test
	void testCreateMovie() {
		Movie movie = new Movie();
		movie.setId(4L);
		movie.setTitle("New movie");
		movie.setRating(5.0);
		Set<Category> movieCategories = fakeCategories(new String[]{ "Drama", "Comedy" });
		movie.setCategories(movieCategories);
		
		Category dramaCategory = new Category();
		dramaCategory.setName("Drama");
		Category comedyCategory = new Category();
		comedyCategory.setName("Comedy");
		
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);
		when(categoryRepository.findById("Drama")).thenReturn(Optional.of(dramaCategory));
		when(categoryRepository.findById("Comedy")).thenReturn(Optional.of(comedyCategory));
		
		CreateUpdateMovieRequest createMovie = new CreateUpdateMovieRequest();
		createMovie.setTitle("New movie");
		createMovie.setRating(5.0);
		Set<String> createCategories = new HashSet<>(Arrays.asList(new String[]{ "Drama", "Comedy" }));
		createMovie.setCategories(createCategories);
		
		MovieDto movieDto = movieService.createMovie(createMovie);
		
		assertNotNull(movieDto);
		assertEquals(4L, movieDto.getId());
		assertEquals("New movie", movieDto.getTitle());
		assertEquals(5.0, movieDto.getRating());
		assertEquals(createCategories, movieDto.getCategories());
	}
	
	private Movie fakeMovie(Long id, String title) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setRating(0.5);
		Set<Category> categories = fakeCategories(new String[]{"Action", "Science fiction"});
		movie.setCategories(categories);
		
		return movie;
	}
	
	private Set<Category> fakeCategories(String[] stringArray) {
		List<String> categoryList = Arrays.asList(stringArray);
		Set<Category> categories = categoryList.stream().map(string -> { 
			Category cat = new Category();
			cat.setName(string);
			return cat;
		}).collect(Collectors.toSet());
		
		return categories;
	}
}
