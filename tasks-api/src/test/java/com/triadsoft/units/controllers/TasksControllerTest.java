package com.triadsoft.units.controllers;

import com.triadsoft.api.TaskController;
import com.triadsoft.exceptions.ResourceNotFoundException;
import com.triadsoft.model.Task;
import com.triadsoft.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 4/11/19 10:09
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TasksControllerTest {
    @Mock
    TaskService taskService;

    private TaskController taskController;

    private Task taskOne;
    private Task taskTwo;
    private Task taskThree;

    @Before
    public void onLoad() {
        taskOne = new Task(1, "First Task");
        taskTwo = new Task(1, "Second Task");
        taskThree = new Task(1, "Third Task");
        taskController = new TaskController(taskService);
    }

    @Test
    public void taskList_nullParams() {
        ResponseEntity<List<Task>> taskList = taskController.tasks(null);
        assertEquals(0, taskList.getBody().size());
    }

    @Test
    public void taskList_secondTask(){
        Specification<Task> spec = Specification.where(taskHasDescription("SecondTask"));
        when(taskService.getTasks(spec)).thenReturn(Arrays.asList(taskTwo));
        List<Task> tasks = taskController.tasks(spec).getBody();
        assertNotNull(tasks);
        assertEquals(1,tasks.size());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void task_notFound(){
        when(taskService.findTask(15)).thenThrow(new ResourceNotFoundException("No se encontró la tarea con el id:15"));
        Task task = taskController.getTask(15).getBody();
        assertNull(task);
    }

    @Test()
    public void taskDelete_ok(){
        when(taskService.deleteTask(1)).thenReturn(taskOne);
        Task task = taskController.deleteTask(1).getBody();
        assertNotNull(task);
        assertEquals(taskOne.getDescription(),task.getDescription());
        assertEquals(taskOne.getImage(),task.getImage());
        assertEquals(taskOne.getId(),task.getId());
        assertEquals(taskOne.hashCode(),task.hashCode());
    }

    public static Specification<Task> taskHasDescription(String description) {
        return (book, cq, cb) -> cb.equal(book.get("description"), description);
    }
}
