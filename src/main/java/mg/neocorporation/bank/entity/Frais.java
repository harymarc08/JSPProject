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
@Table(name = "frais")

public class Frais {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idFrais;


    @Column(name =  "montant1" )
    private Double montant1;

    @Column(name =  "montant2" )
    private Double montant2;

    @Column(name =  "frais" )
    private Double frais;



}
