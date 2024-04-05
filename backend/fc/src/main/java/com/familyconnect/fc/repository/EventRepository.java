package com.familyconnect.fc.repository;

import com.familyconnect.fc.models.Event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByFamilyId(Integer familyId);
}

