package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer save(Customer newCustomer, List<Long> petIds) {

        List<Pet> pets = new ArrayList<>();

        if (petIds != null) {
            pets = petIds.stream().map(petId -> petRepository.getOne(petId)).collect(toList());
        }
        newCustomer.setPets(pets);

        return customerRepository.save(newCustomer);
    }

    public Customer findCustomerById(Long id){
        return customerRepository.getOne(id);
    }

    public List<Customer> findAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer findOwnerByPetId(Long petId) {

        Pet pet = petRepository.getOne(petId);

        return pet.getOwner();
    }
}
