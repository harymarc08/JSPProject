package mg.neocorporation.bank.mapper;

import mg.neocorporation.bank.dto.EnvoyerDto;
import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvoyerMapper {

    @Autowired
    private ClientRepository clientRepository;

    public Envoyer toEntity(EnvoyerDto dto) throws Exception {
        Envoyer envoyer = new Envoyer();

        // Récupération des instances Client à partir des identifiants
        Client envoyeur = clientRepository.findById(dto.getNumEnvoyeur())
                .orElseThrow(() -> new Exception("Envoyeur not found"));
        Client recepteur = clientRepository.findById(dto.getNumRecepteur())
                .orElseThrow(() -> new Exception("Recepteur not found"));

        envoyer.setIdEnv(dto.getIdEnv());
        envoyer.setNumEnvoyeur(envoyeur);
        envoyer.setNumRecepteur(recepteur);
        envoyer.setMontant(dto.getMontant());
        envoyer.setFrais(dto.getFrais());
        envoyer.setDate(dto.getDate());
        envoyer.setRaison(dto.getRaison());

        return envoyer;
    }

    public EnvoyerDto toDTO(Envoyer envoyer) {
        EnvoyerDto dto = new EnvoyerDto();
        dto.setIdEnv(envoyer.getIdEnv());
        dto.setNumEnvoyeur(envoyer.getNumEnvoyeur().getNumTel());
        dto.setNumRecepteur(envoyer.getNumRecepteur().getNumTel());
        dto.setMontant(envoyer.getMontant());
        dto.setFrais(envoyer.getFrais());
        dto.setDate(envoyer.getDate());
        dto.setRaison(envoyer.getRaison());
        return dto;
    }
}
