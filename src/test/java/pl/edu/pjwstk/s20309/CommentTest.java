package pl.edu.pjwstk.s20309;

import java.time.LocalDate;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.s20309.entity.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CommentTest {


    public CommentTest() {
    }

    @Test
    public void checkIfCanBuildCommentForTask(){
      Comment commentTest = Comment.builder()
      .id((long) 1)
      .note("jakas notatka")
      .whenNoteWasAdded(LocalDate.now())
      .build();

      assertThat(commentTest.getNote()).isEqualTo("jakas notatka");
      assertThat(commentTest.getId()).isEqualTo(1);
      assertThat(commentTest.getWhenNoteWasAdded()).isEqualTo(LocalDate.now());
    }
}
