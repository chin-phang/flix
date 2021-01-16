package com.rhb.movie.dto;

import java.io.Serializable;
import java.util.Set;

public class CreateUpdateMovieRequest implements Serializable {

	private static final long serialVersionUID = -3431880760600057662L;

	private String title;
	private Set<String> categories;
	private Double rating;
	
	public CreateUpdateMovieRequest() {
		// Empty constructor
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
		return "CreateUpdateMovieRequest [title=" + title + ", categories=" + categories + ", rating=" + rating + "]";
	}
}
