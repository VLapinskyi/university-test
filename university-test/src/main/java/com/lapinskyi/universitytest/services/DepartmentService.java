package com.lapinskyi.universitytest.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lapinskyi.universitytest.domain.Degree;
import com.lapinskyi.universitytest.domain.Department;
import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.repositories.DepartmentRepository;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService (DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Lector getDepartmentHead(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName);
        return department.getDepartmentHead();
    }

    public int getEmployeeCountFromDepartment(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName);
        return department.getLectors().size();
    }

    public int getEmployeeCountFromDepartmentByDegree(String departmentName, Degree degree) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName);
        return department.getLectors().stream()
                .filter(lector -> lector.getDegree().equals(degree)).toList().size();
    }

    public BigDecimal getAverageSalaryFromDepartment(String departmentName) {
        Department department = departmentRepository.findByNameIgnoreCase(departmentName);

        double doubleAverage = department.getLectors().stream()
                .mapToDouble(lector -> lector.getSalary())
                .average().orElse(0.0);

        BigDecimal resultAverage = new BigDecimal(Double.toString(doubleAverage));
        return resultAverage.setScale(2, RoundingMode.HALF_UP);
    }
}
