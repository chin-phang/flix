package com.rhb.movie.dto;

import java.io.Serializable;

import com.rhb.movie.entity.Movie;

public class MovieDto implements Serializable {

	private static final long serialVersionUID = -6907574568628551785L;
	
	private Long id;
	
	private String title;
	
	private String category;
	
	private Double rating;
	
	public MovieDto() {
		// Empty constructor
	}
	
	public MovieDto(Movie movie) {
		this.id = movie.getId();
		this.title = movie.getTitle();
		this.category = movie.getCategory();
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "MovieDto [id=" + id + ", title=" + title + ", category=" + category + ", rating=" + rating + "]";
	}
}
