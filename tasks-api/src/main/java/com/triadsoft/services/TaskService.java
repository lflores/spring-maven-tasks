package com.triadsoft.services;/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 15/04/19 18:13
 */

import com.triadsoft.api.model.TaskCreate;
import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.exceptions.ResourceNotFoundException;
import com.triadsoft.mappers.TaskMapper;
import com.triadsoft.model.Task;
import com.triadsoft.repositories.TaskRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 15/04/19 18:13
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Task addTask(TaskCreate taskCreate) {
        Task task = new Task();
        taskMapper.mapTaskCreateToTask(taskCreate, task);
        task.setStatus("todo");
        return taskRepository.save(task);
    }

    public Task updateTask(Integer id, TaskUpdate update) {
        Optional<Task> optional = taskRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("La tarea con el id: " + id + " no existe");
        }
        Task task = optional.get();
        taskMapper.mapTaskUpdateToTask(update, task);
        return taskRepository.save(task);
    }

    public List<Task> getTasks(Specification<Task> taskSpecification) {
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
