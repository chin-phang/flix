package com.rhb.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhb.movie.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>{

}
