package ru.unibell.testtask.client.dto;

import java.util.Set;

public class ContactsDTO {

    private Set<String> emails;

    private Set<String> phoneNumbers;

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
