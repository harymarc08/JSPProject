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
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "numTel", length = 20)
    private String numTel;

    @Column(name = "nom", length = 50)
    private String nom;

    @Column(name = "sexe", length = 1)
    private String sexe;

    @ManyToOne
    @JoinColumn(name = "idTaux", referencedColumnName = "idTaux", foreignKey = @ForeignKey(name = "FK_taux_idTaux"))
    private Taux idTaux;

    @Column(name = "solde")
    private Double solde;

    @Column(name = "mail", length = 100)
    private String mail;
}
