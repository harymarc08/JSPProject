package mg.neocorporation.bank.service.impl;


import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.FraisDto;
import mg.neocorporation.bank.entity.Frais;
import mg.neocorporation.bank.mapper.FraisMapper;
import mg.neocorporation.bank.exception.ResourceNotFoundException;
import mg.neocorporation.bank.repository.FraisRepository;
import mg.neocorporation.bank.service.FraisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FraisServiceImpl implements FraisService {
    private FraisRepository fraisRepository;

@Override
    public FraisDto createFrais(FraisDto fraisDto) {

        Frais frais = FraisMapper.maptoFrais(fraisDto);
        Frais savedFrais = fraisRepository.save(frais);

        return FraisMapper.mapToFraisDto(savedFrais);


    }

    @Override
    public FraisDto getFraisById(Long fraisId) {
        Frais frais = fraisRepository.findById(fraisId)
                .orElseThrow(() -> new ResourceNotFoundException("Client n'est pas trouver :" + fraisId));

        return FraisMapper.mapToFraisDto(frais);
    }

    @Override
    public List<FraisDto> getAllFrais() {
        List<Frais> frais = fraisRepository.findAll();
        return frais.stream()
                .map(FraisMapper::mapToFraisDto)
                .collect(Collectors.toList());
    }

    @Override
    public FraisDto updateFrais(Long fraisId, FraisDto updatedFrais) {

        Frais frais = fraisRepository.findById(fraisId).orElseThrow(
                () -> new ResourceNotFoundException("Client n'existe pas  :" + fraisId)
        );
        frais.setIdFrais(updatedFrais.getIdFrais());
        frais.setMontant1(updatedFrais.getMontant1());
        frais.setMontant2(updatedFrais.getMontant2());
        frais.setFrais(updatedFrais.getFrais());

        Frais updatedFraisObj = fraisRepository.save(frais);


        return FraisMapper.mapToFraisDto(updatedFraisObj);
    }

    @Override
    public void deleteFrais(Long fraisId) {

        Frais frais = fraisRepository.findById(fraisId).orElseThrow(
                () -> new ResourceNotFoundException("Client n'existe pas  :" + fraisId)

        );

        fraisRepository.deleteById(fraisId);

    }

}
