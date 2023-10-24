package ru.unibell.testtask.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.unibell.testtask.data.client.Client;
import ru.unibell.testtask.data.client.ClientRepository;
import ru.unibell.testtask.dto.AddClientDTO;
import ru.unibell.testtask.dto.ClientDTO;
import ru.unibell.testtask.dto.ContactsDTO;
import ru.unibell.testtask.dto.DTOConverter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientDTO addClient(AddClientDTO addClientDTO) {
        Client client = DTOConverter.convertFromDTO(addClientDTO);
        return DTOConverter.convertToDTO(clientRepository.save(client));
    }

    public ClientDTO addClientEmail(Long clientId, String email) {
        Client client = findClientById(clientId);
        client.addEmail(email);
        return DTOConverter.convertToDTO(client);
    }

    public ClientDTO addClientPhoneNumber(Long clientId, String phoneNumber) {
        Client client = findClientById(clientId);
        client.addPhoneNumber(phoneNumber);
        return DTOConverter.convertToDTO(client);
    }

    public List<ClientDTO> getClients() {
        return StreamSupport
                .stream(clientRepository.findAll().spliterator(), false)
                .map(DTOConverter::convertToDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        return DTOConverter.convertToDTO(findClientById(id));
    }

    public ContactsDTO getClientContacts(Long clientId) {
        Client client = findClientById(clientId);
        return DTOConverter.convertToDTO(client.getEmails(), client.getPhoneNumbers());
    }

    public Set<String> getClientEmails(Long clientId) {
        return findClientById(clientId).getEmails();
    }

    public Set<String> getClientPhoneNumbers(Long clientId) {
        return findClientById(clientId).getPhoneNumbers();
    }

    private Client findClientById(Long id) {
        return clientRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Клиент с заданным id не найден")
                );
    }
}
