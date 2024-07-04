package mg.neocorporation.bank.service;

import mg.neocorporation.bank.dto.FraisDto;

import java.util.List;

public interface FraisService {
    FraisDto createFrais(FraisDto fraisDto);

    FraisDto getFraisById(Long fraisId);

    List<FraisDto> getAllFrais();

    FraisDto updateFrais(Long fraisId, FraisDto updatedFrais);

    void deleteFrais(Long fraisId);
}
