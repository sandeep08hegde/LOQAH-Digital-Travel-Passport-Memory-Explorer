package com.loqah.destination;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController @RequestMapping("/api/destinations")
public class DestinationController {
    private final DestinationRepository repo;
    public DestinationController(DestinationRepository repo){this.repo=repo;}
    @GetMapping public List<Destination> all(@RequestParam(required=false) String state,
                                             @RequestParam(required=false) String category) {
        if(state!=null&&!state.isBlank()) return repo.findByStateIgnoreCaseAndActiveTrue(state);
        if(category!=null&&!category.isBlank()) return repo.findByCategoryIgnoreCaseAndActiveTrue(category);
        return repo.findByActiveTrueOrderByStateAscNameAsc();
    }
    @GetMapping("/{id}") public Destination one(@PathVariable UUID id){return repo.findById(id).orElseThrow();}
}