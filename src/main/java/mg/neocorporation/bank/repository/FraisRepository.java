package mg.neocorporation.bank.repository;

import mg.neocorporation.bank.entity.Frais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FraisRepository extends JpaRepository<Frais,Long> {
    List<Frais> findAllByOrderByMontant1Asc();
    Optional<Frais> findByMontant1LessThanEqualAndMontant2GreaterThanEqual(Double montant1, Double montant2);
}
