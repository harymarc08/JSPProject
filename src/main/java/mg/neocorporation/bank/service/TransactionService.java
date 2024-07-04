package mg.neocorporation.bank.service;

import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.entity.Frais;
import mg.neocorporation.bank.repository.ClientRepository;
import mg.neocorporation.bank.repository.EnvoyerRepository;
import mg.neocorporation.bank.repository.FraisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EnvoyerRepository envoyerRepository;

    @Autowired
    private FraisRepository fraisRepository;


    @Transactional
    public void envoyerArgent(Envoyer envoyer) {
        Client envoyeur = envoyer.getNumEnvoyeur();
        Client recepteur = envoyer.getNumRecepteur();
        Double montant = envoyer.getMontant();
        Double frais = Double.valueOf(calculerFrais(montant));

        if (envoyeur.getSolde() < montant + frais) {
            throw new RuntimeException("Solde insuffisant pour effectuer l'envoi");
        }

        envoyeur.setSolde(envoyeur.getSolde() - montant - frais);
        recepteur.setSolde(recepteur.getSolde() + montant);

        clientRepository.save(envoyeur);
        clientRepository.save(recepteur);
        envoyerRepository.save(envoyer);
    }

    private Double calculerFrais(Double montant) {
        List<Frais> fraisList = fraisRepository.findAllByOrderByMontant1Asc();
        for (Frais frais : fraisList) {
            if (montant >= frais.getMontant1() && montant <= frais.getMontant2()) {
                return Double.valueOf(frais.getFrais());
            }
        }
        return (double) 0;
    }


}
