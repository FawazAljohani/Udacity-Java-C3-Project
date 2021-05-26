package com.udacity.jdnd.course3.critter.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

// Schedules that indicate one or more employees will be meeting one or more pets
// to perform one or more activities on a specific day.
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ElementCollection
    @JoinTable(name="schedule_activities")
    private Set<EmployeeSkill> activities;

    @ManyToMany
    @JoinTable(
            name="schedule_employee",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="employee_id")
    )
    private List<Employee> employees;

    @ManyToMany
    @JoinTable(
            name="schedule_pet",
            joinColumns = @JoinColumn(name="schedule_id"),
            inverseJoinColumns = @JoinColumn(name="pet_id")
    )
    private List<Pet> pets;

    public Schedule() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
