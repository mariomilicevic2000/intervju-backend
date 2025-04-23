package com.ht.interview.repositories;

import com.ht.interview.model.Technician;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    List<Technician> findByGroupManager_GroupId(Long groupManagerGroupId);
    Page<Technician> findAll(Pageable pageable);

    boolean existsByKpNumber(String kpNumber);
}
