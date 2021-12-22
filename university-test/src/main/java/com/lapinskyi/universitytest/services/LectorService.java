package com.lapinskyi.universitytest.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lapinskyi.universitytest.domain.Lector;
import com.lapinskyi.universitytest.repositories.LectorRepository;

@Service
public class LectorService {
    
    private LectorRepository lectorRepository;
    
    @Autowired
    public LectorService(LectorRepository lectorRepository) {
        this.lectorRepository = lectorRepository;
    }
    
    public Set<Lector> searchLectorsByTemplate(String queryTemplate) {
        Set<Lector> result = new HashSet<>();
        result.addAll(lectorRepository.findByFirstNameContainsIgnoreCase(queryTemplate));
        result.addAll(lectorRepository.findByLastNameContainsIgnoreCase(queryTemplate));
        return result;        
    }
}
