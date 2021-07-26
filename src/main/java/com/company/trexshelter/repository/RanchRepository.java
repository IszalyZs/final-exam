package com.company.trexshelter.repository;

import com.company.trexshelter.model.entity.Ranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RanchRepository extends JpaRepository<Ranch, Long> {
    List<Ranch> findAllByName(String ranchName);
}
