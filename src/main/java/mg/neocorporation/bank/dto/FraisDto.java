package mg.neocorporation.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FraisDto {
    private Long idFrais;
    private Double montant1;
    private Double montant2;
    private Double frais;
    public Long getIdFrais() {
        return idFrais;
    }

    public void setIdFrais(Long idFrais) {
        this.idFrais = idFrais;
    }

    public Double getMontant1() {
        return montant1;
    }

    public void setMontant1(Integer montant1) {
        this.montant1 = Double.valueOf(montant1);
    }

    public Double getMontant2() {
        return montant2;
    }

    public void setMontant2(Integer montant2) {
        this.montant2 = Double.valueOf(montant2);
    }

    public Double getFrais() {
        return frais;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }
}
