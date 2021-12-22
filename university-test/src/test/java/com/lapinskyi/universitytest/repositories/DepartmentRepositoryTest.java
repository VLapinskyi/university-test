package com.lapinskyi.universitytest.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.lapinskyi.universitytest.domain.Department;
import com.lapinskyi.universitytest.repositories.aspects.DepartmentRepositoryAspect;
import com.lapinskyi.universitytest.repositories.exceptions.RepositoryException;

@DataJpaTest(showSql = true)
@TestPropertySource("/application-test.properties")
@Import({AopAutoConfiguration.class, DepartmentRepositoryAspect.class})
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class DepartmentRepositoryTest {

    private final String testData = "/Test data.sql";
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private TestEntityManager testEntityManager;
    
    @Test
    @Sql(testData)
    void shouldFindByNameIgnoreCase() {
        String testName = "department";
        Department expectedDepartment = testEntityManager.find(Department.class, 1);
        assertEquals(expectedDepartment, departmentRepository.findByNameIgnoreCase(testName));
    }
    
    @Test
    void shouldThrowRepositoryExceptionWhenEntintyWasNotFoundWhilleFindByNameIgnoreCase() {
        String wrongName = "dtment";
        assertThrows(RepositoryException.class, () -> departmentRepository.findByNameIgnoreCase(wrongName));
    }
}
