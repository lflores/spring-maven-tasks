package com.triadsoft.api;

import com.triadsoft.model.Task;
import com.triadsoft.services.TaskService;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> tasks(
            @And({
                    @Spec(path = "description", spec = Like.class),
                    @Spec(path = "image", spec = Like.class),
                    @Spec(path = "resolved", spec = Equal.class)
            }) Specification<Task> taskSpecification) {
        Iterable<Task> tasks = taskService.getTasks(taskSpecification);
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskService.addTask(task), HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id,@RequestBody Task task) throws Exception {
        return new ResponseEntity<>(taskService.updateTask(id,task), HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<Task>(taskService.findTask(id), HttpStatus.OK);
    }

    @DeleteMapping(
            value = "/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(taskService.deleteTask(id), HttpStatus.OK);
    }
}