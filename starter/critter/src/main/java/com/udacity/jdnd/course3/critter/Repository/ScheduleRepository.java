package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // find all schedules in Schedule db that has matching employee:
    List<Schedule> getAllByEmployeesContains(Employee employee);

    // find all schedules in Schedule db that has matching pet:
    List<Schedule> getAllByPetsContains(Pet pet);

    // find a list of pets in Schedule db that has pets:
    List<Schedule> getAllByPetsIn(List<Pet> pets);
}
