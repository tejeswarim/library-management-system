package com.library.service;

import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for book recommendation operations
 */
public class RecommendationService {
    private final LibraryRepository repository;

    public RecommendationService(LibraryRepository repository) {
        this.repository = repository;
    }

    public List<Book> getRecommendationsForMember(String memberId) {
        Member member = repository.getMember(memberId);
        if (member == null || member.getBorrowingHistory().isEmpty()) {
            return getPopularBooks(5);
        }

        // Get categories from member's history
        Set<BookCategory> preferredCategories = member.getBorrowingHistory().stream()
                .map(isbn -> repository.getBook(isbn))
                .filter(Objects::nonNull)
                .map(Book::getCategory)
                .collect(Collectors.toSet());

        // Recommend books from preferred categories that member hasn't borrowed
        return repository.getAllBooks().stream()
                .filter(book -> preferredCategories.contains(book.getCategory()))
                .filter(book -> !member.getBorrowingHistory().contains(book.getIsbn()))
                .filter(book -> book.getAvailableCopies() > 0)
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Book> getPopularBooks(int limit) {
        Map<String, Long> borrowCounts = repository.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getIsbn, Collectors.counting()));

        return borrowCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> repository.getBook(entry.getKey()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Book> getPopularBooksByCategory(BookCategory category, int limit) {
        Map<String, Long> borrowCounts = repository.getAllTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getIsbn, Collectors.counting()));

        return borrowCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(entry -> repository.getBook(entry.getKey()))
                .filter(Objects::nonNull)
                .filter(book -> book.getCategory() == category)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<Book> getSimilarMemberRecommendations(String memberId) {
        Member member = repository.getMember(memberId);
        if (member == null) return Collections.emptyList();

        // Find members with similar borrowing patterns
        List<Member> similarMembers = repository.getAllMembers().stream()
                .filter(m -> !m.getMemberId().equals(memberId))
                .filter(m -> hasCommonBooks(member, m))
                .collect(Collectors.toList());

        // Get books borrowed by similar members
        return similarMembers.stream()
                .flatMap(m -> m.getBorrowingHistory().stream())
                .distinct()
                .filter(isbn -> !member.getBorrowingHistory().contains(isbn))
                .map(isbn -> repository.getBook(isbn))
                .filter(Objects::nonNull)
                .filter(book -> book.getAvailableCopies() > 0)
                .limit(10)
                .collect(Collectors.toList());
    }

    private boolean hasCommonBooks(Member m1, Member m2) {
        Set<String> books1 = new HashSet<>(m1.getBorrowingHistory());
        Set<String> books2 = new HashSet<>(m2.getBorrowingHistory());
        books1.retainAll(books2);
        return books1.size() >= 2;
    }
}
