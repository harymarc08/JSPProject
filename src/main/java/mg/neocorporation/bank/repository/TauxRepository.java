package mg.neocorporation.bank.repository;

import mg.neocorporation.bank.entity.Taux;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TauxRepository extends JpaRepository<Taux,Long> {
    Optional<Taux> findByMonnaie(String monnaie);

}
