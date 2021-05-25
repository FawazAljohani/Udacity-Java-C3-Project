package com.udacity.jdnd.course3.critter.Services;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Pet save(Pet newPet, Long ownerId) {

        Customer owner = customerRepository.getOne(ownerId);
        newPet.setOwner(owner);

        petRepository.save(newPet);

        owner.getPets().add(newPet);
        customerRepository.save(owner);

        return newPet;
    }

    public Pet findPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwnerId(Long ownerId) {
        Customer owner = customerRepository.getOne(ownerId);

        List<Pet> pets = owner.getPets();

        return pets;
    }
}
