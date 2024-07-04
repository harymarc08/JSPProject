package mg.neocorporation.bank.mapper;

import mg.neocorporation.bank.dto.FraisDto;
import mg.neocorporation.bank.entity.Frais;


public class FraisMapper {
    public static FraisDto mapToFraisDto(Frais frais){
        return new FraisDto(
              frais.getIdFrais(),
                frais.getMontant1(),
                frais.getMontant2(),
                frais.getFrais()

        );
    }

    public static Frais maptoFrais(FraisDto fraisDto){
        return new Frais(
                fraisDto.getIdFrais(),
                fraisDto.getMontant1(),
                fraisDto.getMontant2(),
                fraisDto.getFrais()





        );
    }
}
