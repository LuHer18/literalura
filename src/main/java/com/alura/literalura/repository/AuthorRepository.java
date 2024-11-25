package com.alura.literalura.repository;

import com.alura.literalura.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository  extends JpaRepository<AuthorEntity, Integer> {

}
