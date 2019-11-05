package com.triadsoft.repositories;/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 16/04/19 17:49
 */

import com.triadsoft.model.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 16/04/19 17:49
 */
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAll(Specification<Task> specification);
}
