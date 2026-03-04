package com.library.repository;

import com.library.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository class for managing library data
 */
public class LibraryRepository {
    private final Map<String, Book> books;
    private final Map<String, Member> members;
    private final Map<String, Transaction> transactions;
    private final Map<String, Reservation> reservations;
    private int transactionCounter;
    private int reservationCounter;

    public LibraryRepository() {
        this.books = new ConcurrentHashMap<>();
        this.members = new ConcurrentHashMap<>();
        this.transactions = new ConcurrentHashMap<>();
        this.reservations = new ConcurrentHashMap<>();
        this.transactionCounter = 1;
        this.reservationCounter = 1;
    }

    // Book operations
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    public Book getBook(String isbn) {
        return books.get(isbn);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    public boolean bookExists(String isbn) {
        return books.containsKey(isbn);
    }

    // Member operations
    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    public Collection<Member> getAllMembers() {
        return members.values();
    }

    public boolean memberExists(String memberId) {
        return members.containsKey(memberId);
    }

    // Transaction operations
    public void addTransaction(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    public Transaction getTransaction(String transactionId) {
        return transactions.get(transactionId);
    }

    public Collection<Transaction> getAllTransactions() {
        return transactions.values();
    }

    public String generateTransactionId() {
        return "TXN" + String.format("%05d", transactionCounter++);
    }

    // Reservation operations
    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getReservationId(), reservation);
    }

    public Reservation getReservation(String reservationId) {
        return reservations.get(reservationId);
    }

    public Collection<Reservation> getAllReservations() {
        return reservations.values();
    }

    public String generateReservationId() {
        return "RES" + String.format("%05d", reservationCounter++);
    }
}
