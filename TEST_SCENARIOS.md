# Test Scenarios and Expected Outcomes

## Test Case 1: Add New Book

### Steps:
1. Select "1. Book Operations"
2. Select "1. Add New Book"
3. Choose book type: 1 (Physical Book)
4. Enter ISBN: 978-0-321-35668-0
5. Enter Title: Effective Java
6. Enter Author: Joshua Bloch
7. Enter Publisher: Addison-Wesley
8. Enter Year: 2018
9. Enter Pages: 416
10. Enter Category: TECHNOLOGY
11. Enter Total Copies: 5
12. Enter Shelf Location: A-103

### Expected Outcome:
- Message: "Book added successfully!"
- Book is stored in the system
- Available copies = Total copies = 5

### Test Case 1b: Duplicate ISBN Validation

### Steps:
1. Try to add the same book again with ISBN: 978-0-321-35668-0

### Expected Outcome:
- Error message: "Book with ISBN 978-0-321-35668-0 already exists"
- Book is not added

---

## Test Case 2: Register Member

### Steps:
1. Select "2. Member Operations"
2. Select "1. Register Member"
3. Enter Member ID: M004
4. Enter Name: Alice Williams
5. Enter Email: alice@email.com
6. Enter Phone: 555-999-8888
7. Enter Address: 321 Elm Street
8. Enter Member Type: STUDENT

### Expected Outcome:
- Message: "Member registered successfully!"
- Member is stored with:
  - Borrowing limit: 5 books
  - Borrowing period: 14 days
  - Fine rate: $1.00/day
  - Active status: true
  - Total fines: $0.00

### Test Case 2b: Invalid Email Validation

### Steps:
1. Try to register member with email: "invalid-email"

### Expected Outcome:
- Error message: "Invalid email format"
- Member is not registered

---

## Test Case 3: Issue Book - Success

### Prerequisites:
- Member M001 exists and is active
- Book 978-0-13-468599-1 exists and is available

### Steps:
1. Select "3. Borrowing Operations"
2. Select "1. Issue Book"
3. Enter Member ID: M001
4. Enter ISBN: 978-0-13-468599-1

### Expected Outcome:
- Message: "Book issued successfully! Transaction ID: TXN00001"
- Book available copies decreased by 1
- Transaction created with:
  - Issue date: Current date
  - Due date: Current date + 14 days (for student)
  - Returned: false
- ISBN added to member's borrowing history

---

## Test Case 4: Issue Book - Borrowing Limit Exceeded

### Prerequisites:
- Member M003 (General Public) has already borrowed 3 books

### Steps:
1. Try to issue a 4th book to member M003

### Expected Outcome:
- Error message: "Borrowing limit exceeded"
- Book is not issued
- Available copies remain unchanged

---

## Test Case 5: Issue Book - Unpaid Fines

### Prerequisites:
- Member M001 has unpaid fines of $5.00

### Steps:
1. Try to issue a book to member M001

### Expected Outcome:
- Error message: "Cannot issue book. Member has unpaid fines: $5.0"
- Book is not issued

---

## Test Case 6: Return Book - No Fine

### Prerequisites:
- Transaction TXN00001 exists
- Book returned before due date

### Steps:
1. Select "3. Borrowing Operations"
2. Select "2. Return Book"
3. Enter Transaction ID: TXN00001

### Expected Outcome:
- Message: "Book returned successfully!"
- Fine: $0.00
- Transaction marked as returned
- Return date set to current date
- Book available copies increased by 1

---

## Test Case 7: Return Book - With Fine

### Prerequisites:
- Transaction TXN00002 exists
- Book returned 5 days after due date
- Member is STUDENT (fine rate: $1.00/day)

### Steps:
1. Return the book using transaction ID: TXN00002

### Expected Outcome:
- Message: "Book returned successfully!"
- Message: "Fine charged: $5.00"
- Fine calculated: 5 days × $1.00 = $5.00
- Fine added to member's total fines
- Transaction marked as returned

---

## Test Case 8: Renew Book - Success

### Prerequisites:
- Transaction TXN00003 exists
- Book not overdue
- Renewal count < 2

### Steps:
1. Select "3. Borrowing Operations"
2. Select "3. Renew Book"
3. Enter Transaction ID: TXN00003

### Expected Outcome:
- Message: "Book renewed successfully!"
- Due date extended by borrowing period (14 days for student)
- Renewal count increased by 1

---

## Test Case 9: Renew Book - Maximum Renewals Reached

### Prerequisites:
- Transaction has already been renewed 2 times

### Steps:
1. Try to renew the book again

### Expected Outcome:
- Error message: "Maximum renewals reached"
- Due date remains unchanged

---

## Test Case 10: Reserve Book

### Prerequisites:
- Member M001 exists and is active
- Book 978-0-7432-7356-5 exists

### Steps:
1. Select "3. Borrowing Operations"
2. Select "4. Reserve Book"
3. Enter Member ID: M001
4. Enter ISBN: 978-0-7432-7356-5

### Expected Outcome:
- Message: "Book reserved successfully! Reservation ID: RES00001"
- Reservation created with current date
- Book reserved copies increased by 1

---

## Test Case 11: Search Books by Title

### Steps:
1. Select "1. Book Operations"
2. Select "3. Search Books"
3. Select "1. By Title"
4. Enter title: "Clean"

### Expected Outcome:
- Display all books with "Clean" in title:
  - Clean Code
  - The Clean Coder

---

## Test Case 12: Search Books by Author

### Steps:
1. Search by author: "Robert Martin"

### Expected Outcome:
- Display all books by Robert Martin:
  - Clean Code
  - The Clean Coder

---

## Test Case 13: Search Books by Category

### Steps:
1. Search by category: TECHNOLOGY

### Expected Outcome:
- Display all technology books:
  - Clean Code
  - JavaScript: The Good Parts
  - The Clean Coder

---

## Test Case 14: Advanced Search - Category and Year

### Steps:
1. Select "4. Advanced Features"
2. Select "2. Advanced Search"
3. Enter category: TECHNOLOGY
4. Enter year: 2008

### Expected Outcome:
- Display books matching both criteria:
  - Clean Code (2008, TECHNOLOGY)
  - JavaScript: The Good Parts (2008, TECHNOLOGY)

---

## Test Case 15: Book Recommendations

### Prerequisites:
- Member M001 has borrowed books from TECHNOLOGY category

### Steps:
1. Select "4. Advanced Features"
2. Select "1. Book Recommendations"
3. Enter Member ID: M001

### Expected Outcome:
- Display recommended TECHNOLOGY books that M001 hasn't borrowed
- Books are available (available copies > 0)

---

## Test Case 16: Popular Books Report

### Steps:
1. Select "4. Advanced Features"
2. Select "3. Popular Books Report"

### Expected Outcome:
- Display top 10 most borrowed books
- Sorted by borrow count (descending)

---

## Test Case 17: View Overdue Books

### Prerequisites:
- Some transactions have passed their due date

### Steps:
1. Select "3. Borrowing Operations"
2. Select "5. View Overdue Books"

### Expected Outcome:
- Display all overdue transactions
- Show days overdue for each transaction
- Sorted by due date

---

## Test Case 18: Top Borrowers Report

### Steps:
1. Select "4. Advanced Features"
2. Select "4. Member Analytics"

### Expected Outcome:
- Display top 5 members by borrow count
- Show member details and statistics

---

## Test Case 19: Borrowing Trends by Category

### Steps:
1. Select "5. Reports & Analytics"
2. Select "1. Borrowing Reports"

### Expected Outcome:
- Display borrow count for each category:
  - TECHNOLOGY: 15
  - FICTION: 8
  - HISTORY: 5
  - etc.

---

## Test Case 20: Fine Collection Report

### Steps:
1. Select "5. Reports & Analytics"
2. Select "2. Fine Collection Reports"

### Expected Outcome:
- Display total fine collection amount
- List all members with unpaid fines
- Sorted by fine amount (descending)

---

## Test Case 21: Member Engagement Report

### Steps:
1. Select "5. Reports & Analytics"
2. Select "4. Member Engagement Reports"

### Expected Outcome:
- Display active members count
- Display top 10 borrowers
- Show engagement statistics

---

## Test Case 22: Pay Fine

### Prerequisites:
- Member M001 has total fines of $10.00

### Steps:
1. Select "2. Member Operations"
2. Select "5. Fine Management"
3. Select "2. Pay Fine"
4. Enter Member ID: M001
5. Enter payment amount: 10.00

### Expected Outcome:
- Message: "Fine paid successfully!"
- Member's total fines reduced to $0.00

---

## Test Case 23: Export Books to CSV

### Steps:
1. Select "6. System Operations"
2. Select "2. Export Books"
3. Enter filename: books_export.csv

### Expected Outcome:
- Message: "Books exported successfully!"
- CSV file created with all book data
- File contains headers and all book records

---

## Test Case 24: Data Backup

### Steps:
1. Select "6. System Operations"
2. Select "1. Data Backup"
3. Enter backup directory: ./backup

### Expected Outcome:
- Message: "Backup completed successfully!"
- Three CSV files created:
  - books_[date].csv
  - members_[date].csv
  - transactions_[date].csv

---

## Test Case 25: View Member History

### Prerequisites:
- Member M001 has borrowed multiple books

### Steps:
1. Select "2. Member Operations"
2. Select "4. View Member History"
3. Enter Member ID: M001

### Expected Outcome:
- Display member details
- Display complete borrowing history
- Show all transactions (active and completed)

---

## Performance Test Scenarios

### Test Case 26: Large Dataset Search
- Add 1000 books
- Search by various criteria
- Expected: Results returned in < 1 second

### Test Case 27: Concurrent Operations
- Multiple members borrowing books simultaneously
- Expected: No data corruption, all operations succeed

### Test Case 28: Stream Operations Performance
- Generate analytics on 10,000 transactions
- Expected: Reports generated in < 2 seconds

---

## Edge Cases

### Test Case 29: Book with 0 Available Copies
- Try to issue a book with 0 available copies
- Expected: Error message "Book not available"

### Test Case 30: Inactive Member
- Try to issue book to inactive member
- Expected: Error message "Member account is inactive"

### Test Case 31: Return Already Returned Book
- Try to return a book that's already returned
- Expected: Error message "Book already returned"

### Test Case 32: Maximum Fine Limit
- Return book 100 days late
- Expected: Fine capped at $50.00 (maximum limit)

---

## Summary

Total Test Cases: 32
- Positive Test Cases: 20
- Negative Test Cases: 8
- Performance Test Cases: 3
- Edge Cases: 4

All test cases should be executed to ensure system reliability and correctness.
