package com.ht.interview.controller;


import com.ht.interview.model.Technician;
import com.ht.interview.service.TechnicianService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {this.technicianService = technicianService;}

    @PostMapping("/admin/technicians")
    public ResponseEntity<String> createTechnician(@RequestBody Technician technician) {
        technicianService.createTechnician(technician);
        return new ResponseEntity<>("Technician added successfully",HttpStatus.CREATED);
    }

//    @GetMapping("/admin/technicians")
//    public ResponseEntity<List<Technician>> getAllTechnicians() {
//        List<Technician> technicians = technicianService.getAllTechnicians();
//        return new ResponseEntity<>(technicians, HttpStatus.OK);
//    }

    @GetMapping("/admin/technicians")
    public ResponseEntity<Page<Technician>> getAllTechniciansPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Technician> techniciansPage = technicianService.getTechniciansPaginated(page, size);

        if(techniciansPage.getTotalElements() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(techniciansPage, HttpStatus.OK);
    }
}
