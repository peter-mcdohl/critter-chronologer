package com.udacity.jdnd.course3.critter.user.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee extends User {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeSkills> skills;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<EmployeeAvailability> daysAvailable;

    public Set<EmployeeSkills> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkills> skills) {
        this.skills = skills;
    }


    public Set<EmployeeAvailability> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<EmployeeAvailability> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
