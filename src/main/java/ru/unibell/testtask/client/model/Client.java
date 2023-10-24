package ru.unibell.testtask.client.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ElementCollection
    private Set<String> emails = new HashSet<>();

    @ElementCollection
    private Set<String> phoneNumbers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public Set<String> getEmails() {
        return Set.copyOf(emails);
    }

    public void addPhoneNumber(String phoneNumber) {
        phoneNumbers.add(phoneNumber);
    }

    public Set<String> getPhoneNumbers() {
        return Set.copyOf(phoneNumbers);
    }
}
