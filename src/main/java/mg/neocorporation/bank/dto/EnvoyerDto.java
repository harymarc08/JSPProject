package mg.neocorporation.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvoyerDto {
    private Long idEnv;
    private String numEnvoyeur;
    private String numRecepteur;
    private Double montant;
    private Double frais;
    private LocalDateTime date;
    private String raison;
}
