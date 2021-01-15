package com.rhb.movie.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhb.movie.constant.Constant;
import com.rhb.movie.dto.MovieDto;
import com.rhb.movie.service.MovieService;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@GetMapping
	public ResponseEntity<List<MovieDto>> getMovies() {
		List<MovieDto> result = movieService.getMovies();
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable Long id) {
		try {
			MovieDto result = movieService.getMovieById(id);
			return ResponseEntity.ok(result);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> createMovie(@RequestBody MovieDto movieDto) {
		try {
			movieService.createMovie(movieDto);
			return ResponseEntity.ok(Constant.MOVIE_CREATE_SUCCESS);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
		try {
			movieService.updateMovie(id, movieDto);
			return ResponseEntity.ok(Constant.MOVIE_UPDATE_SUCCESS);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getLocalizedMessage());
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
		try {
			movieService.deleteMovie(id);
			return ResponseEntity.ok(Constant.MOVIE_DELETE_SUCCESS);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
		}
	}
}
