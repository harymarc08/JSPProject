package mg.neocorporation.bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envoyer")
public class Envoyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEnv;

    @ManyToOne
    @JoinColumn(name = "numEnvoyeur", referencedColumnName = "numtel", foreignKey = @ForeignKey(name = "FK_envoyer_numEnvoyeur"))
    private Client numEnvoyeur;

    @ManyToOne
    @JoinColumn(name = "numRecepteur", referencedColumnName = "numtel", foreignKey = @ForeignKey(name = "FK_envoyer_numRecepteur"))
    private Client numRecepteur;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "frais")
    private Double frais;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "raison", length = 255)
    private String raison;
}
