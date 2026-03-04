package com.library.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a library member
 */
public class Member {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private MemberType memberType;
    private LocalDate membershipDate;
    private boolean active;
    private double totalFines;
    private List<String> borrowingHistory;

    public Member(String memberId, String name, String email, String phone, 
                 String address, MemberType memberType) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.memberType = memberType;
        this.membershipDate = LocalDate.now();
        this.active = true;
        this.totalFines = 0.0;
        this.borrowingHistory = new ArrayList<>();
    }

    // Getters and Setters
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public MemberType getMemberType() { return memberType; }
    public void setMemberType(MemberType memberType) { this.memberType = memberType; }
    
    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    
    public double getTotalFines() { return totalFines; }
    public void setTotalFines(double totalFines) { this.totalFines = totalFines; }
    
    public List<String> getBorrowingHistory() { return borrowingHistory; }
    public void addToBorrowingHistory(String isbn) { this.borrowingHistory.add(isbn); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    @Override
    public String toString() {
        return String.format("Member [ID: %s, Name: %s, Type: %s, Active: %s, Fines: $%.2f]",
                memberId, name, memberType, active, totalFines);
    }
}
