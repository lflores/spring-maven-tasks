package com.triadsoft.mappers;

import com.triadsoft.api.model.TaskCreate;
import com.triadsoft.api.model.TaskUpdate;
import com.triadsoft.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 4/11/19 14:15
 */
@Mapper
public interface TaskMapper {
    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "status", target = "status")
    void mapTaskCreateToTask(TaskCreate taskCreate, @MappingTarget Task task);

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "description", target = "description")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "status", target = "status")
    void mapTaskUpdateToTask(TaskUpdate taskUpdate, @MappingTarget Task task);
}
