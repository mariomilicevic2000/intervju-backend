package com.ht.interview.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.UUID;

@Entity(name = "technicians")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "KP broj je obavezan")
    @Pattern(regexp = "\\d{3,10}")
    private String kpNumber;
    @NotNull(message = "Ime ne smije biti prazno")
    @Size(min = 2, max = 50)
    private String firstName;
    @NotNull(message = "Prezime ne smije biti prazno")
    @Size(min = 2, max = 50)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull(message = "Grupa mora biti definirana")
    private GroupManager groupManager;

    @NotNull(message = "Kontakt telefon mora biti definiran")
    private String contactMobile;
    @NotNull(message = "Email mora biti definiran")
    @Email
    private String contactEmail;

    @NotNull(message = "Adresa mora biti definirana")
    private String workStreetName;

    @NotNull(message = "Kucni broj mora biti definiran")
    private String workStreetNumber;
    @NotNull(message = "Postanski broj mora biti definiran")
    private String workPostcode;
    @NotNull(message = "Mjesto mora biti definirano")
    private String workCity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getKpNumber() {
        return kpNumber;
    }

    public void setKpNumber(String kpNumber) {
        this.kpNumber = kpNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public GroupManager getGroup() {
        return groupManager;
    }

    public void setGroup(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getWorkStreetName() {
        return workStreetName;
    }

    public void setWorkStreetName(String workStreetName) {
        this.workStreetName = workStreetName;
    }

    public String getWorkStreetNumber() {
        return workStreetNumber;
    }

    public void setWorkStreetNumber(String workStreetNumber) {
        this.workStreetNumber = workStreetNumber;
    }

    public String getWorkPostcode() {
        return workPostcode;
    }

    public void setWorkPostcode(String workPostcode) {
        this.workPostcode = workPostcode;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public Long getGroupId() {
        return groupManager != null ? groupManager.getGroupId() : -1;
    }

    // Setter for groupId
    public void setGroupId(Long groupId) {
        if (this.groupManager != null) {
            this.groupManager.setGroupId(groupId);
        } else {
            this.groupManager = new GroupManager();
            this.groupManager.setGroupId(groupId);
        }
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + id +
                ", kpNumber=" + kpNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupManager=" + groupManager +
                ", contactMobile='" + contactMobile + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", workStreetName='" + workStreetName + '\'' +
                ", workStreetNumber='" + workStreetNumber + '\'' +
                ", workPostcode='" + workPostcode + '\'' +
                ", workCity='" + workCity + '\'' + '\n' +
                '}';
    }
}
