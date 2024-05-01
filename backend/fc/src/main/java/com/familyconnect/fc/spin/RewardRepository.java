package com.familyconnect.fc.spin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RewardRepository extends JpaRepository<Reward, Integer>{

    Optional<Reward> findById(Long id);
}
