package com.company.trexshelter.repository;

import com.company.trexshelter.model.entity.Ranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RanchRepository extends JpaRepository<Ranch, Long> {
}
