package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // find a list of employee with matching skills and date from Employee table:
//    List<Employee> getAllBySkillsIn(Set<EmployeeSkill> skills);

    // find a list of employee with matching available day of the week:
    List<Employee> findAllByDaysAvailableContaining(DayOfWeek day);
}
