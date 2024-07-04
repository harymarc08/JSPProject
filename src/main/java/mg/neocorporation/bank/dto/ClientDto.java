package mg.neocorporation.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientDto {
    private String numTel;
    private String nom;
    private String sexe;
    private Long idTaux;
    private Double solde;
    private String mail;

    public ClientDto(String numTel, String nom) {
        this.numTel = numTel;
        this.nom = nom;}


}
