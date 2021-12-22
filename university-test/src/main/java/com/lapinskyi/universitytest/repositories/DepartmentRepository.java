package com.lapinskyi.universitytest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapinskyi.universitytest.domain.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public Department findByNameIgnoreCase (String departmentName);
}
