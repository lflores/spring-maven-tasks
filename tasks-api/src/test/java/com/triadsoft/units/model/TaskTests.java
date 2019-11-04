package com.triadsoft.units.model;

import com.triadsoft.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 14:51
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TaskTests {

    @Test
    public void newTaskTest_sameTask() {
        Task task1 = new Task(1, "Task1 Test");
        Task task2 = new Task(1, "Task1 Test");
        assertTrue(task1.equals(task2));
        assertEquals(task1.hashCode(), task2.hashCode());
    }
}
