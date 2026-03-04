package com.library.service;

import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.io.*;
import java.time.LocalDate;

/**
 * Service class for data import/export operations
 */
public class DataService {
    private final LibraryRepository repository;

    public DataService(LibraryRepository repository) {
        this.repository = repository;
    }

    public void exportBooksToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ISBN,Title,Author,Publisher,Year,Pages,Category,Type,TotalCopies,AvailableCopies");
            
            for (Book book : repository.getAllBooks()) {
                writer.printf("%s,%s,%s,%s,%d,%d,%s,%s,%d,%d%n",
                        book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublisher(),
                        book.getPublicationYear(), book.getPages(), book.getCategory(),
                        book.getBookType(), book.getTotalCopies(), book.getAvailableCopies());
            }
        }
    }

    public void exportMembersToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("MemberID,Name,Email,Phone,MemberType,MembershipDate,Active,TotalFines");
            
            for (Member member : repository.getAllMembers()) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%.2f%n",
                        member.getMemberId(), member.getName(), member.getEmail(), member.getPhone(),
                        member.getMemberType(), member.getMembershipDate(), member.isActive(),
                        member.getTotalFines());
            }
        }
    }

    public void exportTransactionsToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("TransactionID,MemberID,ISBN,IssueDate,DueDate,ReturnDate,Fine,Returned");
            
            for (Transaction transaction : repository.getAllTransactions()) {
                writer.printf("%s,%s,%s,%s,%s,%s,%.2f,%s%n",
                        transaction.getTransactionId(), transaction.getMemberId(), transaction.getIsbn(),
                        transaction.getIssueDate(), transaction.getDueDate(),
                        transaction.getReturnDate() != null ? transaction.getReturnDate() : "N/A",
                        transaction.getFine(), transaction.isReturned());
            }
        }
    }

    public void backupData(String directory) throws IOException {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String timestamp = LocalDate.now().toString();
        exportBooksToCSV(directory + "/books_" + timestamp + ".csv");
        exportMembersToCSV(directory + "/members_" + timestamp + ".csv");
        exportTransactionsToCSV(directory + "/transactions_" + timestamp + ".csv");
    }
}
