package com.alura.literalura.vista;

import com.alura.literalura.dto.BookDto;
import com.alura.literalura.dto.BookProjection;
import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.model.Result;

import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumoApiService;
import com.alura.literalura.service.ConvierteDatos;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LiteraAluraVista {
    private Scanner keyboard;
    private ConsumoApiService consumoApiService;
    private ConvierteDatos convierteDatos;
    private static final String BASE_URL = "https://gutendex.com/books/?";
    private BookRepository bookRepository;


    public LiteraAluraVista(BookRepository bookRepository) {
        this.keyboard = new Scanner(System.in);
        this.consumoApiService = new ConsumoApiService();
        this.convierteDatos = new ConvierteDatos();
        this.bookRepository = bookRepository;

    }

    public void getMenu(){

        int option = -1;
            System.out.println("Bienvenido a LiterAlura\n");

        while (option != 0){

            System.out.println("Por favor elige una opción:\n");

            System.out.println("""
                    1 - Buscar libro por titulo
                    2 - lista de libros registrados
                    3 - lista de libros.
                    4 - buscar autores vivos en un determinado año
                    5 - lista libros por idioma 
                    0 - salir
                
                    """);
            option = keyboard.nextInt();
            keyboard.nextLine();

            switch (option){
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    System.out.println("lista de libros\n");
                    bookList();
                    break;
                case 3:
                    System.out.println("lista");
                    break;
                case 4:
                    System.out.println("lista...");
                    break;
                case 5:
                    System.out.println("listica...");
                    break;
                case 0:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Elija una opcion valida");
                    break;

            }

        }
    }

    private void searchBookByTitle(){
        System.out.println("Escribe el nombre del libro que deseas buscar: ");


        String bookToSeach = keyboard.nextLine();

        String bookResult = consumoApiService.obtenerDatos(BASE_URL + "search=" +bookToSeach.replace(" ", "+"));

        List<Book> searchBooks = convierteDatos.obtenerDatos(bookResult, Result.class).results();
        if (searchBooks.isEmpty()){
            System.out.println("No se han encontrado libro con este nombre");
        }else{
            Book book = searchBooks.get(0);
            saveBook(book);
        }

    }

    private void saveBook(Book book){
        BookEntity bookToSave = new BookEntity();
        AuthorEntity author = new AuthorEntity();

        bookToSave.setTitle(book.title());
        bookToSave.setDownloadCount(book.downloadCount());
        bookToSave.setLanguages(book.languages().get(0));

        author.setName(book.authors().get(0).name());
        author.setBirthYear(book.authors().get(0).birthYear());
        author.setDeathYear(book.authors().get(0).deathYear());
        author.setBook(bookToSave);


        bookToSave.setAuthor(author);
        Optional<BookEntity> isBookStoraged = bookRepository.findByTitleContainsIgnoreCase(bookToSave.getTitle());
        if(isBookStoraged.isPresent()){
            System.out.println("El libro ya se encuentra registrado");
        }else {
            bookRepository.save(bookToSave);
            System.out.println("libro guardado");
        }


    }

    private void bookList(){
        List<BookProjection> books = bookRepository.findBooks();
        if (books.isEmpty()){
            System.out.println("Lista vacia");
        }else {
            List<BookDto> bookDtos = books.stream()
                    .map(b -> new BookDto(b.getTitulo(), b.getDescargas(), b.getLenguaje(), b.getAutor()))
                    .collect(Collectors.toList());
            bookDtos.forEach(System.out::println);
        }
    }
}
