package mg.neocorporation.bank.service.impl;

import mg.neocorporation.bank.dto.EnvoyerDto;
import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.entity.Frais;
import mg.neocorporation.bank.entity.Taux;
import mg.neocorporation.bank.repository.TauxRepository;
import mg.neocorporation.bank.mapper.EnvoyerMapper;
import mg.neocorporation.bank.repository.ClientRepository;
import mg.neocorporation.bank.repository.EnvoyerRepository;
import mg.neocorporation.bank.repository.FraisRepository;
import mg.neocorporation.bank.service.EnvoyerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EnvoyerServiceImpl implements EnvoyerService {

    @Autowired
    private EnvoyerRepository envoyerRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TauxRepository tauxRepository;

    @Autowired
    private FraisRepository fraisRepository;

    @Autowired
    private EnvoyerMapper envoyerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate; // Assurez-vous que votre connexion est correctement configurée

    @Override
    @Transactional
    public void createEnvoyer(EnvoyerDto envoyerDTO) throws Exception {
        // Récupération des clients
        Client envoyeur = clientRepository.findById(envoyerDTO.getNumEnvoyeur())
                .orElseThrow(() -> new Exception("Envoyeur not found"));
        Client recepteur = clientRepository.findById(envoyerDTO.getNumRecepteur())
                .orElseThrow(() -> new Exception("Recepteur not found"));

        // Récupération des taux de change
        Taux tauxEnvoyeur = tauxRepository.findById(envoyeur.getIdTaux().getIdTaux())
                .orElseThrow(() -> new Exception("Taux not found for envoyeur"));

        Taux tauxRecepteur = tauxRepository.findById(recepteur.getIdTaux().getIdTaux())
                .orElseThrow(() -> new Exception("Taux not found for recepteur"));



        // Conversion du montant envoyé en Euro
        double montantEnvoyeurEnEuro = convertirEnEuro(envoyerDTO.getMontant(), tauxEnvoyeur);

        System.out.println("Montant en euros: " + montantEnvoyeurEnEuro);


        // Récupération des frais
        Frais frais = fraisRepository.findByMontant1LessThanEqualAndMontant2GreaterThanEqual(montantEnvoyeurEnEuro, montantEnvoyeurEnEuro)
                .orElseThrow(() -> new Exception("Frais not found for the given montant"));

        // Calcul du montant total en Euro
        double montantTotalEnEuro = montantEnvoyeurEnEuro + frais.getFrais();
        System.out.println("Montant total en euros : " + montantTotalEnEuro);
        // Conversion du montant total en devise d'origine
        double montantTotalDeviseOrigine = convertirEnDeviseEnvoyeur(montantTotalEnEuro, tauxEnvoyeur);
        System.out.println("Montant en local: " + montantTotalDeviseOrigine);
        // Vérification du solde de l'envoyeur
        if (envoyeur.getSolde() < montantTotalDeviseOrigine) {
            throw new Exception("Insufficient balance");
        }



        // Mise à jour des soldes
        envoyeur.setSolde(envoyeur.getSolde() - montantTotalDeviseOrigine);
        double montantRecepteurEnEuro = montantEnvoyeurEnEuro;
        double montantBruteRecepteur = convertirEnDeviseRecepteur(montantRecepteurEnEuro, tauxRecepteur);
        System.out.println("Montant brute : " + montantBruteRecepteur);
        recepteur.setSolde(recepteur.getSolde() + montantBruteRecepteur);

        clientRepository.save(envoyeur);
        clientRepository.save(recepteur);

        // Création de la transaction Envoyer
        Envoyer envoyer = envoyerMapper.toEntity(envoyerDTO);
        envoyer.setMontant(montantRecepteurEnEuro);
        envoyer.setFrais(frais.getFrais());
        envoyer.setDate(LocalDateTime.now());

        // Sauvegarde de l'entité Envoyer
        envoyerRepository.save(envoyer);

        // Envoi de l'email à l'envoyeur et au recepteur
        sendEmailNotification(envoyerDTO, frais.getFrais(),montantRecepteurEnEuro);
    }

    @Override
    public void sendEmailNotification(EnvoyerDto envoyerDTO, double frais) throws Exception {

    }

    public double convertirEnEuro(double montant, Taux taux) throws Exception {
        double montant2Euro = getMontant2ForEuro();
        System.out.println("montant en euro "  +montant2Euro);
        System.out.println("taux :"  +taux.getMontant2());


        double operateur1 = ( taux.getMontant2() / montant2Euro );
        System.out.println("operateur" + operateur1);

        return   operateur1 * montant;
    }

    public double convertirEnDeviseEnvoyeur(double montantEnEuro, Taux taux) throws Exception {
      double montant2Euro = getMontant2ForEuro();
        double operateur2 = (  montant2Euro  / taux.getMontant2());

        return operateur2 * montantEnEuro;
    }
    public double convertirEnDeviseRecepteur(double montantEnEuro, Taux taux) throws Exception {
        double montant2Euro = getMontant2ForEuro();
        double operateur2 = (  montant2Euro  / taux.getMontant2());

        return operateur2 * montantEnEuro;
    }
    public double getMontant2ForEuro() throws Exception {
        // Récupérer le taux pour l'euro
        Taux tauxEuro = tauxRepository.findByMonnaie("euro")
                .orElseThrow(() -> new Exception("Taux pour l'euro non trouvé"));
        // Retourner la valeur de montant2
        return tauxEuro.getMontant2();
    }


    @Override
    public void sendEmailNotification(EnvoyerDto envoyerDTO, double frais,double montantRecepteurEnEuro) throws Exception {
        String from = "marchary08@gmail.com"; // Votre adresse email
        String envoyeurEmail = getClientEmailById(envoyerDTO.getNumEnvoyeur()); // Email de l'envoyeur
        String numRecepteur = envoyerDTO.getNumRecepteur();
        String raison = envoyerDTO.getRaison();

        // Récupérer l'email du récepteur
        String recepteurEmail = getClientEmailByNumTel(numRecepteur);

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "bwrmnyevnxhugedj "); // Mot de passe de l'application
            }
        });

        try {
            // Message pour l'envoyeur
            MimeMessage msgEnvoyeur = new MimeMessage(session);
            msgEnvoyeur.setFrom(new InternetAddress(from));
            msgEnvoyeur.addRecipient(Message.RecipientType.TO, new InternetAddress(envoyeurEmail));
            msgEnvoyeur.setSubject("Confirmation de votre envoi chez OurBank", "utf-8");
            msgEnvoyeur.setText("Cher Client, \n Votre envoi de " + montantRecepteurEnEuro + " euro avec un frais de " + frais + " Euro au contact " + numRecepteur + " a été effectué avec succès. \n Raison : " + raison, "utf-8");

            // Message pour le récepteur
            MimeMessage msgRecepteur = new MimeMessage(session);
            msgRecepteur.setFrom(new InternetAddress(from));
            msgRecepteur.addRecipient(Message.RecipientType.TO, new InternetAddress(recepteurEmail));
            msgRecepteur.setSubject("Reçu d'argent chez OurBank", "utf-8");
            msgRecepteur.setText("Cher Client, \n Vous avez reçu de l'argent de la part du numéro " + envoyerDTO.getNumEnvoyeur() + " avec un montant de " + montantRecepteurEnEuro + " Euro \n Raison :"+raison, "utf-8");

            // Envoi des messages
            Transport transport = session.getTransport("smtp");
            try {
                transport.connect();
                Transport.send(msgEnvoyeur);
                System.out.println("Message envoyeur envoyé");
                Transport.send(msgRecepteur);
                System.out.println("Message récepteur envoyé");
            } finally {
                transport.close();
            }
        } catch (MessagingException e) {
            System.out.println("Erreur lors de l'envoi de l'email d: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur lors de l'envoi de l'email: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi de l'email: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Erreur lors de l'envoi de l'email: " + e.getMessage(), e);
        }
    }


    private String getClientEmailByNumTel(String numTel) throws Exception {
        try {
            return jdbcTemplate.queryForObject("SELECT mail FROM client WHERE num_tel = ?", new Object[]{numTel}, String.class);
        } catch (Exception e) {
            throw new Exception("Erreur lors de la récupération de l'email du récepteur : " + e.getMessage(), e);
        }
    }

    private String getClientEmailById(String clientId) throws Exception {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new Exception("Client not found"));
        return client.getMail();
    }

    @Override
    public List<EnvoyerDto> getAllEnvoyers() {
        List<Envoyer> envoyers = envoyerRepository.findAll();
        return envoyers.stream().map(envoyerMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public EnvoyerDto getEnvoyerById(Long id) throws Exception {
        Envoyer envoyer = envoyerRepository.findById(id)
                .orElseThrow(() -> new Exception("Envoyeur not found"));
        return envoyerMapper.toDTO(envoyer);
    }

    @Override
    @Transactional
    public EnvoyerDto updateEnvoyer(EnvoyerDto envoyerDTO) throws Exception {
        // Récupérer l'objet Envoyer existant à partir de l'ID
        Envoyer envoyer = envoyerRepository.findById(envoyerDTO.getIdEnv())
                .orElseThrow(() -> new Exception("Envoyeur not found"));

        // Récupérer les objets Client à partir des identifiants fournis dans le DTO
        Client envoyeur = clientRepository.findById(envoyerDTO.getNumEnvoyeur())
                .orElseThrow(() -> new Exception("Envoyeur Client not found"));
        Client recepteur = clientRepository.findById(envoyerDTO.getNumRecepteur())
                .orElseThrow(() -> new Exception("Recepteur Client not found"));

        // Mettre à jour l'objet Envoyer avec les valeurs du DTO
        envoyer.setNumEnvoyeur(envoyeur);
        envoyer.setNumRecepteur(recepteur);
        envoyer.setMontant(envoyerDTO.getMontant());
        envoyer.setFrais(envoyerDTO.getFrais());
        envoyer.setDate(envoyerDTO.getDate());
        envoyer.setRaison(envoyerDTO.getRaison());

        // Sauvegarder les modifications
        envoyerRepository.save(envoyer);

        // Retourner le DTO mis à jour
        return envoyerMapper.toDTO(envoyer);
    }

    @Override
    @Transactional
    public void deleteEnvoyer(Long id) throws Exception {
        Envoyer envoyer = envoyerRepository.findById(id)
                .orElseThrow(() -> new Exception("Envoyeur not found"));

        envoyerRepository.delete(envoyer);
    }

    @Override
    public List<EnvoyerDto> findByDate(LocalDateTime date) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnvoyerDto> findByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return envoyerRepository.findByDateBetween(startOfDay, endOfDay)
                .stream()
                .map(envoyerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalFees() {
        return envoyerRepository.findTotalFees();
    }
}
