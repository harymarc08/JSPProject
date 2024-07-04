package mg.neocorporation.bank.service.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mg.neocorporation.bank.entity.Client;
import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.entity.Taux;
import mg.neocorporation.bank.repository.ClientRepository;
import mg.neocorporation.bank.repository.EnvoyerRepository;
import mg.neocorporation.bank.repository.TauxRepository;
import mg.neocorporation.bank.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Service
public class PdfServiceImpl implements PdfService {

    private final ClientRepository clientRepository;
    private final EnvoyerRepository envoyerRepository;
    private final TauxRepository tauxRepository;

    public PdfServiceImpl(ClientRepository clientRepository, EnvoyerRepository envoyerRepository, TauxRepository tauxRepository) {
        this.clientRepository = clientRepository;
        this.envoyerRepository = envoyerRepository;
        this.tauxRepository = tauxRepository;
    }

    @Override
    public byte[] generateClientPdf(String numTel, String month) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            SimpleDateFormat monthDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.FRENCH);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new SimpleDateFormat("yyyy-MM").parse(month));
            String formattedDate = monthDateFormat.format(cal.getTime());

            Paragraph dateParagraph = new Paragraph("Date: " + formattedDate);
            dateParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(dateParagraph);

            Client client = clientRepository.findById(numTel).orElse(null);
            if (client == null) {
                document.add(new Paragraph("Aucun client trouvé avec le numéro de téléphone " + numTel));
                document.close();
                return baos.toByteArray();
            }

            document.add(new Paragraph("Contact: " + client.getNumTel()));
            document.add(new Paragraph("Nom: " + client.getNom()));
            // Récupérer le nom du pays à partir de l'idTaux
            Long idTaux = client.getIdTaux().getIdTaux();
            String pays = "Inconnu";
            Optional<Taux> tauxOptional = tauxRepository.findById(idTaux);
            if (tauxOptional.isPresent()) {
                pays = tauxOptional.get().getPays();
            }
            document.add(new Paragraph("Pays: " + pays));
            String sexe = client.getSexe();
            if ("M".equals(sexe)) {
                sexe = "Masculin";
            } else if ("F".equals(sexe)) {
                sexe = "Féminin";
            }
            document.add(new Paragraph("Sexe: " + sexe));
            document.add(new Paragraph("Solde actuel: " + client.getSolde() + " $"));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(4);
            String[] columnTitles = {"Date", "Raison", "Nom du récepteur", "Montant"};
            for (String columnTitle : columnTitles) {
                PdfPCell header = new PdfPCell();
                header.setPhrase(new com.itextpdf.text.Phrase(columnTitle));
                header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(header);
            }

            List<Envoyer> envois = envoyerRepository.findByNumEnvoyeurAndMonth(numTel, month);
            for (Envoyer envoyer : envois) {
                String date = envoyer.getDate().toString();
                String raison = envoyer.getRaison();
                String numRecepteur = envoyer.getNumRecepteur().getNumTel();

                BigDecimal montant = BigDecimal.valueOf(envoyer.getMontant() != null ? envoyer.getMontant() : 0);
                BigDecimal frais = BigDecimal.valueOf(envoyer.getFrais() != null ? envoyer.getFrais() : 0);
                BigDecimal total = montant.add(frais);

                Client recepteur = clientRepository.findById(numRecepteur).orElse(new Client());
                String nomRecepteur = recepteur.getNom();

                table.addCell(date);
                table.addCell(raison);
                table.addCell(nomRecepteur);
                table.addCell(total.toString());
            }

            document.add(table);

            BigDecimal totalDebits = envoyerRepository.calculateTotalDebits(numTel, month);
            document.add(new Paragraph("Total Débit: " + totalDebits + " Euro"));

            document.close();
        } catch (DocumentException | ParseException e) {
            throw new IOException("Erreur lors de la génération du PDF", e);
        }

        return baos.toByteArray();
    }
}

