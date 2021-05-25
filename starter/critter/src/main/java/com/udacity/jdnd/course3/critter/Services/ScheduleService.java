package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findSchedulesByEmployeeId(Long employeeId) {

        Employee employee = employeeRepository.getOne(employeeId);

        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> findSchedulesByPetId(Long petId) {

        Pet pet = petRepository.getOne(petId);

        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> findScheduleByOwnerId(Long ownerId) {

        Customer owner = customerRepository.getOne(ownerId);

        return scheduleRepository.getAllByPetsIn(owner.getPets());
    }
}
