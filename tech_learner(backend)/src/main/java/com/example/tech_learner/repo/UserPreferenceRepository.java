package com.example.tech_learner.repo;

import com.example.tech_learner.entity.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPreferenceRepository extends JpaRepository<UserPreference, Integer> {

    UserPreference findByEmpId(String empId);
}
