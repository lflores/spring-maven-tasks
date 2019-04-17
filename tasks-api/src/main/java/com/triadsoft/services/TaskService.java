package com.triadsoft.services;/**
 * @author triad <leonardo.flores@xcaleconsulting.com>
 * Created 15/04/19 18:13
 */

import com.triadsoft.model.Task;
import com.triadsoft.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author triad <leonardo.flores@xcaleconsulting.com>
 * Created 15/04/19 18:13
 */
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;


    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, Task task) throws Exception {
        Optional optional = taskRepository.findById(id);
        if (!optional.isPresent()) {
            throw new Exception("La tarea con el id: " + id + " no existe");
        }
        return taskRepository.save(task);
    }

    public Iterable<Task> getTasks(Specification<Task> taskSpecification) {
        return taskRepository.findAll(taskSpecification);
    }

    public Task findTask(Integer taskId) throws Exception {
        Optional optional = taskRepository.findById(taskId);
        if (!optional.isPresent()) {
            throw new Exception("No se encontró la tarea con el id: " + taskId);
        }
        return (Task) optional.get();
    }

    public Task deleteTask(Integer taskId) throws Exception {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return task.get();
        }
        throw new Exception("No se encontró la tarea con el id: " + taskId);
    }
}