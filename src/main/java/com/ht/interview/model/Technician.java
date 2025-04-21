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
    @Pattern(regexp = "\\d{3,10}", message = "KP broj mora biti izmedu 3 i 10 znamenaka")
    private String kpNumber;
    @NotNull(message = "Ime ne smije biti prazno")
    @Size(min = 2, max = 50, message = "Ime mora imati barem 2 slova")
    private String firstName;
    @NotNull(message = "Prezime ne smije biti prazno")
    @Size(min = 2, max = 50, message = "Prezime mora imati barem 2 slova")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @NotNull(message = "Grupa mora biti definirana")
    private GroupManager groupManager;

    @NotNull(message = "Kontakt telefon mora biti definiran")
    @Pattern( regexp = "^(\\+385|0)(91|92|95|97|98|99)[\\s-]?\\d{3}[\\s-]?\\d{3,4}$", message = "Unesite ispravan hrvatski mobilni broj"
    )
    private String contactMobile;
    @NotNull(message = "Email mora biti definiran")
    @Email(message = "Unesite ispravnu email adresu")
    private String contactEmail;

    @NotNull(message = "Adresa mora biti definirana")
    @Pattern(regexp = "^[A-Za-zČčĆćŽžŠšĐđ]{2,}(\\s[A-Za-zČčĆćŽžŠšĐđ]{1,})*$", message = "Unesite ispravnu hrvatsku adresu")
    private String workStreetName;

    @NotNull(message = "Kucni broj mora biti definiran")
    @Size(min = 1, max = 5, message = "Kucni broj mora imati izmedu 1 i 5 znakova")
    @Pattern(regexp = "^\\\\d{1,5}[A-Za-z]?$|^bb$", message = "Unesite ispravan kucni broj")
    private String workStreetNumber;
    @NotNull(message = "Postanski broj mora biti definiran")
    @Pattern(regexp = "^[1-5]\\d{4}$", message = "Unesite ispravan hrvatski postanski broj")
    private String workPostcode;
    @NotNull(message = "Mjesto mora biti definirano")
    @Pattern(regexp = "^[A-Za-zČĆŽŠĐčćžšđ\\s\\-']+$", message = "Unesite ispravno ime naselja")
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
