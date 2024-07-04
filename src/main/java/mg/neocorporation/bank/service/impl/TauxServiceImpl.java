package mg.neocorporation.bank.service.impl;

import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.TauxDto;
import mg.neocorporation.bank.entity.Taux;
import mg.neocorporation.bank.exception.ResourceNotFoundException;
import mg.neocorporation.bank.mapper.TauxMapper;
import mg.neocorporation.bank.repository.TauxRepository;
import mg.neocorporation.bank.service.TauxService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TauxServiceImpl implements TauxService {

    private final TauxRepository tauxRepository;

    @Override
    public TauxDto createTaux(TauxDto tauxDto) {
        Taux taux = TauxMapper.mapToTaux(tauxDto);
        Taux savedTaux = tauxRepository.save(taux);
        return TauxMapper.mapToTauxDto(savedTaux);
    }

    @Override
    public List<TauxDto> getAllTaux() {
        List<Taux> tauxList = tauxRepository.findAll();
        return tauxList.stream()
                .map(TauxMapper::mapToTauxDto)
                .collect(Collectors.toList());
    }

    @Override
    public TauxDto getTauxById(Long idTaux) {
        Taux taux = tauxRepository.findById(idTaux)
                .orElseThrow(() -> new ResourceNotFoundException("Taux not found with id :" + idTaux));
        return TauxMapper.mapToTauxDto(taux);
    }

    @Override
    public TauxDto updateTaux(Long idTaux, TauxDto tauxDto) {
        Taux taux = tauxRepository.findById(idTaux)
                .orElseThrow(() -> new ResourceNotFoundException("Taux not found with id :" + idTaux));
        taux.setMontant1(tauxDto.getMontant1());
        taux.setMontant2(tauxDto.getMontant2());
        taux.setPays(tauxDto.getPays());
        taux.setMonnaie(tauxDto.getMonnaie());
        Taux updatedTaux = tauxRepository.save(taux);
        return TauxMapper.mapToTauxDto(updatedTaux);
    }

    @Override
    public void deleteTaux(Long idTaux) {
        Taux taux = tauxRepository.findById(idTaux)
                .orElseThrow(() -> new ResourceNotFoundException("Taux not found with id :" + idTaux));
        tauxRepository.delete(taux);
    }
}
