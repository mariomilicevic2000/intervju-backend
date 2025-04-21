package com.ht.interview.service;

import com.ht.interview.model.Technician;
import org.springframework.data.domain.Page;

//import java.util.List;

public interface TechnicianService {
//    List<Technician> getAllTechnicians();
    void createTechnician(Technician technician);
    Page<Technician> getTechniciansPaginated(int page, int size);
}
