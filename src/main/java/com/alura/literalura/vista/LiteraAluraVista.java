package com.alura.literalura.vista;

import com.alura.literalura.dto.BookDto;
import com.alura.literalura.dto.BookProjection;
import com.alura.literalura.model.AuthorEntity;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookEntity;
import com.alura.literalura.model.Result;

import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumoApiService;
import com.alura.literalura.service.ConvierteDatos;


import java.util.*;
import java.util.stream.Collectors;

public class LiteraAluraVista {
    private Scanner keyboard;
    private ConsumoApiService consumoApiService;
    private ConvierteDatos convierteDatos;
    private static final String BASE_URL = "https://gutendex.com/books/?";
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;


    public LiteraAluraVista(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.keyboard = new Scanner(System.in);
        this.consumoApiService = new ConsumoApiService();
        this.convierteDatos = new ConvierteDatos();
        this.bookRepository = bookRepository;
        this.authorRepository =authorRepository;

    }

    public void getMenu(){

        int option = -1;
            System.out.println("Bienvenido a LiterAlura\n");

        while (option != 0){

            System.out.println("Por favor elige una opción:\n");

            System.out.println("""
                    1 - Buscar libro por titulo
                    2 - lista de libros registrados
                    3 - lista de autores.
                    4 - buscar autores vivos en un determinado año
                    5 - lista libros por idioma 
                    0 - salir
                
                    """);
            while (true) {
                try {
                    System.out.print("Introduce una opción: ");
                    option = keyboard.nextInt();
                    keyboard.nextLine();
                    if (option >= 0 && option <= 5) {
                        break;
                    } else {
                        System.out.println("Elija una opción válida (0-5).");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor ingresa un número entero.");
                    keyboard.nextLine();
                }
            }

            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    System.out.println("Lista de libros:\n");
                    bookList();
                    break;
                case 3:
                    System.out.println("Lista de autores:\n");
                    authorsList();
                    break;
                case 4:
                    System.out.println("Lista de autores vivos en un determinado año:\n");
                    authorListByYear();
                    break;
                case 5:
                    System.out.println("Lista de libros por idioma:\n");
                    booksByLanguages();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Elija una opción válida.");
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
        AuthorEntity author = authorRepository.findByNameContainsIgnoreCase(book.authors().get(0).name()).orElse(null);

        if (author == null){
            author = new AuthorEntity();
            author.setName(book.authors().get(0).name());
            author.setBirthYear(book.authors().get(0).birthYear());
            author.setDeathYear(book.authors().get(0).deathYear());
        }
        if( bookRepository.findByTitleContainsIgnoreCase(book.title()).isPresent()){
            System.out.println("Este libro ya ha sido registrado");
        }else{
            bookToSave.setTitle(book.title());
            bookToSave.setDownloadCount(book.downloadCount());
            bookToSave.setLanguages(book.languages().get(0));
            bookToSave.setAuthor(author);
            if (author.getBooks() == null){
                author.setBooks(new ArrayList<>());
            }
            author.getBooks().add(bookToSave);
            authorRepository.save(author);
            System.out.println("Registro exitoso");

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

    private void authorsList(){
        List<AuthorEntity> authorList = authorRepository.findAll();
        authorList.forEach(System.out::println);
    }

    private void authorListByYear(){
        System.out.println("Por favor escribe un año para validar si hay actores que vivieron durante este periodo: ");
        Integer year = null;

        while (year == null){
            try{
                year = keyboard.nextInt();

                List<AuthorEntity> authorByYearList = authorRepository.findAuthorsWithinYearRange(year);

                if (authorByYearList.size() < 1){
                    System.out.println("No se han registrado autores que hayan vivido en este periodo: " + year);
                }else {
                    System.out.println("Se han encontrado autores en el año: " + year + '\n');
                    System.out.println(authorByYearList);
                }
            }catch (InputMismatchException e){
                System.out.println("Escribe un año valido");
                keyboard.nextLine();
            }

        }

    }

    private void booksByLanguages(){
        System.out.println("Escribe el idioma por el que quieres filtrar los libros: ");
        String bookByLanguage = keyboard.nextLine();

        List<BookEntity> booksLanguage  = bookRepository.findByLanguagesContainsIgnoreCase(bookByLanguage);

        if (booksLanguage.size() < 1){
            System.out.println("No se ha registrado un libro con este idioma");
        }else{
            System.out.println("Libros filtrados por el idioma: " + bookByLanguage + '\n');
            System.out.println(booksLanguage);
        }



        }

}
