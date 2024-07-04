package mg.neocorporation.bank.controller;

import lombok.AllArgsConstructor;
import mg.neocorporation.bank.dto.ClientDto;
import mg.neocorporation.bank.exception.ResourceNotFoundException;
import mg.neocorporation.bank.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientController {


    private ClientService clientService;
//Post API
     @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto){
      ClientDto savedClient = clientService.createClient(clientDto);
      return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }
//get API
@GetMapping("/{numTel}")
public ResponseEntity<ClientDto> getClientById(@PathVariable("numTel") String numTel) {
    try {
        ClientDto clientDto = clientService.getClientById(numTel);
        return ResponseEntity.ok(clientDto);
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
    @GetMapping
    public ResponseEntity<List<ClientDto>> getAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    //Update API
    @PutMapping("{numTel}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable("numTel") String clientId,
                                                  @RequestBody ClientDto updatedClient){
        ClientDto clientDto = clientService.updateClient(clientId, updatedClient);

        return ResponseEntity.ok(clientDto);
    }

    //Delete API
    @DeleteMapping("{numTel}")
    public ResponseEntity<String> deleteClient(@PathVariable("numTel") String numTel) {
        try {
            clientService.deleteClient(numTel);
            return ResponseEntity.ok("Client supprimé avec succès.");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression du client.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientDto>> searchClients(@RequestParam("keyword") String keyword) {
        List<ClientDto> clients = clientService.searchClients(keyword);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/numTel")
    public ResponseEntity<List<String>> getAllClientsNumTel() {
        List<String> numTels = clientService.getAllClientsNumTel();
        return ResponseEntity.ok(numTels);
    }


 }
