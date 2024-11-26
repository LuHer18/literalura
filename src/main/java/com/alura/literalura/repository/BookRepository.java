package com.alura.literalura.repository;

import com.alura.literalura.dto.BookDto;
import com.alura.literalura.dto.BookProjection;
import com.alura.literalura.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity>  findByTitleContainsIgnoreCase(String title);

    @Query(value = """
            SELECT  b.title as titulo, b.download_count as descargas, b.languages as lenguaje, a.name as autor FROM books b INNER JOIN authors a ON b.author_id = a.id""", nativeQuery = true)
    List<BookProjection> findBooks();
}
