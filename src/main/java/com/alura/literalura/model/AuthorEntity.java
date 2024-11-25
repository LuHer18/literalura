package com.alura.literalura.model;


import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(name = "death_year")
    private Integer deathYear;

    private String name;

    @OneToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "------------Autor---------------" + '\n' +
                "Año de nacimiento: " + birthYear + '\n' +
                ", Año de muerte: " + deathYear + '\n' +
                ", Nombre: '" + name + '\'' + '\n' +
                "----------------------------------";
    }
}
