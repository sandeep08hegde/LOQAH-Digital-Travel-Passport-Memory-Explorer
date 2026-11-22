package com.loqah.visit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface VisitRepository extends JpaRepository<Visit,UUID>{
 List<Visit> findByUserEmailIgnoreCaseOrderByVisitedAtDesc(String email);
 boolean existsByUserEmailIgnoreCaseAndDestinationId(String email,UUID destinationId);
}