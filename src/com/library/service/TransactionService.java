package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for transaction management operations
 */
public class TransactionService {
    private final LibraryRepository repository;
    private final MemberService memberService;

    public TransactionService(LibraryRepository repository, MemberService memberService) {
        this.repository = repository;
        this.memberService = memberService;
    }

    public String issueBook(String memberId, String isbn) throws LibraryException {
        Member member = repository.getMember(memberId);
        Book book = repository.getBook(isbn);

        // Validation checks
        if (member == null) throw new LibraryException("Member not found");
        if (book == null) throw new LibraryException("Book not found");
        if (!member.isActive()) throw new LibraryException("Member account is inactive");
        if (book.getAvailableCopies() <= 0) throw new LibraryException("Book not available");
        
        long currentBorrowedCount = repository.getAllTransactions().stream()
                .filter(t -> t.getMemberId().equals(memberId) && !t.isReturned())
                .count();
        
        if (currentBorrowedCount >= member.getMemberType().getBorrowingLimit()) {
            throw new LibraryException("Borrowing limit exceeded");
        }

        if (member.getTotalFines() > 0) {
            throw new LibraryException("Cannot issue book. Member has unpaid fines: $" + member.getTotalFines());
        }

        // Check if book is reserved by someone else
        Optional<Reservation> reservation = repository.getAllReservations().stream()
                .filter(r -> r.getIsbn().equals(isbn) && !r.isFulfilled())
                .findFirst();
        
        if (reservation.isPresent() && !reservation.get().getMemberId().equals(memberId)) {
            throw new LibraryException("Book is reserved by another member");
        }

        // Create transaction
        String transactionId = repository.generateTransactionId();
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(member.getMemberType().getBorrowingDays());
        
        Transaction transaction = new Transaction(transactionId, memberId, isbn, issueDate, dueDate);
        repository.addTransaction(transaction);

        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        // Add to member's borrowing history
        member.addToBorrowingHistory(isbn);

        // Fulfill reservation if exists
        reservation.ifPresent(r -> r.setFulfilled(true));

        return transactionId;
    }

    public double returnBook(String transactionId) throws LibraryException {
        Transaction transaction = repository.getTransaction(transactionId);
        if (transaction == null) throw new LibraryException("Transaction not found");
        if (transaction.isReturned()) throw new LibraryException("Book already returned");

        Book book = repository.getBook(transaction.getIsbn());
        Member member = repository.getMember(transaction.getMemberId());

        transaction.setReturnDate(LocalDate.now());
        transaction.setReturned(true);

        // Calculate fine
        double fine = calculateFine(transaction, member.getMemberType());
        transaction.setFine(fine);

        if (fine > 0) {
            memberService.addFine(member.getMemberId(), fine);
        }

        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return fine;
    }

    public void renewBook(String transactionId) throws LibraryException {
        Transaction transaction = repository.getTransaction(transactionId);
        if (transaction == null) throw new LibraryException("Transaction not found");
        if (transaction.isReturned()) throw new LibraryException("Book already returned");
        if (transaction.getRenewalCount() >= 2) throw new LibraryException("Maximum renewals reached");
        if (transaction.isOverdue()) throw new LibraryException("Cannot renew overdue book");

        Member member = repository.getMember(transaction.getMemberId());
        transaction.setDueDate(transaction.getDueDate().plusDays(member.getMemberType().getBorrowingDays()));
        transaction.setRenewalCount(transaction.getRenewalCount() + 1);
    }

    public String reserveBook(String memberId, String isbn) throws LibraryException {
        Member member = repository.getMember(memberId);
        Book book = repository.getBook(isbn);

        if (member == null) throw new LibraryException("Member not found");
        if (book == null) throw new LibraryException("Book not found");
        if (!member.isActive()) throw new LibraryException("Member account is inactive");

        String reservationId = repository.generateReservationId();
        Reservation reservation = new Reservation(reservationId, memberId, isbn);
        repository.addReservation(reservation);
        book.setReservedCopies(book.getReservedCopies() + 1);

        return reservationId;
    }

    public List<Transaction> getOverdueTransactions() {
        return repository.getAllTransactions().stream()
                .filter(Transaction::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Transaction> getMemberTransactions(String memberId) {
        return repository.getAllTransactions().stream()
                .filter(t -> t.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }

    public List<Transaction> getActiveTransactions(String memberId) {
        return repository.getAllTransactions().stream()
                .filter(t -> t.getMemberId().equals(memberId) && !t.isReturned())
                .collect(Collectors.toList());
    }

    private double calculateFine(Transaction transaction, MemberType memberType) {
        long daysOverdue = transaction.getDaysOverdue();
        if (daysOverdue <= 0) return 0.0;
        
        double fine = daysOverdue * memberType.getFineRatePerDay();
        double maxFine = 50.0; // Maximum fine limit
        return Math.min(fine, maxFine);
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(repository.getAllTransactions());
    }
}
