package mg.neocorporation.bank.service;

import mg.neocorporation.bank.dto.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);

    ClientDto getClientById(String clientId);

    List<ClientDto> getAllClients();


    ClientDto updateClient(String clientId, ClientDto updatedClient);

    void deleteClient(String clientId);

    List<ClientDto> searchClients(String keyword);

    List<String> getAllClientsNumTel();


    List<ClientDto> getAllClientNumbers();
}
