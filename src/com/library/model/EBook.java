package com.library.model;

/**
 * Represents an electronic book in the library
 */
public class EBook extends Book {
    private String downloadUrl;
    private double fileSizeMB;
    private String format;

    public EBook(String isbn, String title, String author, String publisher,
                int publicationYear, int pages, BookCategory category,
                int totalCopies, String downloadUrl, double fileSizeMB, String format) {
        super(isbn, title, author, publisher, publicationYear, pages, category, totalCopies);
        this.downloadUrl = downloadUrl;
        this.fileSizeMB = fileSizeMB;
        this.format = format;
    }

    @Override
    public String getBookType() {
        return "E-Book";
    }

    public String getDownloadUrl() { return downloadUrl; }
    public void setDownloadUrl(String downloadUrl) { this.downloadUrl = downloadUrl; }
    
    public double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(double fileSizeMB) { this.fileSizeMB = fileSizeMB; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}
