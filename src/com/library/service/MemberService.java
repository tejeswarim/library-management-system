package com.library.service;

import com.library.exception.LibraryException;
import com.library.model.*;
import com.library.repository.LibraryRepository;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for member management operations
 */
public class MemberService {
    private final LibraryRepository repository;

    public MemberService(LibraryRepository repository) {
        this.repository = repository;
    }

    public void registerMember(Member member) throws LibraryException {
        if (repository.memberExists(member.getMemberId())) {
            throw new LibraryException("Member with ID " + member.getMemberId() + " already exists");
        }
        if (!isValidEmail(member.getEmail())) {
            throw new LibraryException("Invalid email format");
        }
        repository.addMember(member);
    }

    public void updateMemberInfo(String memberId, String name, String email, String phone, String address) 
            throws LibraryException {
        Member member = repository.getMember(memberId);
        if (member == null) {
            throw new LibraryException("Member not found");
        }
        if (name != null) member.setName(name);
        if (email != null && isValidEmail(email)) member.setEmail(email);
        if (phone != null) member.setPhone(phone);
        if (address != null) member.setAddress(address);
    }

    public Member getMemberDetails(String memberId) throws LibraryException {
        Member member = repository.getMember(memberId);
        if (member == null) {
            throw new LibraryException("Member not found");
        }
        return member;
    }

    public List<Member> searchMembersByName(String name) {
        return repository.getAllMembers().stream()
                .filter(member -> member.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Member> getActiveMembers() {
        return repository.getAllMembers().stream()
                .filter(Member::isActive)
                .collect(Collectors.toList());
    }

    public void addFine(String memberId, double amount) throws LibraryException {
        Member member = repository.getMember(memberId);
        if (member == null) {
            throw new LibraryException("Member not found");
        }
        member.setTotalFines(member.getTotalFines() + amount);
    }

    public void payFine(String memberId, double amount) throws LibraryException {
        Member member = repository.getMember(memberId);
        if (member == null) {
            throw new LibraryException("Member not found");
        }
        if (amount > member.getTotalFines()) {
            throw new LibraryException("Payment amount exceeds total fines");
        }
        member.setTotalFines(member.getTotalFines() - amount);
    }

    public List<Member> getMembersWithFines() {
        return repository.getAllMembers().stream()
                .filter(member -> member.getTotalFines() > 0)
                .collect(Collectors.toList());
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(repository.getAllMembers());
    }
}
