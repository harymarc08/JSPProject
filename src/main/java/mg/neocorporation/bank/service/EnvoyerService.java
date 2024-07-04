package mg.neocorporation.bank.service;

import mg.neocorporation.bank.dto.EnvoyerDto;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDateTime;

public interface EnvoyerService {

    // Créer un envoi
    void createEnvoyer(EnvoyerDto envoyerDTO) throws Exception;

    void sendEmailNotification(EnvoyerDto envoyerDTO, double frais) throws Exception;

    void sendEmailNotification(EnvoyerDto envoyerDTO, double frais, double montantRecepteurEnEuro) throws Exception;

    // Récupérer tous les envois
    List<EnvoyerDto> getAllEnvoyers();

    // Récupérer un envoi par ID
    EnvoyerDto getEnvoyerById(Long id) throws Exception;

    // Mettre à jour un envoi existant
    EnvoyerDto updateEnvoyer(EnvoyerDto envoyerDTO) throws Exception;

    // Supprimer un envoi par ID
    void deleteEnvoyer(Long id) throws Exception;

    List<EnvoyerDto> findByDate(LocalDateTime date);

    @Transactional(readOnly = true)
    List<EnvoyerDto> findByDate(LocalDate date);

    @Transactional(readOnly = true)
    Double getTotalFees();
}
