package com.example.tech_learner.repo;

import com.example.tech_learner.entity.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    UserCourse findByEmpId(String empId);

    List<UserCourse> findAllByEmpIdAndStatus(String empId, String pending);
}
