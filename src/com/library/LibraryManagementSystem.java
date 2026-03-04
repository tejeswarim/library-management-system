package com.library;

import com.library.exception.LibraryException;
import com.library.model.*;
import com.library.repository.LibraryRepository;
import com.library.service.*;
import java.util.*;

/**
 * Main class for Library Management System
 */
public class LibraryManagementSystem {
    private final Scanner scanner;
    private final LibraryRepository repository;
    private final BookService bookService;
    private final MemberService memberService;
    private final TransactionService transactionService;
    private final RecommendationService recommendationService;
    private final AnalyticsService analyticsService;
    private final DataService dataService;

    public LibraryManagementSystem() {
        this.scanner = new Scanner(System.in);
        this.repository = new LibraryRepository();
        this.bookService = new BookService(repository);
        this.memberService = new MemberService(repository);
        this.transactionService = new TransactionService(repository, memberService);
        this.recommendationService = new RecommendationService(repository);
        this.analyticsService = new AnalyticsService(repository);
        this.dataService = new DataService(repository);
        
        loadSampleData();
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.run();
    }

    public void run() {
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            try {
                switch (choice) {
                    case 1: handleBookOperations(); break;
                    case 2: handleMemberOperations(); break;
                    case 3: handleBorrowingOperations(); break;
                    case 4: handleAdvancedFeatures(); break;
                    case 5: handleReportsAndAnalytics(); break;
                    case 6: handleSystemOperations(); break;
                    case 0:
                        System.out.println("Thank you for using Library Management System!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
        System.out.println("1. Book Operations");
        System.out.println("2. Member Operations");
        System.out.println("3. Borrowing Operations");
        System.out.println("4. Advanced Features");
        System.out.println("5. Reports & Analytics");
        System.out.println("6. System Operations");
        System.out.println("0. Exit");
    }

    private void handleBookOperations() throws LibraryException {
        System.out.println("\n--- Book Operations ---");
        System.out.println("1. Add New Book");
        System.out.println("2. Update Book Info");
        System.out.println("3. Search Books");
        System.out.println("4. View Book Details");
        System.out.println("5. Check Availability");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: addNewBook(); break;
            case 2: updateBookInfo(); break;
            case 3: searchBooks(); break;
            case 4: viewBookDetails(); break;
            case 5: checkAvailability(); break;
        }
    }

    private void addNewBook() throws LibraryException {
        System.out.println("\n--- Add New Book ---");
        System.out.println("Book Type: 1. Physical  2. E-Book  3. Audio Book");
        int type = getIntInput("Enter type: ");
        
        String isbn = getStringInput("ISBN: ");
        String title = getStringInput("Title: ");
        String author = getStringInput("Author: ");
        String publisher = getStringInput("Publisher: ");
        int year = getIntInput("Publication Year: ");
        int pages = getIntInput("Pages: ");
        
        System.out.println("Categories: " + Arrays.toString(BookCategory.values()));
        String categoryStr = getStringInput("Category: ").toUpperCase();
        BookCategory category = BookCategory.valueOf(categoryStr);
        
        int copies = getIntInput("Total Copies: ");
        
        Book book;
        switch (type) {
            case 1:
                String location = getStringInput("Shelf Location: ");
                book = new PhysicalBook(isbn, title, author, publisher, year, pages, category, copies, location);
                break;
            case 2:
                String url = getStringInput("Download URL: ");
                double size = getDoubleInput("File Size (MB): ");
                String format = getStringInput("Format: ");
                book = new EBook(isbn, title, author, publisher, year, pages, category, copies, url, size, format);
                break;
            case 3:
                String narrator = getStringInput("Narrator: ");
                int duration = getIntInput("Duration (minutes): ");
                String audioFormat = getStringInput("Audio Format: ");
                book = new AudioBook(isbn, title, author, publisher, year, pages, category, copies, narrator, duration, audioFormat);
                break;
            default:
                System.out.println("Invalid type");
                return;
        }
        
        bookService.addBook(book);
        System.out.println("Book added successfully!");
    }

    private void updateBookInfo() throws LibraryException {
        String isbn = getStringInput("Enter ISBN: ");
        String title = getStringInput("New Title (or press Enter to skip): ");
        String author = getStringInput("New Author (or press Enter to skip): ");
        String publisher = getStringInput("New Publisher (or press Enter to skip): ");
        
        bookService.updateBookInfo(isbn, 
                title.isEmpty() ? null : title,
                author.isEmpty() ? null : author,
                publisher.isEmpty() ? null : publisher);
        System.out.println("Book updated successfully!");
    }

    private void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. By Title  2. By Author  3. By Category  4. By ISBN");
        int choice = getIntInput("Enter choice: ");
        
        List<Book> results = new ArrayList<>();
        switch (choice) {
            case 1:
                String title = getStringInput("Enter title: ");
                results = bookService.searchByTitle(title);
                break;
            case 2:
                String author = getStringInput("Enter author: ");
                results = bookService.searchByAuthor(author);
                break;
            case 3:
                System.out.println("Categories: " + Arrays.toString(BookCategory.values()));
                String cat = getStringInput("Enter category: ").toUpperCase();
                results = bookService.searchByCategory(BookCategory.valueOf(cat));
                break;
            case 4:
                String isbn = getStringInput("Enter ISBN: ");
                results = bookService.searchByISBN(isbn);
                break;
        }
        
        displayBooks(results);
    }

    private void viewBookDetails() throws LibraryException {
        String isbn = getStringInput("Enter ISBN: ");
        Book book = bookService.getBookDetails(isbn);
        System.out.println("\n" + book);
        System.out.println("Added Date: " + book.getAddedDate());
    }

    private void checkAvailability() {
        List<Book> available = bookService.getAvailableBooks();
        System.out.println("\n--- Available Books ---");
        displayBooks(available);
    }

    private void handleMemberOperations() throws LibraryException {
        System.out.println("\n--- Member Operations ---");
        System.out.println("1. Register Member");
        System.out.println("2. Update Member Info");
        System.out.println("3. Search Members");
        System.out.println("4. View Member History");
        System.out.println("5. Fine Management");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: registerMember(); break;
            case 2: updateMemberInfo(); break;
            case 3: searchMembers(); break;
            case 4: viewMemberHistory(); break;
            case 5: fineManagement(); break;
        }
    }

    private void registerMember() throws LibraryException {
        System.out.println("\n--- Register Member ---");
        String id = getStringInput("Member ID: ");
        String name = getStringInput("Name: ");
        String email = getStringInput("Email: ");
        String phone = getStringInput("Phone: ");
        String address = getStringInput("Address: ");
        
        System.out.println("Member Types: " + Arrays.toString(MemberType.values()));
        String typeStr = getStringInput("Member Type: ").toUpperCase();
        MemberType type = MemberType.valueOf(typeStr);
        
        Member member = new Member(id, name, email, phone, address, type);
        memberService.registerMember(member);
        System.out.println("Member registered successfully!");
    }

    private void updateMemberInfo() throws LibraryException {
        String id = getStringInput("Enter Member ID: ");
        String name = getStringInput("New Name (or press Enter to skip): ");
        String email = getStringInput("New Email (or press Enter to skip): ");
        String phone = getStringInput("New Phone (or press Enter to skip): ");
        String address = getStringInput("New Address (or press Enter to skip): ");
        
        memberService.updateMemberInfo(id,
                name.isEmpty() ? null : name,
                email.isEmpty() ? null : email,
                phone.isEmpty() ? null : phone,
                address.isEmpty() ? null : address);
        System.out.println("Member updated successfully!");
    }

    private void searchMembers() {
        String name = getStringInput("Enter member name: ");
        List<Member> results = memberService.searchMembersByName(name);
        displayMembers(results);
    }

    private void viewMemberHistory() throws LibraryException {
        String id = getStringInput("Enter Member ID: ");
        Member member = memberService.getMemberDetails(id);
        System.out.println("\n" + member);
        
        List<Transaction> transactions = transactionService.getMemberTransactions(id);
        System.out.println("\nBorrowing History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private void fineManagement() throws LibraryException {
        System.out.println("\n--- Fine Management ---");
        System.out.println("1. View Member Fines  2. Pay Fine");
        int choice = getIntInput("Enter choice: ");
        
        if (choice == 1) {
            List<Member> members = memberService.getMembersWithFines();
            displayMembers(members);
        } else if (choice == 2) {
            String id = getStringInput("Enter Member ID: ");
            double amount = getDoubleInput("Enter payment amount: ");
            memberService.payFine(id, amount);
            System.out.println("Fine paid successfully!");
        }
    }

    private void handleBorrowingOperations() throws LibraryException {
        System.out.println("\n--- Borrowing Operations ---");
        System.out.println("1. Issue Book");
        System.out.println("2. Return Book");
        System.out.println("3. Renew Book");
        System.out.println("4. Reserve Book");
        System.out.println("5. View Overdue Books");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: issueBook(); break;
            case 2: returnBook(); break;
            case 3: renewBook(); break;
            case 4: reserveBook(); break;
            case 5: viewOverdueBooks(); break;
        }
    }

    private void issueBook() throws LibraryException {
        String memberId = getStringInput("Enter Member ID: ");
        String isbn = getStringInput("Enter ISBN: ");
        
        String transactionId = transactionService.issueBook(memberId, isbn);
        System.out.println("Book issued successfully! Transaction ID: " + transactionId);
    }

    private void returnBook() throws LibraryException {
        String transactionId = getStringInput("Enter Transaction ID: ");
        double fine = transactionService.returnBook(transactionId);
        
        System.out.println("Book returned successfully!");
        if (fine > 0) {
            System.out.printf("Fine charged: $%.2f%n", fine);
        }
    }

    private void renewBook() throws LibraryException {
        String transactionId = getStringInput("Enter Transaction ID: ");
        transactionService.renewBook(transactionId);
        System.out.println("Book renewed successfully!");
    }

    private void reserveBook() throws LibraryException {
        String memberId = getStringInput("Enter Member ID: ");
        String isbn = getStringInput("Enter ISBN: ");
        
        String reservationId = transactionService.reserveBook(memberId, isbn);
        System.out.println("Book reserved successfully! Reservation ID: " + reservationId);
    }

    private void viewOverdueBooks() {
        List<Transaction> overdue = transactionService.getOverdueTransactions();
        System.out.println("\n--- Overdue Books ---");
        for (Transaction t : overdue) {
            System.out.println(t + " | Days Overdue: " + t.getDaysOverdue());
        }
    }

    private void handleAdvancedFeatures() {
        System.out.println("\n--- Advanced Features ---");
        System.out.println("1. Book Recommendations");
        System.out.println("2. Advanced Search");
        System.out.println("3. Popular Books Report");
        System.out.println("4. Member Analytics");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: bookRecommendations(); break;
            case 2: advancedSearch(); break;
            case 3: popularBooksReport(); break;
            case 4: memberAnalytics(); break;
        }
    }

    private void bookRecommendations() {
        String memberId = getStringInput("Enter Member ID: ");
        List<Book> recommendations = recommendationService.getRecommendationsForMember(memberId);
        System.out.println("\n--- Recommended Books ---");
        displayBooks(recommendations);
    }

    private void advancedSearch() {
        System.out.println("\n--- Advanced Search ---");
        System.out.println("Search by Category and Year");
        System.out.println("Categories: " + Arrays.toString(BookCategory.values()));
        String cat = getStringInput("Enter category: ").toUpperCase();
        int year = getIntInput("Enter year: ");
        
        List<Book> results = bookService.searchByCategoryAndYear(BookCategory.valueOf(cat), year);
        displayBooks(results);
    }

    private void popularBooksReport() {
        System.out.println("\n--- Popular Books ---");
        List<Book> popular = recommendationService.getPopularBooks(10);
        displayBooks(popular);
    }

    private void memberAnalytics() {
        System.out.println("\n--- Member Analytics ---");
        System.out.println("Top Borrowers:");
        List<Member> topBorrowers = analyticsService.getTopBorrowers(5);
        displayMembers(topBorrowers);
        
        System.out.println("\nMember Distribution:");
        Map<MemberType, Long> distribution = analyticsService.getMemberDistribution();
        distribution.forEach((type, count) -> System.out.println(type + ": " + count));
    }

    private void handleReportsAndAnalytics() {
        System.out.println("\n--- Reports & Analytics ---");
        System.out.println("1. Borrowing Reports");
        System.out.println("2. Fine Collection Reports");
        System.out.println("3. Book Popularity Analysis");
        System.out.println("4. Member Engagement Reports");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: borrowingReports(); break;
            case 2: fineCollectionReports(); break;
            case 3: bookPopularityAnalysis(); break;
            case 4: memberEngagementReports(); break;
        }
    }

    private void borrowingReports() {
        System.out.println("\n--- Borrowing Trends by Category ---");
        Map<BookCategory, Long> trends = analyticsService.getBorrowingTrendsByCategory();
        trends.forEach((category, count) -> System.out.println(category + ": " + count + " borrows"));
    }

    private void fineCollectionReports() {
        double total = analyticsService.getTotalFineCollection();
        System.out.printf("\nTotal Fine Collection: $%.2f%n", total);
        
        System.out.println("\nDefaulters:");
        List<Member> defaulters = analyticsService.getDefaulters();
        displayMembers(defaulters);
    }

    private void bookPopularityAnalysis() {
        System.out.println("\n--- Most Popular Books ---");
        List<Book> popular = recommendationService.getPopularBooks(10);
        displayBooks(popular);
        
        System.out.println("\n--- Least Borrowed Books ---");
        List<Book> least = analyticsService.getLeastBorrowedBooks(10);
        displayBooks(least);
    }

    private void memberEngagementReports() {
        long activeCount = analyticsService.getActiveMembersCount();
        System.out.println("\nActive Members: " + activeCount);
        
        System.out.println("\nTop Borrowers:");
        List<Member> top = analyticsService.getTopBorrowers(10);
        displayMembers(top);
    }

    private void handleSystemOperations() {
        System.out.println("\n--- System Operations ---");
        System.out.println("1. Data Backup");
        System.out.println("2. Export Books");
        System.out.println("3. Export Members");
        
        int choice = getIntInput("Enter choice: ");
        
        try {
            switch (choice) {
                case 1:
                    String dir = getStringInput("Enter backup directory: ");
                    dataService.backupData(dir);
                    System.out.println("Backup completed successfully!");
                    break;
                case 2:
                    String bookFile = getStringInput("Enter filename: ");
                    dataService.exportBooksToCSV(bookFile);
                    System.out.println("Books exported successfully!");
                    break;
                case 3:
                    String memberFile = getStringInput("Enter filename: ");
                    dataService.exportMembersToCSV(memberFile);
                    System.out.println("Members exported successfully!");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void displayBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        books.forEach(System.out::println);
    }

    private void displayMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        members.forEach(System.out::println);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void loadSampleData() {
        try {
            // Add sample books
            bookService.addBook(new PhysicalBook("978-0-13-468599-1", "Clean Code", "Robert Martin", 
                    "Prentice Hall", 2008, 464, BookCategory.TECHNOLOGY, 5, "A-101"));
            bookService.addBook(new PhysicalBook("978-0-596-52068-7", "JavaScript: The Good Parts", 
                    "Douglas Crockford", "O'Reilly", 2008, 176, BookCategory.TECHNOLOGY, 3, "A-102"));
            bookService.addBook(new EBook("978-0-13-235088-4", "The Clean Coder", "Robert Martin", 
                    "Prentice Hall", 2011, 256, BookCategory.TECHNOLOGY, 10, "http://example.com/clean-coder", 2.5, "PDF"));
            bookService.addBook(new AudioBook("978-1-4028-9462-6", "Sapiens", "Yuval Noah Harari", 
                    "Harper Audio", 2015, 443, BookCategory.HISTORY, 2, "Derek Perkins", 900, "MP3"));
            bookService.addBook(new PhysicalBook("978-0-7432-7356-5", "1984", "George Orwell", 
                    "Signet Classic", 1949, 328, BookCategory.FICTION, 4, "B-201"));

            // Add sample members
            memberService.registerMember(new Member("M001", "John Doe", "john@email.com", 
                    "123-456-7890", "123 Main St", MemberType.STUDENT));
            memberService.registerMember(new Member("M002", "Jane Smith", "jane@email.com", 
                    "098-765-4321", "456 Oak Ave", MemberType.FACULTY));
            memberService.registerMember(new Member("M003", "Bob Johnson", "bob@email.com", 
                    "555-123-4567", "789 Pine Rd", MemberType.GENERAL_PUBLIC));

            System.out.println("Sample data loaded successfully!");
        } catch (LibraryException e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }
}
