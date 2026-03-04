package com.library.model;

import java.time.LocalDate;

/**
 * Represents a book reservation
 */
public class Reservation {
    private String reservationId;
    private String memberId;
    private String isbn;
    private LocalDate reservationDate;
    private boolean fulfilled;

    public Reservation(String reservationId, String memberId, String isbn) {
        this.reservationId = reservationId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.reservationDate = LocalDate.now();
        this.fulfilled = false;
    }

    // Getters and Setters
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }
    
    public boolean isFulfilled() { return fulfilled; }
    public void setFulfilled(boolean fulfilled) { this.fulfilled = fulfilled; }

    @Override
    public String toString() {
        return String.format("Reservation [ID: %s, Member: %s, ISBN: %s, Date: %s, Fulfilled: %s]",
                reservationId, memberId, isbn, reservationDate, fulfilled);
    }
}
