package com.ht.interview.service;

import com.ht.interview.model.GroupManager;
import com.ht.interview.model.Technician;
import com.ht.interview.repositories.GroupManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class GroupManagerServiceImpl implements GroupManagerService {

    @Autowired
    private GroupManagerRepository groupManagerRepository;

    @Override
    public void createGroupManager(GroupManager groupManager){
        groupManagerRepository.save(groupManager);
    }

    @Override
    public List<GroupManager> getGroupManagers(){
        return groupManagerRepository.findAll();
    }
}
