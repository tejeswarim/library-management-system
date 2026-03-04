package com.library.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Abstract base class representing a book in the library system
 */
public abstract class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int pages;
    private BookCategory category;
    private int totalCopies;
    private int availableCopies;
    private int reservedCopies;
    private LocalDate addedDate;

    public Book(String isbn, String title, String author, String publisher, 
                int publicationYear, int pages, BookCategory category, int totalCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.pages = pages;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.reservedCopies = 0;
        this.addedDate = LocalDate.now();
    }

    public abstract String getBookType();

    // Getters and Setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
    
    public BookCategory getCategory() { return category; }
    public void setCategory(BookCategory category) { this.category = category; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
    
    public int getReservedCopies() { return reservedCopies; }
    public void setReservedCopies(int reservedCopies) { this.reservedCopies = reservedCopies; }
    
    public LocalDate getAddedDate() { return addedDate; }
    public void setAddedDate(LocalDate addedDate) { this.addedDate = addedDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return String.format("%s [ISBN: %s, Title: %s, Author: %s, Category: %s, Available: %d/%d]",
                getBookType(), isbn, title, author, category, availableCopies, totalCopies);
    }
}
