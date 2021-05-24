package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // save new Employee:
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    // get Employee by its id:
    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    // set available days for employee by employee Id:
    public void setDayAvailableByEmployeeId(Set<DayOfWeek> daysAvailable, Long employeeId) {
        // find employee by Id
        // set days available by employee:
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        // save newly updated data to Employee db:
        employeeRepository.save(employee);
    }

    // find Employee with matching EmployeeRequestDTO of skills and date:
    public List<Employee> findEmployeesWithSkillsAndDate(Set<EmployeeSkill> skills, LocalDate date) {
        // find a list of employee with matching date of week and skill:
        // first, find in employee_availability table all employees with matching available day of week
        // get day of week from Controller layer scheduleRequestDTO request data
        // then, use filter() to filter out employee with matching skills
        // finally, return as a list of employee using Collectors class .toList():
        List<Employee> employees = employeeRepository
                                    .findAllByDaysAvailableContaining(date.getDayOfWeek()).stream()
                                    .filter(employee -> employee.getSkills().containsAll(skills))
                                    .collect(Collectors.toList());
        return employees;
    }
}
