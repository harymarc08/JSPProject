package mg.neocorporation.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TauxDto {
    private Long idTaux;
    private Double montant1;
    private Double montant2;
    private String pays;
    private String monnaie;
    // Getters and Setters
    public Long getIdTaux() {
        return idTaux;
    }

    public void setIdTaux(Long idTaux) {
        this.idTaux = idTaux;
    }

    public Double getMontant1() {
        return montant1;
    }

    public void setMontant1(Double montant1) {
        this.montant1 = montant1;
    }


    public Double getMontant2() {
        return montant2;
    }

    public void setMontant2(Double montant2) {
        this.montant2 = montant2;
    }



}
