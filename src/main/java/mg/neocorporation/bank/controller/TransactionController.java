package mg.neocorporation.bank.controller;


import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/envoyer")
    public ResponseEntity<String> envoyerArgent(@RequestBody Envoyer envoyer) {
        try {
            transactionService.envoyerArgent(envoyer);
            return ResponseEntity.ok("Transaction réussie");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
