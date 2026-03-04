package com.library.model;

/**
 * Represents an audio book in the library
 */
public class AudioBook extends Book {
    private String narrator;
    private int durationMinutes;
    private String audioFormat;

    public AudioBook(String isbn, String title, String author, String publisher,
                    int publicationYear, int pages, BookCategory category,
                    int totalCopies, String narrator, int durationMinutes, String audioFormat) {
        super(isbn, title, author, publisher, publicationYear, pages, category, totalCopies);
        this.narrator = narrator;
        this.durationMinutes = durationMinutes;
        this.audioFormat = audioFormat;
    }

    @Override
    public String getBookType() {
        return "Audio Book";
    }

    public String getNarrator() { return narrator; }
    public void setNarrator(String narrator) { this.narrator = narrator; }
    
    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public String getAudioFormat() { return audioFormat; }
    public void setAudioFormat(String audioFormat) { this.audioFormat = audioFormat; }
}
