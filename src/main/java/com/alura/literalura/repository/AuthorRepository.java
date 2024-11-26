package com.alura.literalura.repository;

import com.alura.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository  extends JpaRepository<AuthorEntity, Integer> {
    Optional<AuthorEntity> findByNameContainsIgnoreCase(String name);

    @Query("SELECT a FROM AuthorEntity a WHERE a.birthYear <= :year AND a.deathYear >= :year")
    List<AuthorEntity> findAuthorsWithinYearRange(@Param("year") Integer year);

}
