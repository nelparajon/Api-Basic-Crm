package com.mic.crm.api_crm.repository;

import com.mic.crm.api_crm.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByOrderByIdAsc(); //usamos la convención de nombres de JPA para el método
    Optional<Customer> findCustomerByName(String name);
    @Query("SELECT c.id FROM Customer c WHERE c.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.contactos ORDER BY c.id ASC")
    Page<Customer> findAllCustomersWithContacts(Pageable pageable);

}
