/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.triadsoft;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.model.Task;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 16/04/19 12:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TasksControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void noParamTaskShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description").value("Task1"))
                .andExpect(jsonPath("$[1].description").value("Task2"))
                .andExpect(jsonPath("$[2].description").value("Task3"))
        ;
    }

    @Test
    public void getTaskById() throws Exception {

        this.mockMvc.perform(get("/tasks/2")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Task2"))
        ;
    }

    @Test
    public void getTaskByUnknownId() throws Exception {
        this.mockMvc.perform(get("/tasks/15")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void paramDescriptionShouldReturnTailoredMessage() throws Exception {
        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("description", "task2"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Task2"));
    }

    @Test
    public void paramImageShouldReturnTailoredMessage() throws Exception {
        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("image", "default.gif"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Task3"));
    }

    @Test
    public void paramResolvedShouldReturnTailoredMessage() throws Exception {
        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", "resolved"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Task2"));
    }

    @Test
    public void paramDescriptionAndResolvedShouldReturnOne() throws Exception {
        //La combinación Task2 & true tiene que traer un resultado
        //ver data.sql
        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("description", "task2")
                .param("status", "resolved"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].description").value("Task2"));
    }

    @Test
    public void paramDescriptionAndResolvedShouldReturnNone() throws Exception {
        //La combinación Task2 & false, no tiene que traer ningún resultado
        //ver data.sql
        this.mockMvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("description", "Task2")
                .param("status", "todo"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void addTaskWithResolvedNullAndImage() throws Exception {
        Task task = new Task();
        task.setDescription("New Task");
        task.setImage("mynewimage.gif");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(mapper.writeValueAsString(task)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("New Task"))
                .andExpect(jsonPath("$.status").value("todo"))
                .andExpect(jsonPath("$.image").value("mynewimage.gif"))
        ;
    }

    @Test
    public void addTaskWithResolvedTrueAndWithoutImage() throws Exception {
        Task task = new Task();
        task.setDescription("New Task");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("New Task"))
                .andExpect(jsonPath("$.status").value("todo"))
                .andExpect(jsonPath("$.image").isEmpty())
        ;
    }

    @Test
    public void updateTaskWithOnlyDescription() throws Exception {
        TaskUpdate task = new TaskUpdate();
        task.setDescription("Task1 Updated");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Task1 Updated"))
                .andExpect(jsonPath("$.status").value("todo"))
                .andExpect(jsonPath("$.image").value("images/myimage.gif"))
        ;
    }

    @Test
    public void updateTaskWithOnlyImage() throws Exception {
        TaskUpdate task = new TaskUpdate();
        task.setImage("images/new-image.gif");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andDo(print()).andExpect(status().isOk())
                //debe ser igual a la imagen actualizada
                .andExpect(jsonPath("$.image").value("images/new-image.gif"))
        ;
    }

    @Test
    public void updateTaskWithOnlyStatus() throws Exception {
        TaskUpdate task = new TaskUpdate();
        task.setStatus("resolved");
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(
                put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(task)))
                .andDo(print()).andExpect(status().isOk())
                //debe tener el distinto estado que el test anterior
                .andExpect(jsonPath("$.status").value("resolved"))
        ;
    }
}
