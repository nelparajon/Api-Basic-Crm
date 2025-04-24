package com.mic.crm.api_crm.repository;

import com.mic.crm.api_crm.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
