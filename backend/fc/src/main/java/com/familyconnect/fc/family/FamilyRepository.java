package com.familyconnect.fc.family;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FamilyRepository extends JpaRepository<Family, Integer>{
    Optional<Family> findById(Long id);
}