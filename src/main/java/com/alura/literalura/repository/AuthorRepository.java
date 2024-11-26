package com.alura.literalura.repository;

import com.alura.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository  extends JpaRepository<AuthorEntity, Integer> {
    Optional<AuthorEntity> findByNameContainsIgnoreCase(String name);
}
