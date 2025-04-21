package com.ht.interview.service;

import com.ht.interview.model.Technician;
import com.ht.interview.repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicianCronJobServiceImpl implements TechnicianCronJobService{

    @Autowired
    private TechnicianRepository technicianRepository;

    @Scheduled(cron = "0 */1 * * * *")
    @Override
    public void transferTechniciansFrom200To300() {
        try {
            List<Technician> techniciansInGroup200 = technicianRepository.findByGroupManager_GroupId(200L);
            System.out.println(techniciansInGroup200.toString());

            if(techniciansInGroup200.isEmpty()) {
                System.out.println("No technicians in group 200");
                return;
            }

            for(Technician technician : techniciansInGroup200) {
                if(technician.getGroupId() != 300L){
                    technician.setGroupId(300L);
                }
            }

            technicianRepository.saveAll(techniciansInGroup200);
            System.out.println("Moved " + techniciansInGroup200.size() + " technicians from 200 to 300");

        } catch (Exception e) {
            System.out.println("Error during cron job execution " + e.getMessage());
            e.printStackTrace();
        }
    }
}
