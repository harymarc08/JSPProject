package mg.neocorporation.bank.service.impl;

import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.ClientDto;
import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Taux;
import mg.neocorporation.bank.exception.ResourceNotFoundException;
import mg.neocorporation.bank.mapper.ClientMapper;
import mg.neocorporation.bank.repository.ClientRepository;
import mg.neocorporation.bank.repository.EnvoyerRepository;
import mg.neocorporation.bank.repository.TauxRepository;
import mg.neocorporation.bank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EnvoyerRepository envoyerRepository;

    @Autowired
    private TauxRepository tauxRepository;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        if (clientDto.getNumTel() == null || clientDto.getNumTel().isEmpty()) {
            throw new IllegalArgumentException("Le champ numTel doit être défini.");
        }

        Taux pays = tauxRepository.findById(clientDto.getIdTaux())
                .orElseThrow(() -> new ResourceNotFoundException("Pays n'est pas trouvé : " + clientDto.getIdTaux()));

        Client client = ClientMapper.mapToClient(clientDto, pays);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.mapToClientDto(savedClient);
    }

    @Override
    public ClientDto getClientById(String clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client n'est pas trouvé :" + clientId));
        return ClientMapper.mapToClientDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientMapper::mapToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto updateClient(String clientId, ClientDto updatedClient) {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client n'existe pas  :" + clientId)
        );

        Taux idClient = tauxRepository.findById(Long.valueOf(updatedClient.getIdTaux()))
                .orElseThrow(() -> new ResourceNotFoundException("Pays n'est pas trouvé : " + updatedClient.getIdTaux()));

        client.setNumTel(updatedClient.getNumTel());
        client.setNom(updatedClient.getNom());
        client.setSexe(updatedClient.getSexe());
        client.setIdTaux(idClient);
        client.setSolde(updatedClient.getSolde());
        client.setMail(updatedClient.getMail());

        Client updatedClientObj = clientRepository.save(client);
        return ClientMapper.mapToClientDto(updatedClientObj);
    }

    @Override
    @Transactional
    public void deleteClient(String clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client n'existe pas  :" + clientId)
        );

        // Uncomment if needed
        // envoyerRepository.deleteByNumEnvoyeur(client);
        // envoyerRepository.deleteByNumRecepteur(client);

        clientRepository.deleteById(clientId);
    }

    @Override
    public List<ClientDto> searchClients(String keyword) {
        List<Client> clients = clientRepository.findByNomContainingIgnoreCase(keyword);
        return clients.stream()
                .map(ClientMapper::mapToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllClientsNumTel() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(Client::getNumTel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientDto> getAllClientNumbers() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> new ClientDto(client.getNumTel(), client.getNom()))
                .collect(Collectors.toList());
    }
}
