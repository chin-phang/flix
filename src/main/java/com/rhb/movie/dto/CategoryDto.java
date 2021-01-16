package com.rhb.movie.dto;

import java.io.Serializable;

import com.rhb.movie.entity.Category;

public class CategoryDto implements Serializable {

	private static final long serialVersionUID = -3336449919840466258L;

	private String name;
	
	public CategoryDto() {
		// Empty constructor
	}

	public CategoryDto(Category category) {
		this.name = category.getName();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategoryDto [name=" + name + "]";
	}
}
