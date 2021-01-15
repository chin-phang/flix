package com.rhb.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhb.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
