package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a book borrowing transaction
 */
public class Transaction {
    private String transactionId;
    private String memberId;
    private String isbn;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private boolean returned;
    private int renewalCount;

    public Transaction(String transactionId, String memberId, String isbn, 
                      LocalDate issueDate, LocalDate dueDate) {
        this.transactionId = transactionId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.fine = 0.0;
        this.returned = false;
        this.renewalCount = 0;
    }

    public long getDaysOverdue() {
        LocalDate checkDate = returned ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, checkDate);
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }

    // Getters and Setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }
    
    public boolean isReturned() { return returned; }
    public void setReturned(boolean returned) { this.returned = returned; }
    
    public int getRenewalCount() { return renewalCount; }
    public void setRenewalCount(int renewalCount) { this.renewalCount = renewalCount; }

    @Override
    public String toString() {
        return String.format("Transaction [ID: %s, Member: %s, ISBN: %s, Due: %s, Returned: %s]",
                transactionId, memberId, isbn, dueDate, returned);
    }
}
