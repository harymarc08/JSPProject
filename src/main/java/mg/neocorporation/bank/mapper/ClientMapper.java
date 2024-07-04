package mg.neocorporation.bank.mapper;

import mg.neocorporation.bank.dto.ClientDto;
import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Taux;

public class ClientMapper {

    public static ClientDto mapToClientDto(Client client) {
        ClientDto clientDto = new ClientDto();
        clientDto.setNumTel(client.getNumTel());
        clientDto.setNom(client.getNom());
        clientDto.setSexe(client.getSexe());
        clientDto.setIdTaux(client.getIdTaux().getIdTaux());
        clientDto.setSolde(client.getSolde());
        clientDto.setMail(client.getMail());
        return clientDto;
    }

    public static Client mapToClient(ClientDto clientDto, Taux idTaux){
        return new Client(
                clientDto.getNumTel(),
                clientDto.getNom(),
                clientDto.getSexe(),
                idTaux,
                clientDto.getSolde(),
                clientDto.getMail()
        );
    }

}
