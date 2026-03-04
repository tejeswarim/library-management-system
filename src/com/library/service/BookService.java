package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for book management operations
 */
public class BookService {
    private final LibraryRepository repository;

    public BookService(LibraryRepository repository) {
        this.repository = repository;
    }

    public void addBook(Book book) throws LibraryException {
        if (repository.bookExists(book.getIsbn())) {
            throw new LibraryException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        repository.addBook(book);
    }

    public void updateBookInfo(String isbn, String title, String author, String publisher) throws LibraryException {
        Book book = repository.getBook(isbn);
        if (book == null) {
            throw new LibraryException("Book not found");
        }
        if (title != null) book.setTitle(title);
        if (author != null) book.setAuthor(author);
        if (publisher != null) book.setPublisher(publisher);
    }

    public void updateBookStock(String isbn, int additionalCopies) throws LibraryException {
        Book book = repository.getBook(isbn);
        if (book == null) {
            throw new LibraryException("Book not found");
        }
        book.setTotalCopies(book.getTotalCopies() + additionalCopies);
        book.setAvailableCopies(book.getAvailableCopies() + additionalCopies);
    }

    public Book getBookDetails(String isbn) throws LibraryException {
        Book book = repository.getBook(isbn);
        if (book == null) {
            throw new LibraryException("Book not found");
        }
        return book;
    }

    public List<Book> searchByTitle(String title) {
        return repository.getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return repository.getAllBooks().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByCategory(BookCategory category) {
        return repository.getAllBooks().stream()
                .filter(book -> book.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Book> searchByISBN(String isbn) {
        return repository.getAllBooks().stream()
                .filter(book -> book.getIsbn().contains(isbn))
                .collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {
        return repository.getAllBooks().stream()
                .filter(book -> book.getAvailableCopies() > 0)
                .collect(Collectors.toList());
    }

    public List<Book> searchByCategoryAndYear(BookCategory category, int year) {
        return repository.getAllBooks().stream()
                .filter(book -> book.getCategory() == category && book.getPublicationYear() == year)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(repository.getAllBooks());
    }
}
