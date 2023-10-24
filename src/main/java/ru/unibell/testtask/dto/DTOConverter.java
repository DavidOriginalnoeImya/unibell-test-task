package ru.unibell.testtask.dto;

import ru.unibell.testtask.data.client.Client;

import java.util.Set;

public class DTOConverter {
    public static Client convertFromDTO(AddClientDTO addClientDTO) {
        Client client = new Client();
        client.setName(addClientDTO.getClientName());
        return client;
    }

    public static ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(clientDTO.getName());
        clientDTO.setEmails(clientDTO.getEmails());
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
