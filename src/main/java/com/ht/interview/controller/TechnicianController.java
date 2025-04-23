package com.ht.interview.controller;


import com.ht.interview.model.Technician;
import com.ht.interview.service.TechnicianService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {this.technicianService = technicianService;}

    @PostMapping("/admin/technicians")
    public ResponseEntity<String> createTechnician(@Valid @RequestBody Technician technician) {
        try {
            technicianService.createTechnician(technician);
            return new ResponseEntity<>("Technician added successfully",HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/admin/technicians/check-kp/{kpNumber}")
    public ResponseEntity<Boolean> checkIfKpNumberExists(@PathVariable String kpNumber) {
        boolean exists = technicianService.kpNumberExists(kpNumber);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }


}
