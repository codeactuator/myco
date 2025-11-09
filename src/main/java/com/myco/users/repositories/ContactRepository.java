package com.myco.users.repositories;

import com.myco.users.entities.AppUser;
import com.myco.users.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByAppUser(AppUser appUser);
}
