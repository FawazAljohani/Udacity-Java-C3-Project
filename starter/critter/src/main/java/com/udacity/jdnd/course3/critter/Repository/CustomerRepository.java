package com.udacity.jdnd.course3.critter.Repository;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // find customer by pet id
    // need to use Query to find joined tables
//    @Query(value="SELECT * FROM Customer AS c INNER JOIN User AS u ON c.id = u.id " +
//            "INNER JOIN Pet AS p ON c.id = p.customer_id WHERE p.id = :petId")
//    Customer findByPetId;
}
