package com.lapinskyi.universitytest.services;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.lapinskyi.universitytest.repositories.LectorRepository;

@ExtendWith(SpringExtension.class)
@Import({LectorRepository.class, LectorService.class})
class LectorServiceTest {

    @Autowired
    private LectorService lectorService;

    @MockBean
    private LectorRepository lectorRepository;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(lectorService, "lectorRepository", lectorRepository);
    }

    @Test
    void shouldInvokeRepositoryMethodsWhenSearchLectorsByTemplate() {
        String template = "Test";
        lectorService.searchLectorsByTemplate(template);

        verify(lectorRepository).findByFirstNameContainsIgnoreCase(template);
        verify(lectorRepository).findByLastNameContainsIgnoreCase(template);
    }

}
