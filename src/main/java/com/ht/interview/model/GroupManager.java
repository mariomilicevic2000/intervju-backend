package com.ht.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GroupManager {
    @Id
    private Long groupId;
    private String managerName;

    public GroupManager(Long groupId, String managerName) {
        this.groupId = groupId;
        this.managerName = managerName;
    }

    public GroupManager() {}

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
