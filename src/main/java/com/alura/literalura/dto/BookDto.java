package com.alura.literalura.dto;

public class BookDto {
    private String titulo;
    private Integer descargas;
    private String lenguaje;
    private String autor;

    public BookDto() {
    }

    public BookDto(String titulo, Integer descargas, String lenguaje, String autor) {
        this.titulo = titulo;
        this.descargas = descargas;
        this.lenguaje = lenguaje;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "-------------------Libro---------------------\n" +
                "Titulo: " + titulo + '\n' +
                "Descargas: " + descargas + '\n' +
                "Lenguaje: " + lenguaje + '\n' +
                "Autor: " + autor + '\n' +
                "----------------------------------------------\n";
    }
}
