package com.rhb.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhb.movie.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
