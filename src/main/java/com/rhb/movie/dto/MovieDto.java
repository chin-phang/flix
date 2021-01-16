package com.rhb.movie.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import com.rhb.movie.entity.Category;
import com.rhb.movie.entity.Movie;

public class MovieDto implements Serializable {

	private static final long serialVersionUID = -6907574568628551785L;
	
	private Long id;
	private String title;
	private Set<String> categories;
	private Double rating;
	
	public MovieDto() {
		// Empty constructor
	}
	
	public MovieDto(Movie movie) {
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.categories = movie.getCategories().stream().map(Category::getName).collect(Collectors.toSet());
		this.rating = movie.getRating();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", title=" + title + ", categories=" + categories + ", rating=" + rating + "]";
	}
}
