package pl.edu.pjwstk.s20309.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.s20309.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c where c.clientName=:clientName")
    Optional<Client> findClientByClientName(String clientName);
}
