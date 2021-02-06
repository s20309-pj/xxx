package pl.edu.pjwstk.s20309.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.s20309.entity.Task;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.enums.TaskStatus;
import pl.edu.pjwstk.s20309.repository.TaskRepository;
import pl.edu.pjwstk.s20309.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void saveTask() {
        Task task = Task.builder()
                .id((long) 1)
                .creator("author")
                .taskDescription("desc")
                .note("note")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .creationDate(LocalDate.now())
                .modificationDate(LocalDate.now())

                .build();


       Mockito.lenient().when(taskRepository.save(task)).thenReturn(task);
        taskRepository.save(task);
        assertThat(task.getId()).isPositive();
        assertThat(task.getCreator()).isEqualTo("author");
        assertThat(task.getTaskDescription()).isEqualTo("desc");
        assertThat(task.getNote()).isEqualTo("note");
        assertThat(task.getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(task.getCreationDate()).isEqualTo(LocalDate.now());
        assertThat(task.getModificationDate()).isEqualTo(LocalDate.now());
    }


    @Test
    void whenGetAvailableNoteForTaskThenReturnEmpty() {
        //given
        final Optional<User> author = userRepository.findById(1L);
        final Optional<Task> taskWithComment = taskRepository.findById(1L);
        taskWithComment.ifPresent(task -> task.setId(1L));
        author.ifPresent(user -> user.setId(1L));

        List<Task> foundComments = taskRepository.findAll().stream().peek(Task::getNote).collect(Collectors.toList());
        assertThat(foundComments).isEmpty();
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));
        Optional<Task> equipment = taskRepository.findById(1L);
        assertThat(equipment).isNotEmpty();
    }

    @Test
    void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(
                Task.builder()
                        .id((long) 1)
                        .creator("author")
                        .taskDescription("desc")
                        .note("note")
                        .taskStatus(TaskStatus.IN_PROGRESS)
                        .creationDate(LocalDate.now())
                        .modificationDate(LocalDate.now())
                        .build(),
                Task.builder()
                        .id((long) 1)
                        .creator("author")
                        .taskDescription("desc")
                        .note("note")
                        .taskStatus(TaskStatus.IN_PROGRESS)
                        .creationDate(LocalDate.now())
                        .modificationDate(LocalDate.now())
                        .build(),
                Task.builder()
                        .id((long) 1)
                        .creator("author")
                        .taskDescription("desc")
                        .note("note")
                        .taskStatus(TaskStatus.IN_PROGRESS)
                        .creationDate(LocalDate.now())
                        .modificationDate(LocalDate.now())
                        .build()
        ));
        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList).hasSize(3);
    }

    @Test
    void deleteTaskById() {
        userRepository.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
