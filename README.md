# Library Management System

A comprehensive library management system built with Java that handles books, members, borrowing/returning processes, and provides advanced search and recommendation features using modern Java concepts.

## Features

### 1. Book Management
- **Multiple Book Types**: Physical books, E-books, and Audiobooks (using inheritance)
- **Book Categories**: Fiction, Non-Fiction, Science, Technology, History, Biography, etc.
- **Book Operations**: Add, update, search, and track availability
- **Duplicate ISBN validation**
- **Stock management**

### 2. Member Management
- **Member Types**: Student, Faculty, General Public (with different privileges)
- **Member Operations**: Register, update, search members
- **Borrowing Limits**: 
  - Student: 5 books for 14 days
  - Faculty: 10 books for 30 days
  - General Public: 3 books for 7 days
- **Fine Management**: Track and manage overdue fines

### 3. Borrowing System
- **Issue Books**: With comprehensive validation checks
- **Return Books**: Automatic fine calculation
- **Renewal Process**: Extend borrowing period (max 2 renewals)
- **Reservation System**: Queue system for unavailable books
- **Overdue Tracking**: Monitor and report overdue books

### 4. Advanced Search & Recommendations
- **Multi-criteria Search**: By title, author, category, ISBN, year
- **Recommendation Engine**: 
  - Based on borrowing history
  - Similar member preferences
  - Popular books by category
- **Stream API**: Extensive use of Java Streams for filtering and analysis

### 5. Reports & Analytics
- **Member Reports**: Active members, defaulters, top borrowers
- **Book Reports**: Most popular books, least borrowed books
- **Financial Reports**: Fine collection analysis
- **Borrowing Trends**: By category and time period
- **Member Distribution**: By member type

### 6. Data Management
- **Export to CSV**: Books, members, and transactions
- **Data Backup**: Complete system backup
- **Sample Data**: Pre-loaded for testing

## Project Structure

```
library-management-system/
├── src/
│   └── com/
│       └── library/
│           ├── model/
│           │   ├── Book.java (abstract)
│           │   ├── PhysicalBook.java
│           │   ├── EBook.java
│           │   ├── AudioBook.java
│           │   ├── BookCategory.java (enum)
│           │   ├── Member.java
│           │   ├── MemberType.java (enum)
│           │   ├── Transaction.java
│           │   └── Reservation.java
│           ├── service/
│           │   ├── BookService.java
│           │   ├── MemberService.java
│           │   ├── TransactionService.java
│           │   ├── RecommendationService.java
│           │   ├── AnalyticsService.java
│           │   └── DataService.java
│           ├── repository/
│           │   └── LibraryRepository.java
│           ├── exception/
│           │   └── LibraryException.java
│           └── LibraryManagementSystem.java (main)
└── README.md
```

## Setup and Installation

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt or IDE (IntelliJ IDEA, Eclipse, VS Code)

### Compilation and Running

Navigate to the project directory and compile:

```bash
cd library-management-system/src
javac com/library/*.java com/library/*/*.java
java com.library.LibraryManagementSystem
```

## Usage Guide

### Main Menu Structure

```
=== LIBRARY MANAGEMENT SYSTEM ===
1. Book Operations
   1.1 Add New Book
   1.2 Update Book Info
   1.3 Search Books
   1.4 View Book Details
   1.5 Check Availability

2. Member Operations
   2.1 Register Member
   2.2 Update Member Info
   2.3 Search Members
   2.4 View Member History
   2.5 Fine Management

3. Borrowing Operations
   3.1 Issue Book
   3.2 Return Book
   3.3 Renew Book
   3.4 Reserve Book
   3.5 View Overdue Books

4. Advanced Features
   4.1 Book Recommendations
   4.2 Advanced Search
   4.3 Popular Books Report
   4.4 Member Analytics

5. Reports & Analytics
   5.1 Borrowing Reports
   5.2 Fine Collection Reports
   5.3 Book Popularity Analysis
   5.4 Member Engagement Reports

6. System Operations
   6.1 Data Backup
   6.2 Export Books
   6.3 Export Members

0. Exit
```

## Sample Data

The system comes pre-loaded with sample data:

### Books
- Clean Code (Physical Book)
- JavaScript: The Good Parts (Physical Book)
- The Clean Coder (E-Book)
- Sapiens (Audio Book)
- 1984 (Physical Book)

### Members
- M001: John Doe (Student)
- M002: Jane Smith (Faculty)
- M003: Bob Johnson (General Public)

## Key Features Implementation

### 1. Inheritance
- Abstract `Book` class with concrete implementations: `PhysicalBook`, `EBook`, `AudioBook`

### 2. Enums
- `BookCategory`: Different book categories
- `MemberType`: Member types with associated privileges

### 3. Java Streams & Lambdas
```java
// Search books by author
bookService.searchByAuthor(author)
    .stream()
    .filter(book -> book.getAvailableCopies() > 0)
    .collect(Collectors.toList());

// Get top borrowers
analyticsService.getTopBorrowers(10)
    .stream()
    .sorted(Comparator.comparing(Member::getTotalFines).reversed())
    .collect(Collectors.toList());
```

### 4. Validation Checks for Book Borrowing
1. Member exists and is active
2. Book is available
3. Member hasn't exceeded borrowing limit
4. Member has no unpaid fines
5. Book is not reserved by someone else

### 5. Fine Calculation System
- Different rates for different member types
- Student: $1.00/day
- Faculty: $0.50/day
- General Public: $2.00/day
- Maximum fine limit: $50.00

## Test Scenarios

### Scenario 1: Issue a Book
1. Navigate to Borrowing Operations → Issue Book
2. Enter Member ID: M001
3. Enter ISBN: 978-0-13-468599-1
4. System validates and issues the book

### Scenario 2: Return with Fine
1. Issue a book and manually adjust the due date (for testing)
2. Return the book after due date
3. System calculates and applies fine

### Scenario 3: Book Recommendations
1. Navigate to Advanced Features → Book Recommendations
2. Enter Member ID: M001
3. System shows recommended books based on history

### Scenario 4: Generate Reports
1. Navigate to Reports & Analytics
2. Select desired report type
3. View analytics and insights

## Advanced Analytics Examples

### Borrowing Trends by Category
```java
Map<BookCategory, Long> trends = analyticsService.getBorrowingTrendsByCategory();
// Returns: {TECHNOLOGY=15, FICTION=8, HISTORY=5, ...}
```

### Average Borrowing Duration
```java
double avgDays = analyticsService.getAverageBorrowingDuration(BookCategory.TECHNOLOGY);
// Returns average days books are borrowed in a category
```

### Book Turnover Rate
```java
double turnover = analyticsService.getBookTurnoverRate(isbn);
// Returns monthly turnover rate for a book
```

## Exception Handling

The system uses custom `LibraryException` for:
- Duplicate ISBN validation
- Member not found errors
- Book unavailability
- Borrowing limit exceeded
- Invalid email format
- Fine payment errors

## Data Export

### Export Books to CSV
```
ISBN,Title,Author,Publisher,Year,Pages,Category,Type,TotalCopies,AvailableCopies
```

### Export Members to CSV
```
MemberID,Name,Email,Phone,MemberType,MembershipDate,Active,TotalFines
```

### Export Transactions to CSV
```
TransactionID,MemberID,ISBN,IssueDate,DueDate,ReturnDate,Fine,Returned
```

## Future Enhancements

- Database integration (MySQL/PostgreSQL)
- GUI using JavaFX or Swing
- Email notifications for overdue books
- Barcode scanning support
- Multi-language support
- Mobile app integration
- Online catalog browsing

## Technical Highlights

- **Design Patterns**: Repository pattern, Service layer architecture
- **Java 8+ Features**: Streams, Lambdas, Optional, LocalDate
- **Collections**: ConcurrentHashMap for thread-safe operations
- **OOP Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction
- **Clean Code**: Meaningful names, single responsibility, DRY principle

## Author

Library Management System - Java Project

## License

This project is created for educational purposes.
