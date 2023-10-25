package ru.unibell.testtask.client.dto;

import ru.unibell.testtask.client.model.Client;

import java.util.Set;

public class DTOConverter {
    public static Client convertFromDTO(AddClientDTO addClientDTO) {
        Client client = new Client();
        client.setName(addClientDTO.getName());
        return client;
    }

    public static ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setEmails(client.getEmails());
        clientDTO.setPhoneNumbers(client.getPhoneNumbers());
        return clientDTO;
    }

    public static ContactsDTO convertToDTO(Set<String> emails, Set<String> phoneNumbers) {
        ContactsDTO contactsDTO = new ContactsDTO();
        contactsDTO.setEmails(emails);
        contactsDTO.setPhoneNumbers(phoneNumbers);
        return contactsDTO;
    }
}
