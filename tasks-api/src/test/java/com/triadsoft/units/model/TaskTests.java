package com.triadsoft.units.model;

import com.triadsoft.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Objects;

import static org.junit.Assert.*;

/**
 * @author triad <leonardo.flores@overactive.com>
 * Created 4/11/19 14:51
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class TaskTests {

    @Test
    public void taskTest_sameTask() {
        Task task1 = new Task(1, "Task1 Test");
        Task task2 = new Task(1, "Task1 Test");
        assertTrue(task1.equals(task2));
        assertEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void newTaskTest_equalTest(){
        Task task1 = new Task(1, "Task1 Test");
        Task2 task2 = new Task2(1, "Task1 Test");
        assertFalse(task1.equals(task2));
    }

    @Test
    public void taskTest_notEqual_id() {
        Task task1 = new Task(1, "Task1 Test");
        Task task2 = new Task(2, "Task2 Test");
        assertFalse(task1.equals(task2));
        assertNotEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void taskTest_notEqual_description() {
        Task task1 = new Task(1, "Task1 Test");
        Task task2 = new Task(1, "Task2 Test");
        assertFalse(task1.equals(task2));
        assertNotEquals(task1.hashCode(), task2.hashCode());
    }

    @Test
    public void taskTest_notEqual_status() {
        Task task1 = new Task(1, "Task1 Test");
        Task task2 = new Task(1, "Task1 Test");
        task1.setStatus("Test1");
        task1.setStatus("Test2");
        assertFalse(task1.equals(task2));
        assertNotEquals(task1.hashCode(), task2.hashCode());
    }


    private class Task2{
        private Integer id;
        private String description;

        public Task2(Integer id, String description){
            this.id = id;
            this.description = description;
        }

        public Integer getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Task2)) return false;
            Task2 task2 = (Task2) o;
            return Objects.equals(id, task2.id) &&
                    Objects.equals(description, task2.description) &&
                    Objects.equals(image, task2.image) &&
                    Objects.equals(status, task2.status);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, description, image, status);
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private String image;
        private String status;
    }
}
