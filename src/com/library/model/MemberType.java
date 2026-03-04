package com.library.model;

/**
 * Enum representing different member types with their privileges
 */
public enum MemberType {
    STUDENT(5, 14, 1.0),
    FACULTY(10, 30, 0.5),
    GENERAL_PUBLIC(3, 7, 2.0);

    private final int borrowingLimit;
    private final int borrowingDays;
    private final double fineRatePerDay;

    MemberType(int borrowingLimit, int borrowingDays, double fineRatePerDay) {
        this.borrowingLimit = borrowingLimit;
        this.borrowingDays = borrowingDays;
        this.fineRatePerDay = fineRatePerDay;
    }

    public int getBorrowingLimit() { return borrowingLimit; }
    public int getBorrowingDays() { return borrowingDays; }
    public double getFineRatePerDay() { return fineRatePerDay; }
}
