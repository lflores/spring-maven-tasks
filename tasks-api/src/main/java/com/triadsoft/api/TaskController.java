package com.triadsoft.api;

import com.triadsoft.api.model.TaskCreate;
import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.model.Task;
import com.triadsoft.services.TaskService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 16/04/19 17:32
 */
@RequestMapping(
        value = "/tasks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {
    final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> tasks(
            @And({
                    @Spec(path = "description", spec = LikeIgnoreCase.class),
                    @Spec(path = "image", spec = LikeIgnoreCase.class),
                    @Spec(path = "status", spec = Equal.class)
            }) Specification<Task> taskSpecification) {
        return new ResponseEntity<>(taskService.getTasks(taskSpecification), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody TaskCreate task) {
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody TaskUpdate task) {
        return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.findTask(id), HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }
}
