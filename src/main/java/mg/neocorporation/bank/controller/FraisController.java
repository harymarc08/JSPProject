package mg.neocorporation.bank.controller;

import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.FraisDto;
import mg.neocorporation.bank.entity.Frais;
import mg.neocorporation.bank.service.FraisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/frais")
public class FraisController {

    private FraisService fraisService;
    //Post API
    @PostMapping
    public ResponseEntity<FraisDto> createFrais(@RequestBody FraisDto fraisDto){
        FraisDto savedFrais = fraisService.createFrais(fraisDto);
        return new ResponseEntity<>(savedFrais, HttpStatus.CREATED);
    }
    //get API
    @GetMapping("{idFrais}")
    public ResponseEntity<FraisDto> getFraisById(@PathVariable("idFrais")Long fraisId){
        FraisDto fraisDto= fraisService.getFraisById(fraisId);
        return ResponseEntity.ok(fraisDto);
    }
    @GetMapping
    public ResponseEntity<List<FraisDto>> getAllFrais() {
        List<FraisDto> frais = fraisService.getAllFrais();
        return ResponseEntity.ok(frais);
    }

    //Update API
    @PutMapping("{idFrais}")
    public ResponseEntity<FraisDto> updateFrais(@PathVariable("idFrais") Long fraisId,
                                                  @RequestBody FraisDto updatedFrais){
        FraisDto fraisDto = fraisService.updateFrais(fraisId, updatedFrais);

        return ResponseEntity.ok(fraisDto);
    }

    //Delete API
    @DeleteMapping("{idFrais}")
    public ResponseEntity<String> deleteClient(@PathVariable("idFrais") Long fraisId){
        fraisService.deleteFrais(fraisId);
        return ResponseEntity.ok("Frais est supprimé avec succès!!");
    }
}
