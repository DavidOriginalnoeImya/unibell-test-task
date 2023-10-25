package ru.unibell.testtask.client.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import ru.unibell.testtask.client.dto.AddClientDTO;
import ru.unibell.testtask.client.dto.ClientDTO;
import ru.unibell.testtask.client.service.ClientService;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/clients")
@Validated
public class ClientController {

    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "phoneNumber";

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        return ResponseEntity.ok(clientService.getClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(clientService.getClientById(id));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<?> getClientContacts(
            @PathVariable("id") Long id,
            @RequestParam(name = "type", required = false) String contactType
    ) {
        try {
            if (EMAIL.equals(contactType)) {
                return ResponseEntity.ok(clientService.getClientEmails(id));
            } else if (PHONE_NUMBER.equals(contactType)) {
                return ResponseEntity.ok(clientService.getClientPhoneNumbers(id));
            }
            return ResponseEntity.ok(clientService.getClientContacts(id));
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<ClientDTO> addClient(
            HttpServletRequest httpRequest,
            @RequestBody AddClientDTO addClientDTO
    ) {
        ClientDTO clientDTO = clientService.addClient(addClientDTO);
        return ResponseEntity
                .created(
                    ServletUriComponentsBuilder
                            .fromCurrentContextPath()
                            .pathSegment(httpRequest.getRequestURI().split("/"))
                            .pathSegment(String.valueOf(clientDTO.getId()))
                            .build().toUri()
                )
                .body(clientDTO);
    }

    @PostMapping("/{id}/emails")
    public ResponseEntity<?> addClientEmail(
            @PathVariable("id") Long clientId,
            @RequestBody @Email String email
    ) {
        try {
            ClientDTO clientDTO = clientService.addClientEmail(clientId, email);
            return ResponseEntity
                    .created(
                        URI.create(
                                ServletUriComponentsBuilder
                                    .fromCurrentRequestUri()
                                    .queryParam("type", EMAIL)
                                    .toUriString().replace("emails", "contacts"))
                    )
                    .body(clientDTO.getEmails());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @PostMapping("/{id}/phone-numbers")
    public ResponseEntity<?> addClientPhoneNumber(
            @PathVariable("id") Long clientId,
            @RequestBody @Pattern(regexp = "\\+7[0-9]{10}") String phoneNumber
    ) {
        try {
            ClientDTO clientDTO = clientService.addClientPhoneNumber(clientId, phoneNumber);
            return ResponseEntity
                    .created(
                        URI.create(
                                ServletUriComponentsBuilder
                                    .fromCurrentRequestUri()
                                    .queryParam("type", PHONE_NUMBER)
                                    .toUriString().replace("phone-numbers", "contacts"))
                    )
                    .body(clientDTO.getPhoneNumbers());
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
