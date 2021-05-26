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

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id) {
        return employeeRepository.getOne(id);
    }

    public void setDayAvailableByEmployeeId(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeesWithSkillsAndDate(Set<EmployeeSkill> skills, LocalDate date) {

        List<Employee> employees = employeeRepository
                                    .findAllByDaysAvailableContaining(date.getDayOfWeek()).stream()
                                    .filter(employee -> employee.getSkills().containsAll(skills))
                                    .collect(Collectors.toList());
        return employees;
    }
}
