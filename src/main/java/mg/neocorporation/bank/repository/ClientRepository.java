package mg.neocorporation.bank.repository;

import mg.neocorporation.bank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, String> {
    List<Client> findByNomContainingIgnoreCase(String keyword);


}
