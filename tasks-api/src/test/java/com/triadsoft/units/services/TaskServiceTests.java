package com.triadsoft.units.services;

import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.exceptions.ResourceNotFoundException;
import com.triadsoft.mappers.TaskMapper;
import com.triadsoft.model.Task;
import com.triadsoft.repositories.TaskRepository;
import com.triadsoft.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 11:08
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TaskServiceTests {
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;
    @Autowired
    TaskMapper taskMapper;

    Task taskOne = new Task(1, "First Task");
    Task taskTwo = new Task(1, "Second Task");
    Task taskThree = new Task(1, "Third Task");

    @Before
    public void onLoad() {
        taskService = new TaskService(taskRepository, taskMapper);
    }

    @Test
    public void taskList_withoutParams() {
        List<Task> taskList = Arrays.asList(taskOne, taskTwo, taskThree);
        Specification<Task> spec = Specification.where(null);
        when(taskRepository.findAll(spec)).thenReturn(taskList);
        Iterable<Task> tasks = taskService.getTasks(spec);
        assertNotNull(tasks);
    }

    @Test
    public void taskList_secondParams() {
        List<Task> taskList = Arrays.asList(taskTwo);
        Specification<Task> spec = Specification.where(taskHasDescription("second"));
        when(taskRepository.findAll(spec)).thenReturn(taskList);
        List<Task> tasks = taskService.getTasks(spec);
        assertEquals(1,tasks.size());
    }

    @Test
    public void taskDelete_ok() {
        List<Task> taskList = Arrays.asList(taskOne, taskThree);
        when(taskRepository.findById(2)).thenReturn(Optional.of(taskTwo));
        Task task = taskService.deleteTask(2);
        assertEquals(taskTwo, task);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void taskDelete_notFound() {
        when(taskRepository.findById(any())).thenReturn( Optional.empty());
        Task task = taskService.deleteTask(5);
        assertEquals(taskTwo, task);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void taskUpdate_notFound() {
        TaskUpdate update = new TaskUpdate();
        when(taskRepository.findById(any())).thenReturn( Optional.empty());
        Task task = taskService.updateTask(5,update);
        assertEquals(taskTwo, task);
    }

    public static Specification<Task> taskHasDescription(String description) {
        return (book, cq, cb) -> cb.equal(book.get("description"), description);
    }
}
