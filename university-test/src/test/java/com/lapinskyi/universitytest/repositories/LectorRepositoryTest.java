package com.lapinskyi.universitytest.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.lapinskyi.universitytest.domain.Lector;

@DataJpaTest(showSql = true)
@TestPropertySource("/application-test.properties")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class LectorRepositoryTest {

    private final String testData = "/Test data.sql";
    
    @Autowired
    private LectorRepository lectorRepository;
    
    @Autowired
    private TestEntityManager testEntityManager;
    
    @Test
    @Sql(testData)
    void shouldFindByFirstNameContainsIgnoreCase() {
        String templateQuery = "An";
        List<Lector> expectedResult = new ArrayList<>(Arrays.asList(
                testEntityManager.find(Lector.class, 1),
                testEntityManager.find(Lector.class, 2)));
        assertEquals(expectedResult, lectorRepository.findByFirstNameContainsIgnoreCase(templateQuery));
    }

    @Test
    @Sql(testData)
    void shouldFindByLastNameContainsIgnoreCase() {
        String templateQuery = "rov";
        List<Lector> expectedResult = new ArrayList<>(Arrays.asList(
                testEntityManager.find(Lector.class, 2)));
        assertEquals(expectedResult, lectorRepository.findByLastNameContainsIgnoreCase(templateQuery));
    }

}
