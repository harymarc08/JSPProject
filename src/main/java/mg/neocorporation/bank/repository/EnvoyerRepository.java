package mg.neocorporation.bank.repository;
import mg.neocorporation.bank.entity.Envoyer;
import mg.neocorporation.bank.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnvoyerRepository extends JpaRepository<Envoyer, Long> {

//    void deleteByNumEnvoyeur(Client numEnvoyeur);
//
//    void deleteByNumRecepteur(Client numRecepteur);

    List<Envoyer> findByDate(LocalDateTime date);
    List<Envoyer> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT SUM(e.frais) FROM Envoyer e")
    Double findTotalFees();

    @Query("SELECT e FROM Envoyer e WHERE e.numEnvoyeur.numTel = :numTel AND to_char(e.date, 'YYYY-MM') = :month")
    List<Envoyer> findByNumEnvoyeurAndMonth(@Param("numTel") String numTel, @Param("month") String month);

    @Query("SELECT COALESCE(SUM(e.montant + e.frais), 0) FROM Envoyer e WHERE e.numEnvoyeur.numTel = :numTel AND to_char(e.date, 'YYYY-MM') = :month")
    BigDecimal calculateTotalDebits(@Param("numTel") String numTel, @Param("month") String month);

}
