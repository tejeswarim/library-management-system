package com.library.model;

/**
 * Represents a physical book in the library
 */
public class PhysicalBook extends Book {
    private String shelfLocation;
    private String condition;

    public PhysicalBook(String isbn, String title, String author, String publisher,
                       int publicationYear, int pages, BookCategory category, 
                       int totalCopies, String shelfLocation) {
        super(isbn, title, author, publisher, publicationYear, pages, category, totalCopies);
        this.shelfLocation = shelfLocation;
        this.condition = "Good";
    }

    @Override
    public String getBookType() {
        return "Physical Book";
    }

    public String getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(String shelfLocation) { this.shelfLocation = shelfLocation; }
    
    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
