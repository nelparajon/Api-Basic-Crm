package com.mic.repository;

import com.mic.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OppRepository extends JpaRepository<Opportunity, Long> {

    Optional<Opportunity> findByPublicId(String publicId);
}
