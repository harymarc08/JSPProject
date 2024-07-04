package mg.neocorporation.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taux")


public class Taux {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idTaux;


    @Column(name =  "montant1" )
    private Double montant1;

    @Column(name =  "montant2" )
    private Double montant2;

    @Column(name = "pays", length = 50)
    private String pays;

    @Column(name = "monnaie")
    private String monnaie;

}



