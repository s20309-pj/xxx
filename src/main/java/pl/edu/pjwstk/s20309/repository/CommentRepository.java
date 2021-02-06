package pl.edu.pjwstk.s20309.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.s20309.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select co from Comment co left join Task ta on co.taskReceiver.id= ta.id order by co.taskReceiver.creationDate" )
    List<Comment> getAllByTask();
}
