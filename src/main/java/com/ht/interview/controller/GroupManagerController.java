package com.ht.interview.controller;

import com.ht.interview.model.GroupManager;
import com.ht.interview.service.GroupManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class GroupManagerController {
    private final GroupManagerService groupManagerService;

    public GroupManagerController(GroupManagerService groupManagerService) {this.groupManagerService = groupManagerService;}

    @PostMapping("/admin/groupmanagers")
    public ResponseEntity<List<GroupManager>> createGroupManagers(@RequestBody List<GroupManager> groupManagers) {
        for(GroupManager groupManager : groupManagers) {
            groupManagerService.createGroupManager(groupManager);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/admin/groupmanagers")
    public ResponseEntity<List<GroupManager>> getGroupManager() {
        List<GroupManager> groupManagers = groupManagerService.getGroupManagers();
        return new ResponseEntity<>(groupManagers, HttpStatus.OK);
    }
}
