package com.lapinskyi.universitytest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapinskyi.universitytest.domain.Lector;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Integer> {
    public List<Lector> findByFirstNameContainsIgnoreCase (String template);
    public List<Lector> findByLastNameContainsIgnoreCase (String template);
}
