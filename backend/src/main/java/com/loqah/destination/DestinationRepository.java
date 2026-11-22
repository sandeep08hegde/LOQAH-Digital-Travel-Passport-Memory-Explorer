package com.loqah.destination;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface DestinationRepository extends JpaRepository<Destination,UUID> {
    List<Destination> findByActiveTrueOrderByStateAscNameAsc();
    List<Destination> findByStateIgnoreCaseAndActiveTrue(String state);
    List<Destination> findByCategoryIgnoreCaseAndActiveTrue(String category);
}