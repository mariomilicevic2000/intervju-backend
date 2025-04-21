package com.ht.interview.service;

import com.ht.interview.model.GroupManager;
import com.ht.interview.model.Technician;
import com.ht.interview.repositories.GroupManagerRepository;
import com.ht.interview.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private GroupManagerRepository groupManagerRepository;

    @Override
    public void createTechnician(Technician technician) {
        GroupManager group = groupManagerRepository.findById(technician.getGroup().getGroupId())
                .orElseThrow(() -> new RuntimeException("GroupManager not found"));

        technicianRepository.save(technician);
    }
//    @NonNull
//    @Override
//    public List<Technician> getAllTechnicians() {
//        return technicianRepository.findAll();
//    }

    @Override
    public Page<Technician> getTechniciansPaginated(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return technicianRepository.findAll(pageable);
    }
}
