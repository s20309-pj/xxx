package pl.edu.pjwstk.s20309.service;

import java.time.LocalDate;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s20309.dto.TaskDTO;
import pl.edu.pjwstk.s20309.entity.Comment;
import pl.edu.pjwstk.s20309.entity.Task;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.enums.TaskStatus;
import pl.edu.pjwstk.s20309.exception.exist.TaskAlreadyExistException;
import pl.edu.pjwstk.s20309.exception.notFound.TaskNotFoundException;
import pl.edu.pjwstk.s20309.repository.CommentRepository;
import pl.edu.pjwstk.s20309.repository.TaskRepository;
import pl.edu.pjwstk.s20309.repository.UserRepository;

@Service
@AllArgsConstructor
public class TaskService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Collection<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getOneTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id " + id + " does not exist"));
    }

    public Task updateTask(Long id, TaskDTO updateTask) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id" + id + "does not exist"));


        if (updateTask.getNote() != null) {
            task.setNote(updateTask.getNote());
        }
        if (updateTask.getTaskDescription() != null) {
            task.setTaskDescription(updateTask.getTaskDescription());
        }
        if (updateTask.getTaskStatus() != null) {
            task.setTaskStatus(updateTask.getTaskStatus());
        }
        if (updateTask.getModificationDate() == null || updateTask.getModificationDate() != null) {
            task.setModificationDate(LocalDate.now());
        }

        return taskRepository.save(task);
    }

    public Task save(Task saveTask) {
        taskRepository.findById(saveTask.getId()).ifPresent(task -> {
          throw  new TaskAlreadyExistException("User with id" + saveTask.getId() + "already exist");
        });
        saveTask.setTaskStatus(TaskStatus.TO_DO);
        return taskRepository.save(saveTask);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public void commentToTask(Task task, String note, User author) {
        task.setNote(note);
        var newComment = Comment.builder()
                .taskReceiver(task)
                .author(author)
                .note(task.getNote())
                .whenNoteWasAdded(LocalDate.now())
                .build();

        commentRepository.save(newComment);
        taskRepository.save(task);
    }

    public User assignTaskToUser(Long taskId, Long userId) {
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id" + taskId + "does not exist"));
        User existingUser = userRepository.getOne(userId);
        existingTask.setTaskStatus(TaskStatus.IN_PROGRESS);
        existingUser.assignTaskToUser(existingTask);
        return userRepository.saveAndFlush(existingUser);
    }
}
