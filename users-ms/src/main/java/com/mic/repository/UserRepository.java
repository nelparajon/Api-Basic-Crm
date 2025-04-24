package com.mic.repository;

import com.mic.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPublicId(String publicId);

    //Nos beneficiamos del query method naming de JPA para una búsqueda no tan exacta.
    //Permite que aparezcan todos los usuarios a partir de una parte del fullName
    //List<User> findByFirstnameAndLastnameContainingIgnoreCase(String firstName, String lastName);

    List<User> findByEmailContainingIgnoreCase(String email);
}