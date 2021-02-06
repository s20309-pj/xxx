package pl.edu.pjwstk.s20309.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.s20309.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
}
