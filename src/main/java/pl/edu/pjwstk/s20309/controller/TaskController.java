package pl.edu.pjwstk.s20309.controller;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.s20309.dto.TaskDTO;
import pl.edu.pjwstk.s20309.entity.Task;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.service.TaskService;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Task>> getAllTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        log.info("Request to get task : {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getOneTask(id));
    }

    @PostMapping("/new")
    public ResponseEntity<Task> addNewTask(@RequestBody Task task) {
        log.info("Request to create task: {}", task);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.save(task));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTaskDetails(@RequestBody TaskDTO task, @PathVariable Long id) {
        log.info("Request to update task: {}", task);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(id, task));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        log.info("Request to delete task with id : {}", id);
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addComment")
    public void addCommentToTask(@RequestParam("id") Long id, @RequestBody String note, User authorId) {
        Task task = taskService.getOneTask(id);
        taskService.commentToTask(task, note, authorId);
    }

    @PostMapping("/assignTask/{taskId}/assignUser/{userId}")
    public User assignUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return taskService.assignTaskToUser(taskId, userId);
    }
}
