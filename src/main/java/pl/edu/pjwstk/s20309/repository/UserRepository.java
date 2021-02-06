package pl.edu.pjwstk.s20309.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.pjwstk.s20309.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select concat( u.firstName, ' ', u.lastName) as fullName " +
            "from User u " +
            "where u.username like :username")
    Optional<String> findFullNameByUsername(String username);
}
