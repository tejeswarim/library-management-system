package com.library.service;

import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for analytics and reporting operations
 */
public class AnalyticsService {
    private final LibraryRepository repository;

    public AnalyticsService(LibraryRepository repository) {
        this.repository = repository;
    }

    public Map<BookCategory, Long> getBorrowingTrendsByCategory() {
        return repository.getAllTransactions().stream()
                .map(t -> repository.getBook(t.getIsbn()))
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Book::getCategory, Collectors.counting()));
    }

    public List<Member> getTopBorrowers(int limit) {
        Map<String, Long> borrowCounts = repository.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getMemberId, Collectors.counting()));

        return borrowCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> repository.getMember(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Member> getDefaulters() {
        return repository.getAllMembers().stream()
                .filter(member -> member.getTotalFines() > 0)
                .sorted(Comparator.comparingDouble(Member::getTotalFines).reversed())
                .collect(Collectors.toList());
    }

    public double getTotalFineCollection() {
        return repository.getAllTransactions().stream()
                .filter(Transaction::isReturned)
                .mapToDouble(Transaction::getFine)
                .sum();
    }

    public Map<String, Long> getMonthlyBorrowingTrends() {
        return repository.getAllTransactions().stream()
                .collect(Collectors.groupingBy(
                        t -> t.getIssueDate().getYear() + "-" + t.getIssueDate().getMonthValue(),
                        Collectors.counting()
                ));
    }

    public List<Book> getLeastBorrowedBooks(int limit) {
        Map<String, Long> borrowCounts = repository.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getIsbn, Collectors.counting()));

        Set<String> borrowedIsbns = borrowCounts.keySet();

        return repository.getAllBooks().stream()
                .filter(book -> !borrowedIsbns.contains(book.getIsbn()) || 
                               borrowCounts.get(book.getIsbn()) < 5)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public double getAverageBorrowingDuration(BookCategory category) {
        return repository.getAllTransactions().stream()
                .filter(Transaction::isReturned)
                .filter(t -> {
                    Book book = repository.getBook(t.getIsbn());
                    return book != null && book.getCategory() == category;
                })
                .mapToLong(t -> java.time.temporal.ChronoUnit.DAYS.between(
                        t.getIssueDate(), t.getReturnDate()))
                .average()
                .orElse(0.0);
    }

    public long getActiveMembersCount() {
        return repository.getAllMembers().stream()
                .filter(Member::isActive)
                .count();
    }

    public Map<MemberType, Long> getMemberDistribution() {
        return repository.getAllMembers().stream()
                .collect(Collectors.groupingBy(Member::getMemberType, Collectors.counting()));
    }

    public List<Transaction> getOverdueReport() {
        return repository.getAllTransactions().stream()
                .filter(Transaction::isOverdue)
                .sorted(Comparator.comparing(Transaction::getDueDate))
                .collect(Collectors.toList());
    }

    public double getBookTurnoverRate(String isbn) {
        Book book = repository.getBook(isbn);
        if (book == null) return 0.0;

        long borrowCount = repository.getAllTransactions().stream()
                .filter(t -> t.getIsbn().equals(isbn))
                .count();

        long daysSinceAdded = java.time.temporal.ChronoUnit.DAYS.between(
                book.getAddedDate(), LocalDate.now());

        return daysSinceAdded > 0 ? (double) borrowCount / daysSinceAdded * 30 : 0.0;
    }
}
