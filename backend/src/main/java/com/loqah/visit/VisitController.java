package com.loqah.visit;
import com.loqah.destination.DestinationRepository;
import com.loqah.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/visits")
public class VisitController {
 private final VisitRepository visits; private final UserRepository users; private final DestinationRepository destinations;
 public VisitController(VisitRepository v,UserRepository u,DestinationRepository d){visits=v;users=u;destinations=d;}
 public record CreateVisitRequest(UUID destinationId,@Size(max=500) String note){}
 @PostMapping public ResponseEntity<?> create(Authentication auth,@Valid @RequestBody CreateVisitRequest req){
  var user=users.findByEmailIgnoreCase(auth.getName()).orElseThrow();
  var destination=destinations.findById(req.destinationId()).orElseThrow();
  if(visits.existsByUserEmailIgnoreCaseAndDestinationId(auth.getName(),req.destinationId()))
   return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","A visit record already exists for this destination"));
  var visit=visits.save(new Visit(user,destination,req.note()));
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("visitId",visit.getId(),"status",visit.getStatus(),"message","Visit recorded. Stamp eligibility is pending verification."));
 }
 @GetMapping public List<Visit> mine(Authentication auth){return visits.findByUserEmailIgnoreCaseOrderByVisitedAtDesc(auth.getName());}
}