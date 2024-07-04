package mg.neocorporation.bank.controller;


import mg.neocorporation.bank.dto.EnvoyerDto;
import mg.neocorporation.bank.service.EnvoyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/envoyer")
public class EnvoyerController {

    @Autowired
    private EnvoyerService envoyerService;

    @PostMapping
    public ResponseEntity<String> createEnvoyer(@RequestBody EnvoyerDto envoyerDTO) {
        try {
            envoyerService.createEnvoyer(envoyerDTO);
            return ResponseEntity.ok("Transaction created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    // GET: Récupérer tous les envois
    @GetMapping
    public ResponseEntity<List<EnvoyerDto>> getAllEnvoyers() {
        List<EnvoyerDto> envoyers = envoyerService.getAllEnvoyers();
        return ResponseEntity.ok(envoyers);
    }

    // GET: Récupérer un envoi par ID
    @GetMapping("/{id}")
    public ResponseEntity<EnvoyerDto> getEnvoyerById(@PathVariable Long id) throws Exception {
        EnvoyerDto envoyerDTO = envoyerService.getEnvoyerById(id);
        return ResponseEntity.ok(envoyerDTO);
    }

    // PUT: Mettre à jour un envoi existant
    @PutMapping("/{id}")
    public ResponseEntity<EnvoyerDto> updateEnvoyer(@PathVariable Long id, @RequestBody EnvoyerDto envoyerDTO) throws Exception {
        envoyerDTO.setIdEnv(id); // Assurez-vous que l'ID est mis à jour dans le DTO
        EnvoyerDto updatedEnvoyer = envoyerService.updateEnvoyer(envoyerDTO);
        return ResponseEntity.ok(updatedEnvoyer);
    }

    // DELETE: Supprimer un envoi par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEnvoyer(@PathVariable Long id) {
        try {
            envoyerService.deleteEnvoyer(id);
            return ResponseEntity.ok("{\"message\": \"Transaction deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Error deleting transaction: " + e.getMessage() + "\"}");
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<EnvoyerDto>> getByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<EnvoyerDto> results = envoyerService.findByDate(date);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
    @GetMapping("/totalFees")
    public ResponseEntity<Double> getTotalFees() {
        Double totalFees = envoyerService.getTotalFees();
        return new ResponseEntity<>(totalFees, HttpStatus.OK);
    }

}
