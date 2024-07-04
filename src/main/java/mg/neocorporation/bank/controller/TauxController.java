package mg.neocorporation.bank.controller;

import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.TauxDto;
import mg.neocorporation.bank.service.TauxService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/taux")
public class TauxController {

    private final TauxService tauxService;

    // Post API
    @PostMapping
    public ResponseEntity<TauxDto> createTaux(@RequestBody TauxDto tauxDto) {
        TauxDto savedTaux = tauxService.createTaux(tauxDto);
        return new ResponseEntity<>(savedTaux, HttpStatus.CREATED);
    }

    // Get API by id
    @GetMapping("{idTaux}")
    public ResponseEntity<TauxDto> getTauxById(@PathVariable("idTaux") Long tauxId) {
        TauxDto tauxDto = tauxService.getTauxById(tauxId);
        return ResponseEntity.ok(tauxDto);
    }

    // Get all API
    @GetMapping
    public ResponseEntity<List<TauxDto>> getAllTaux() {
        List<TauxDto> taux = tauxService.getAllTaux();
        return ResponseEntity.ok(taux);
    }

    // Update API
    @PutMapping("{idTaux}")
    public ResponseEntity<TauxDto> updateTaux(@PathVariable("idTaux") Long tauxId,
                                              @RequestBody TauxDto updatedTaux) {
        TauxDto tauxDto = tauxService.updateTaux(tauxId, updatedTaux);
        return ResponseEntity.ok(tauxDto);
    }

    // Delete API
    @DeleteMapping("{idTaux}")
    public ResponseEntity<String> deleteTaux(@PathVariable("idTaux") Long tauxId) {
        tauxService.deleteTaux(tauxId);
        return ResponseEntity.ok("Taux est supprimé avec succès!!");
    }
}
