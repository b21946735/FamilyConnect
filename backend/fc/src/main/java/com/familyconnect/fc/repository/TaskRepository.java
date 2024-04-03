
package com.familyconnect.fc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.familyconnect.fc.models.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
    Optional<Task> findById(int id);
}