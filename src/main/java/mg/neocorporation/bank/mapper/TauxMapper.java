package mg.neocorporation.bank.mapper;

import mg.neocorporation.bank.dto.TauxDto;
import mg.neocorporation.bank.entity.Taux;

public class TauxMapper {
    public static TauxDto mapToTauxDto(Taux taux) {
        return new TauxDto(
                taux.getIdTaux(),
                taux.getMontant1(),
                taux.getMontant2(),
                taux.getPays(),
                taux.getMonnaie()
        );
    }

    public static Taux mapToTaux(TauxDto tauxDto) {
        return new Taux(
                tauxDto.getIdTaux(),
                tauxDto.getMontant1(),
                tauxDto.getMontant2(),
                tauxDto.getPays(),
                tauxDto.getMonnaie()

        );
    }
}
