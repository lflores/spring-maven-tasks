package com.triadsoft.services;/**
 * @author triad <leonardo.flores@xcaleconsulting.com>
 * Created 15/04/19 18:13
 */

import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.exceptions.ResourceNotFoundException;
import com.triadsoft.model.Task;
import com.triadsoft.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    public Task updateTask(Integer id, TaskUpdate update) {
        Optional<Task> optional = taskRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("La tarea con el id: " + id + " no existe");
        }
        Task task = optional.get();
        //TODO: Mejorar con converter
        if(!Objects.isNull(update.getDescription())){
            task.setDescription(update.getDescription());
        }
        if(!Objects.isNull(update.getImage())){
            task.setImage(update.getImage());
        }
        if(!Objects.isNull(update.getResolved())){
            task.setResolved(update.getResolved());
        }
        return taskRepository.save(task);
    }

    public Iterable<Task> getTasks(Specification<Task> taskSpecification) {
        return taskRepository.findAll(taskSpecification);
    }

    public Task findTask(Integer taskId) {
        Optional optional = taskRepository.findById(taskId);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("No se encontró la tarea con el id: " + taskId);
        }
        return (Task) optional.get();
    }

    public Task deleteTask(Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return task.get();
        }
        throw new ResourceNotFoundException("No se encontró la tarea con el id: " + taskId);
    }
}