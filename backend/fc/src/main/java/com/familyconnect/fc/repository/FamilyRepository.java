package com.familyconnect.fc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.familyconnect.fc.models.Family;


@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer>{
    Optional<Family> findById(Long id);
}