package com.ht.interview.service;

import com.ht.interview.model.GroupManager;

import java.util.List;

public interface GroupManagerService {
    void createGroupManager(GroupManager groupManager);
    List<GroupManager> getGroupManagers();
}
