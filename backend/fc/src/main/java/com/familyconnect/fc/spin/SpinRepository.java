package com.familyconnect.fc.spin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SpinRepository extends JpaRepository<Spin, Integer>{

    Optional<Spin> findById(Long id);
}
