# UML Class Diagrams

## Overview
This document describes the UML class diagrams for the Library Management System. You can draw these diagrams by hand or use tools like draw.io, Lucidchart, or PlantUML.

---

## 1. Model Layer Class Diagram

### Book Hierarchy (Inheritance)

```
┌─────────────────────────────────────┐
│          <<abstract>>               │
│             Book                    │
├─────────────────────────────────────┤
│ - isbn: String                      │
│ - title: String                     │
│ - author: String                    │
│ - publisher: String                 │
│ - publicationYear: int              │
│ - pages: int                        │
│ - category: BookCategory            │
│ - totalCopies: int                  │
│ - availableCopies: int              │
│ - reservedCopies: int               │
│ - addedDate: LocalDate              │
├─────────────────────────────────────┤
│ + getters/setters()                 │
│ + getBookType(): String {abstract}  │
│ + equals(Object): boolean           │
│ + hashCode(): int                   │
│ + toString(): String                │
└─────────────────────────────────────┘
                 △
                 │
        ┌────────┼────────┐
        │        │        │
┌───────┴──┐ ┌──┴─────┐ ┌┴────────┐
│Physical  │ │ EBook  │ │AudioBook│
│Book      │ │        │ │         │
├──────────┤ ├────────┤ ├─────────┤
│-shelf    │ │-download│ │-narrator│
│ Location │ │ Url     │ │-duration│
│-condition│ │-fileSizeMB│-audio  │
│          │ │-format  │  Format  │
├──────────┤ ├────────┤ ├─────────┤
│+getBook  │ │+getBook│ │+getBook │
│ Type()   │ │ Type() │ │ Type()  │
└──────────┘ └────────┘ └─────────┘
```

### Member Class

```
┌─────────────────────────────────────┐
│            Member                   │
├─────────────────────────────────────┤
│ - memberId: String                  │
│ - name: String                      │
│ - email: String                     │
│ - phone: String                     │
│ - address: String                   │
│ - memberType: MemberType            │
│ - membershipDate: LocalDate         │
│ - active: boolean                   │
│ - totalFines: double                │
│ - borrowingHistory: List<String>    │
├─────────────────────────────────────┤
│ + getters/setters()                 │
│ + addToBorrowingHistory(String)     │
│ + equals(Object): boolean           │
│ + hashCode(): int                   │
│ + toString(): String                │
└─────────────────────────────────────┘
```

### Transaction Class

```
┌─────────────────────────────────────┐
│          Transaction                │
├─────────────────────────────────────┤
│ - transactionId: String             │
│ - memberId: String                  │
│ - isbn: String                      │
│ - issueDate: LocalDate              │
│ - dueDate: LocalDate                │
│ - returnDate: LocalDate             │
│ - fine: double                      │
│ - returned: boolean                 │
│ - renewalCount: int                 │
├─────────────────────────────────────┤
│ + getters/setters()                 │
│ + getDaysOverdue(): long            │
│ + isOverdue(): boolean              │
│ + toString(): String                │
└─────────────────────────────────────┘
```

### Reservation Class

```
┌─────────────────────────────────────┐
│          Reservation                │
├─────────────────────────────────────┤
│ - reservationId: String             │
│ - memberId: String                  │
│ - isbn: String                      │
│ - reservationDate: LocalDate        │
│ - fulfilled: boolean                │
├─────────────────────────────────────┤
│ + getters/setters()                 │
│ + toString(): String                │
└─────────────────────────────────────┘
```

### Enums

```
┌──────────────────┐      ┌──────────────────────────┐
│  BookCategory    │      │      MemberType          │
├──────────────────┤      ├──────────────────────────┤
│ FICTION          │      │ STUDENT                  │
│ NON_FICTION      │      │ - borrowingLimit: 5      │
│ SCIENCE          │      │ - borrowingDays: 14      │
│ TECHNOLOGY       │      │ - fineRatePerDay: 1.0    │
│ HISTORY          │      │                          │
│ BIOGRAPHY        │      │ FACULTY                  │
│ SELF_HELP        │      │ - borrowingLimit: 10     │
│ CHILDREN         │      │ - borrowingDays: 30      │
│ MYSTERY          │      │ - fineRatePerDay: 0.5    │
│ ROMANCE          │      │                          │
└──────────────────┘      │ GENERAL_PUBLIC           │
                          │ - borrowingLimit: 3      │
                          │ - borrowingDays: 7       │
                          │ - fineRatePerDay: 2.0    │
                          └──────────────────────────┘
```

---

## 2. Repository Layer Class Diagram

```
┌─────────────────────────────────────────────────┐
│          LibraryRepository                      │
├─────────────────────────────────────────────────┤
│ - books: Map<String, Book>                      │
│ - members: Map<String, Member>                  │
│ - transactions: Map<String, Transaction>        │
│ - reservations: Map<String, Reservation>        │
│ - transactionCounter: int                       │
│ - reservationCounter: int                       │
├─────────────────────────────────────────────────┤
│ + addBook(Book): void                           │
│ + getBook(String): Book                         │
│ + getAllBooks(): Collection<Book>               │
│ + bookExists(String): boolean                   │
│                                                 │
│ + addMember(Member): void                       │
│ + getMember(String): Member                     │
│ + getAllMembers(): Collection<Member>           │
│ + memberExists(String): boolean                 │
│                                                 │
│ + addTransaction(Transaction): void             │
│ + getTransaction(String): Transaction           │
│ + getAllTransactions(): Collection<Transaction> │
│ + generateTransactionId(): String               │
│                                                 │
│ + addReservation(Reservation): void             │
│ + getReservation(String): Reservation           │
│ + getAllReservations(): Collection<Reservation> │
│ + generateReservationId(): String               │
└─────────────────────────────────────────────────┘
```

---

## 3. Service Layer Class Diagram

```
┌──────────────────────┐
│    BookService       │
├──────────────────────┤
│ - repository         │
├──────────────────────┤
│ + addBook()          │
│ + updateBookInfo()   │
│ + getBookDetails()   │
│ + searchByTitle()    │
│ + searchByAuthor()   │
│ + searchByCategory() │
│ + getAvailableBooks()│
└──────────────────────┘

┌──────────────────────┐
│   MemberService      │
├──────────────────────┤
│ - repository         │
├──────────────────────┤
│ + registerMember()   │
│ + updateMemberInfo() │
│ + getMemberDetails() │
│ + searchMembers()    │
│ + addFine()          │
│ + payFine()          │
└──────────────────────┘

┌──────────────────────┐
│ TransactionService   │
├──────────────────────┤
│ - repository         │
│ - memberService      │
├──────────────────────┤
│ + issueBook()        │
│ + returnBook()       │
│ + renewBook()        │
│ + reserveBook()      │
│ + getOverdue()       │
│ - calculateFine()    │
└──────────────────────┘

┌──────────────────────┐
│RecommendationService │
├──────────────────────┤
│ - repository         │
├──────────────────────┤
│ + getRecommendations()│
│ + getPopularBooks()  │
│ + getSimilarMember() │
└──────────────────────┘

┌──────────────────────┐
│  AnalyticsService    │
├──────────────────────┤
│ - repository         │
├──────────────────────┤
│ + getBorrowingTrends()│
│ + getTopBorrowers()  │
│ + getDefaulters()    │
│ + getTotalFines()    │
│ + getAvgDuration()   │
└──────────────────────┘

┌──────────────────────┐
│    DataService       │
├──────────────────────┤
│ - repository         │
├──────────────────────┤
│ + exportBooksToCSV() │
│ + exportMembersToCSV()│
│ + exportTransactions()│
│ + backupData()       │
└──────────────────────┘
```

---

## 4. Complete System Architecture Diagram

```
┌─────────────────────────────────────────────────────┐
│                                                     │
│         LibraryManagementSystem (Main)              │
│                                                     │
└────────────────────┬────────────────────────────────┘
                     │
                     │ uses
                     ▼
┌─────────────────────────────────────────────────────┐
│                 Service Layer                       │
│  ┌──────────┐ ┌──────────┐ ┌──────────────────┐   │
│  │  Book    │ │  Member  │ │   Transaction    │   │
│  │ Service  │ │ Service  │ │    Service       │   │
│  └──────────┘ └──────────┘ └──────────────────┘   │
│  ┌──────────┐ ┌──────────┐ ┌──────────────────┐   │
│  │Recommend │ │Analytics │ │      Data        │   │
│  │ Service  │ │ Service  │ │    Service       │   │
│  └──────────┘ └──────────┘ └──────────────────┘   │
└────────────────────┬────────────────────────────────┘
                     │
                     │ uses
                     ▼
┌─────────────────────────────────────────────────────┐
│              Repository Layer                       │
│           LibraryRepository                         │
└────────────────────┬────────────────────────────────┘
                     │
                     │ manages
                     ▼
┌─────────────────────────────────────────────────────┐
│                 Model Layer                         │
│  ┌──────┐ ┌────────┐ ┌────────────┐ ┌───────────┐ │
│  │ Book │ │ Member │ │Transaction │ │Reservation│ │
│  └──────┘ └────────┘ └────────────┘ └───────────┘ │
└─────────────────────────────────────────────────────┘
```

---

## 5. Relationships

### Associations:
- LibraryRepository **contains** Books (1 to many)
- LibraryRepository **contains** Members (1 to many)
- LibraryRepository **contains** Transactions (1 to many)
- LibraryRepository **contains** Reservations (1 to many)
- Member **has** MemberType (1 to 1)
- Book **has** BookCategory (1 to 1)
- Transaction **references** Member (many to 1)
- Transaction **references** Book (many to 1)
- Reservation **references** Member (many to 1)
- Reservation **references** Book (many to 1)

### Dependencies:
- All Services **depend on** LibraryRepository
- TransactionService **depends on** MemberService
- LibraryManagementSystem **depends on** all Services

### Inheritance:
- PhysicalBook **extends** Book
- EBook **extends** Book
- AudioBook **extends** Book

