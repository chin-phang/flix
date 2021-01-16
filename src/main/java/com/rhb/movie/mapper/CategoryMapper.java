package com.rhb.movie.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.rhb.movie.dto.CategoryDto;
import com.rhb.movie.entity.Category;

@Service
public class CategoryMapper {

	public static CategoryDto toDto(Category entity) {
		return new CategoryDto(entity);
	}
	
	public static Category toEntity(CategoryDto dto) {
		if (dto == null) {
			return null;
		} else {
			Category entity = new Category();
			BeanUtils.copyProperties(dto, entity);
			return entity;
		}
	}
}
