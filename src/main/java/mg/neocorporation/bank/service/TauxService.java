package mg.neocorporation.bank.service;

import mg.neocorporation.bank.dto.TauxDto;

import java.util.List;

public interface TauxService {
    TauxDto createTaux(TauxDto tauxDto);

    List<TauxDto> getAllTaux();

    TauxDto getTauxById(Long idTaux);

    TauxDto updateTaux(Long idTaux, TauxDto tauxDto);

    void deleteTaux(Long idTaux);
}
